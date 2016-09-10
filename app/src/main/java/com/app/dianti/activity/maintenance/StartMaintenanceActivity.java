package com.app.dianti.activity.maintenance;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.NetService2;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.entity.MaintenanceAddEntity;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.ImageUtils;
import com.app.dianti.util.Logs;
import com.app.dianti.util.StringUtils;
import com.app.dianti.vo.ResponseData;
import com.mycroft.qrscan.skill.activity.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class StartMaintenanceActivity extends BaseActivity implements OnClickListener, AMapLocationListener {

    private static final int REQUEST_PHOTO_COMPLETE = 103;
    private static final int REQUEST_PHOTO = 102;
    private static final int REQUEST_QRSCAN = 101;

    private final int FILE_SELECT_CODE = 100;
    private Long id;
    private List<String> checkItemList = new ArrayList<>();

    private TextView mQRTextView;
    private TextView mPhotoTextView;
    private TextView mQuzhengTextView;
    private LinearLayout mRootView;
    private EditText addressEditText;
    private TextView uploadTip;

    private Spinner mTypeSpinner;

    private String maintenTypeId;

    private final String[] mType = new String[]{"半月维保", "半年维保", "季度维保", "年度维保"};
    private CheckBox mAllCheckBox;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;

    private String curAddress = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String idStr = bundle.getString("id", null);
            maintenTypeId = bundle.getString("maintenTypeId", "1");
            if (idStr != null) {
                id = Long.parseLong(idStr);
            }
        }

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.start_maintenance);
        super.initTitleBar("电梯维保");
        initView();
        initListener(this);

        mTypeSpinner = (Spinner) findViewById(R.id.type_spinner);
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mType);
        adapter.setDropDownViewResource(R.layout.statement_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setSelection(0);
        mTypeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                maintenTypeId = (arg2 + 1) + "";
                //此处添加加载维保项数据的事件
                initData(maintenTypeId);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        if (id == null) {
            maintenTypeId = "1";
        } else {
            mTypeSpinner.setVisibility(View.GONE);
        }

        initData(maintenTypeId);

        addressEditText = (EditText) findViewById(R.id.address);
        uploadTip = (TextView) findViewById(R.id.uploadTip);
        findViewById(R.id.locationBtn).setOnClickListener(this);


        mQRTextView = (TextView) findViewById(R.id.qrCode);
        findViewById(R.id.scan_image_view)
                .setOnClickListener(this);
        mPhotoTextView = (TextView) findViewById(R.id.photo_text_view);
        findViewById(R.id.photo_image_view).setOnClickListener(this);

        mQuzhengTextView = (TextView) findViewById(R.id.quzheng);
        findViewById(R.id.take_button).setOnClickListener(this);
        // initDataTest();

        mRootView = (LinearLayout) findViewById(R.id.startRootView);

        mAllCheckBox = (CheckBox) findViewById(R.id.all_check);
        mAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mIgnore) {
                    mIgnore = false;
                    return;
                }
                for (int i = 0, size = mRootView.getChildCount(); i < size; i++) {
                    final CheckBox box = (CheckBox) mRootView.getChildAt(i).findViewById(R.id.checkbox);
                    box.setChecked(b);
                }
            }
        });

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initMapLocation();
    }

    public boolean mIgnore = false;

    private void initView() {
        findViewById(R.id.saveBtn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        findViewById(R.id.signBtn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (id == null || id == 0) {
                    // 增加单子
                    addMaintenance();
                } else {
                    saveSignInData();
                }
            }
        });
    }

    private void addMaintenance() {

        final String qr = mQRTextView.getText().toString();
        if (TextUtils.isEmpty(qr) || qr.equals("请点击右侧二维码")) {
            Toast.makeText(StartMaintenanceActivity.this, "请扫描二维码", Toast.LENGTH_SHORT).show();
            return;
        }

        NetService.getInstance(this)
                .addMaintenance(AppContext.userInfo.getToken(), mTypeSpinner.getSelectedItemPosition() + 1, qr, new OnResponseListener<MaintenanceAddEntity>() {
                    @Override
                    public void onSuccess(MaintenanceAddEntity data) {
                        id = (long) data.getId();
                        saveSignInData();
                    }

                    @Override
                    public void onFailure(String info) {
                        Toast.makeText(StartMaintenanceActivity.this, info, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initData(String maintenTypeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("typeId", maintenTypeId);
        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_MAINTENANCE_ITEM).addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    List<Map<String, Object>> list = responseData.getDataMap("list");
                    if (list == null) {
                        return;
                    }

                    int i = 0;
                    mRootView.removeAllViews();
                    for (Map<String, Object> row : list) {
                        Map<String, String> col = new HashMap<String, String>();
                        col.put("check", "0");
                        col.put("rowSeq", (i + 1) + "");
                        col.put("id", row.get("id").toString());
                        col.put("name", row.get("name").toString());
                        col.put("desc", row.get("desc").toString());
                        i = i + 1;
                        addRow(col);
                    }
                } else {
                    tip("加载维保项目数据失败!请返回后重试。");
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                tip("加载维保项目数据失败!请返回后重试。");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            mTmpPath = null;
            return;
        }
        switch (requestCode) {
            case REQUEST_QRSCAN:
                mQRTextView.setText(data.getStringExtra("result"));
                break;
            case REQUEST_PHOTO:
                handlePhoto(REQUEST_PHOTO);
                break;
            case REQUEST_PHOTO_COMPLETE:
                handlePhoto(REQUEST_PHOTO_COMPLETE);
                break;
        }
    }

    private void handlePhoto(final int requestCode) {
        Logs.e("handlePhoto");

        if (TextUtils.isEmpty(mTmpPath)) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPhotoTextView.setText("正在上传...");
                }
            });
        } else if (requestCode == REQUEST_PHOTO_COMPLETE) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    uploadTip.setText("正在上传...");
                }
            });
        }

        NetService.getInstance(this).getHttpClient().dispatcher().executorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //final File file = Glide.with(StartMaintenanceActivity.this).load(mTmpPath).downloadOnly(480, 480).get();
                    //Logs.e(file.getAbsolutePath());
                    Logs.i(mTmpPath);

                    Bitmap bitmap = ImageUtils.fileToBitmap(mTmpPath);
                    byte[] imageData = ImageUtils.compressToByte(bitmap, 300);
                    NetService2.getInstance(StartMaintenanceActivity.this).uploadImage(AppContext.userInfo.getToken(), imageData, new OnResponseListener<String>() {
                        @Override
                        public void onSuccess(final String data) {
                            if (requestCode == REQUEST_PHOTO) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPhotoTextView.setText(data);
                                    }
                                });
                            } else if (requestCode == REQUEST_PHOTO_COMPLETE) {
                                final CharSequence sequence = mQuzhengTextView.getText();
                                if (TextUtils.isEmpty(sequence)) {
                                    mQuzhengTextView.setText(data);
                                } else {
                                    mQuzhengTextView.append("," + data);
                                }

                                uploadTip.setText("");
                            }
                        }

                        @Override
                        public void onFailure(String info) {
                            Toast.makeText(StartMaintenanceActivity.this, info, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(StartMaintenanceActivity.this, "图片上传错误,请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String mTmpPath;

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.scan_image_view) {
            startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_QRSCAN);
        } else if (id == R.id.photo_image_view || id == R.id.take_button) {
            final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/elevator/" + System.currentTimeMillis() + ".jpg");
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mTmpPath = file.getAbsolutePath();

            final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mTmpPath)));

            if (id == R.id.photo_image_view) {
                startActivityForResult(intent, REQUEST_PHOTO);
            } else {
                startActivityForResult(intent, REQUEST_PHOTO_COMPLETE);
            }
            Logs.e(mTmpPath);
        } else if (id == R.id.locationBtn) {
            curAddress = "";
            mlocationClient.startLocation();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addressEditText.setText("");
                }
            });
        }
    }
/*
    private void initDataTest() {
        Map<String, String> col = new HashMap<String, String>();
        col.put("check", "1");
        col.put("name", "机房滑轮间环境");
        col.put("desc", "清洁门窗完好、照明正常");

        addRow(col);
    }*/

    public void addRow(final Map<String, String> row) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mainten_item_row, null);
        mRootView.addView(rowView);
        CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkbox);
        checkbox.setChecked(row.get("check").equals("1"));
        checkbox.setTag(row.get("id"));
        TextView rowSeqText = (TextView) rowView.findViewById(R.id.rowSeq);
        rowSeqText.setText(row.get("rowSeq"));
        TextView nameText = (TextView) rowView.findViewById(R.id.checkName);
        nameText.setText(row.get("name"));
        TextView descText = (TextView) rowView.findViewById(R.id.checkDesc);
        descText.setText(row.get("desc"));

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    mIgnore = true;
                    mAllCheckBox.setChecked(false);
                }

                if (b) {
                    checkItemList.add(row.get("id"));
                } else {
                    checkItemList.remove(row.get("id"));
                }
            }
        });
    }

    private void initListener(final Context context) {
        findViewById(R.id.photo_image_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(context, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData() {
        String address = addressEditText.getText().toString();
        address = "杭州市 西湖区";

        TextView qrCodeEditText = (TextView) findViewById(R.id.qrCode);
        String qrCodeStr = qrCodeEditText.getText().toString();

        TextView photoEditText = (TextView) findViewById(R.id.photo_text_view);
        String photo = photoEditText.getText().toString();
        photo = photo.equals("请点击右侧拍照") ? "" : photo;

        EditText descriptionEditText = (EditText) findViewById(R.id.description);
        String desc = descriptionEditText.getText().toString();

        EditText proveEditText = (EditText) findViewById(R.id.quzheng);
        String prove = proveEditText.getText().toString();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("id", id);
        map.put("address", address);
        map.put("ele_code", qrCodeStr);
        map.put("pic", photo);
        map.put("desc", desc);
        map.put("prove", prove);
        if (checkItemList.size() > 0) {
            map.put("checkList", StringUtils.listJoin(checkItemList));
        } else {
            map.put("checkList", "");
        }

        //map.put("pic", "1");
        //map.put("prove", "1");

        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_MAINTENANCE_COMMIT).addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                Log.i("wj","维保 responseData="+responseData.getCode()+"  "+responseData.getMsg());
                if (responseData.getCode().equals("200")) {
                    tip("保存成功!");
                    EventBus.getDefault().post(new RefreshEvent());
                    finish();
//                    goActivity(MaintenanceActivity.class);
                } else {
                    tip("保存数据失败!");
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                tip("请求保存数据失败!");
            }
        });
    }

    private void saveSignInData() {

        final String qr = mQRTextView.getText().toString();
        if (TextUtils.isEmpty(qr) || qr.equals("请点击右侧二维码")) {
            Toast.makeText(StartMaintenanceActivity.this, "请扫描二维码", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText addressEditText = (EditText) findViewById(R.id.address);
        String address = addressEditText.getText().toString();
        address = "杭州市 西湖区";

        TextView qrCodeEditText = (TextView) findViewById(R.id.qrCode);
        String qrCodeStr = qrCodeEditText.getText().toString();

        TextView photoEditText = (TextView) findViewById(R.id.photo_text_view);
        String photo = photoEditText.getText().toString();

        if ("正在上传...".equals(photo) || "请点击右侧拍照".equals(photo)) {
            photo = "";
        }

        EditText descriptionEditText = (EditText) findViewById(R.id.description);
        String desc = descriptionEditText.getText().toString();

        EditText proveEditText = (EditText) findViewById(R.id.quzheng);
        String prove = proveEditText.getText().toString();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("id", id);
        map.put("address", address);
        map.put("ele_code", qrCodeStr);
        map.put("type", maintenTypeId);
        map.put("pic", photo);
        //       map.put("desc", desc);
        //       map.put("prove", prove);
        map.put("plan_date", System.currentTimeMillis() / 1000);
        if (checkItemList.size() > 0) {
            map.put("checkList", StringUtils.listJoin(checkItemList));
        } else {
            // map.put("checkList", "");
        }

        final String parmas = JSON.toJSONString(map);

        Logs.e(parmas + "");

        PostFormBuilder okHttpRequestBuilder = OkHttpUtils.post().url(AppContext.API_MAINTENANCE_SIGN);
        okHttpRequestBuilder.addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                Logs.e(">>>>>>>>>>:"+respData + " ");
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                Log.i("mycroft", "onResponse: "+responseData.getCode()+" "+responseData.getMsg()+" "+responseData.getData());
                if (responseData.getCode().equals("200")) {
                    tip("签到成功!");
                    try {
                        id = Long.parseLong(responseData.getDataString("id"));
                        confirm(true, responseData.getDataString("id"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    tip("保存数据失败!");
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                tip("请求保存数据失败!");
            }
        });

    }


    private void confirm(boolean yes, String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("id", id);
        if (yes) {
            map.put("type", "1");
            map.put("reason", "确认需要处理");
        } else {
            //完成
            map.put("type", "0");
        }

        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_MAINTENANCE_CONFIRM)
                .addParams("data", parmas)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    //Toasts.show(context, "操作成功!");
                } else {
                    //  Toasts.show(context, "操作失败!");
                }
                //updateHandler.sendEmptyMessage(0);
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                //Toasts.show(context, "操作失败!");
            }
        });
    }


    public void initMapLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                //定位成功后获取当前地址
                String address = amapLocation.getAddress();
                Double lng = amapLocation.getLongitude();
                Double lat = amapLocation.getLatitude();

                if (!StringUtils.isNotBlank(curAddress)) {
                    curAddress = address;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addressEditText.setText(curAddress);
                        }
                    });
                    mlocationClient.stopLocation();
                }
            } else {
                //addressEditText.setText("获取位置失败");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mlocationClient.stopLocation();
        mlocationClient.unRegisterLocationListener(this);
    }
}
