package com.app.dianti.activity.reskju;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.activity.base.RescueListActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.NetService2;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.entity.MaintenanceAddEntity;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.ConverterUtil;
import com.app.dianti.util.ImageUtils;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.mycroft.qrscan.skill.activity.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class StartPeopleRescueActivity extends BaseActivity implements OnClickListener {

    private static final int REQUEST_QRSCAN = 101;

    private TextView startTime;
    private TextView endTime;
    private TextView uploadTipTextView;
    private TextView mQRTextView;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //救援单子的id,有的话就是更新信息，没有此字段的话就是插入信息
    private Long id;
    private Spinner mSpinner2;
    private Spinner mSpinner1;
    private EditText mPicEditText;
    private EditText mRescueEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.people_rescue_form);
        super.initTitleBar("困人救援");
        initData();
        initSpinner();
        new Thread() {
            @Override
            public void run() {
                initDatePicker();
            }
        }.start();

        mPicEditText = (EditText) findViewById(R.id.pic);
        uploadTipTextView = (TextView) findViewById(R.id.uploadTip);

        findViewById(R.id.take_button).setOnClickListener(this);

        mQRTextView = (TextView) findViewById(R.id.eleCode);
        findViewById(R.id.scan_image_view)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(getApplicationContext(), CaptureActivity.class), REQUEST_QRSCAN);
                    }
                });
    }

    private void initSpinner() {
        List<String> list1 = new ArrayList<String>();
        list1.add("无伤亡");
        list1.add("有伤亡");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(R.layout.statement_item);
        mSpinner2 = (Spinner) findViewById(R.id.people_injuries);
        mSpinner2.setAdapter(adapter1);

        List<String> list2 = new ArrayList<String>();
        list2.add("成功");
        list2.add("未成功");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list2);
        adapter2.setDropDownViewResource(R.layout.statement_item);
        mSpinner1 = (Spinner) findViewById(R.id.rescue_status);
        mSpinner1.setAdapter(adapter2);
    }

    private void initDatePicker() {

        startTime = (TextView) findViewById(R.id.start_time);

        endTime = (TextView) findViewById(R.id.end_time);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1); // 明年

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);// 去年

        final Calendar currentYear = Calendar.getInstance();
        currentYear.add(Calendar.YEAR, 0);// 今年

//        final CalendarPickerView dialogView = (CalendarPickerView) getLayoutInflater().inflate(R.layout.datepicker_dialog, null, false);
//        dialogView.init(new Date(), lastYear.getTime(), nextYear.getTime());// 初始化

        startTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(startTime);
            }
        });

        endTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(endTime);
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
        if (requestCode == REQUEST_PHOTO_COMPLETE) {
            handlePhoto();
        }

        if (requestCode == REQUEST_QRSCAN) {
            mQRTextView.setText(data.getStringExtra("result"));
        }

    }

    private void handlePhoto() {
        if (TextUtils.isEmpty(mTmpPath)) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                uploadTipTextView.setText("正在上传...");
            }
        });

        NetService.getInstance(this).getHttpClient().dispatcher().executorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //final File file = Glide.with(getApplicationContext()).load(mTmpPath).downloadOnly(480, 480).get();
                    Bitmap bitmap = ImageUtils.fileToBitmap(mTmpPath);
                    byte[] imageData = ImageUtils.compressToByte(bitmap, 300);

                    NetService2.getInstance(getApplicationContext()).uploadImage(AppContext.userInfo.getToken(), imageData, new OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String data) {
                            if(mPicEditText.getText().equals("")){
                                mPicEditText.setText(data);
                            }else{
                                mPicEditText.setText(data + "," + mPicEditText.getText().toString());
                            }
                            uploadTipTextView.setText("");
                        }

                        @Override
                        public void onFailure(String info) {
                            Toast.makeText(getApplicationContext(), "图片上传错误: " + info, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "图片上传错误,请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private static final long MONTH = 30L * 24 * 60 * 60 * 1000;

    private long startTimeLong = -1;
    private long endTimeLong = -1;

    private void showDatePicker(final TextView textView) {
        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog dialog = new DatePickerDialog(this, R.style.AppDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                final Date date = calendar.getTime();
                textView.setText(sdf.format(date));

                if (textView == startTime) {
                    startTimeLong = date.getTime() / 1000;
                } else {
                    endTimeLong = date.getTime() / 1000;
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        final DatePicker datePicker = dialog.getDatePicker();

        datePicker.setMinDate(System.currentTimeMillis() - MONTH);
        datePicker.setMaxDate(System.currentTimeMillis());

        dialog.show();
    }

    private static final int REQUEST_PHOTO_COMPLETE = 103;

    private String mTmpPath;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.take_button) {
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

            startActivityForResult(intent, REQUEST_PHOTO_COMPLETE);
            Logs.e(mTmpPath);
        }
    }

    private void initData() {
        //获取救援单id
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String idStr = bundle.getString("id", null);
            if (idStr != null) {
                id = Long.parseLong(idStr);
            }
        }
        findViewById(R.id.saveReskjuBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                saveData();
                finishRescue();
            }
        });
    }

    private void finishRescue() {
        TextView eleCodeText = (TextView) findViewById(R.id.eleCode);
        EditText tirPeopleText = (EditText) findViewById(R.id.tirPeople);
        EditText reasonText = (EditText) findViewById(R.id.reason);
        EditText picText = (EditText) findViewById(R.id.pic);
        EditText rescue_edit_text = (EditText) findViewById(R.id.rescue_edit_text);//
        final String qr = eleCodeText.getText().toString();
        if (TextUtils.isEmpty(qr) || "请点击右侧二维码".equals(qr)) {
            tip("请扫描二维码");
            return;
        }

        int tirPeople = 0;
        int rescuedPeople = 0;
        if (!tirPeopleText.getText().toString().equals("")) {
            tirPeople = Integer.parseInt(tirPeopleText.getText().toString());
        }
        if(!rescue_edit_text.getText().toString().equals("")){
            rescuedPeople = ConverterUtil.convert2Int(rescue_edit_text.getText().toString(), 0);
        }

        final String idStr = (id == null || id == 0) ? null : String.valueOf(id);

        NetService.getInstance(this).finishRescue(idStr, AppContext.userInfo.getToken(),
                eleCodeText.getText().toString(), tirPeople, rescuedPeople,
                mSpinner2.getSelectedItemPosition(), mSpinner1.getSelectedItemPosition(),
                startTimeLong, endTimeLong,
                reasonText.getText().toString(), picText.getText().toString(),
                new OnResponseListener<MaintenanceAddEntity>() {
                    @Override
                    public void onSuccess(MaintenanceAddEntity data) {
                        tip("保存成功!");
                        EventBus.getDefault().post(new RefreshEvent());
                        finish();
                    }

                    @Override
                    public void onFailure(String info) {
                        tip("保存数据失败! " + info);
                    }
                });
    }

    private void saveData() {
        TextView eleCodeText = (TextView) findViewById(R.id.eleCode);
        EditText tirPeopleText = (EditText) findViewById(R.id.tirPeople);
        EditText reasonText = (EditText) findViewById(R.id.reason);
        EditText picText = (EditText) findViewById(R.id.pic);
        EditText rescue_edit_text = (EditText) findViewById(R.id.rescue_edit_text);
        int tirPeople = 0;
        int isSuccess = 1;
        int injuries=mSpinner2.getSelectedItemPosition();
        int rescuedPeople = 0;

        final String qr = eleCodeText.getText().toString();
        if (TextUtils.isEmpty(qr) || "请点击右侧二维码".equals(qr)) {
            tip("请扫描二维码");
            return;
        }

        if (!tirPeopleText.getText().toString().equals("")) {
            tirPeople = Integer.parseInt(tirPeopleText.getText().toString());
        }
        if(!rescue_edit_text.getText().toString().equals("")){
            rescuedPeople = ConverterUtil.convert2Int(rescue_edit_text.getText().toString(), 0);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("id", id);
        //二维码code, 必填
        map.put("eleCode", eleCodeText.getText());
        //困人数量
        map.put("tirPeople", tirPeople);
        //有无人员伤亡 0无,1有
        map.put("injuries",injuries);
        //是否解救成功 //0失败，1成功
        map.put("isSuccess", isSuccess);
        //开始救援时间
        map.put("startTime", startTime.getText().toString());
        //结束时间
        map.put("endTime", endTime.getText().toString());
        //解救人数
        map.put("rescuedPeople", rescuedPeople);
        //原因
        map.put("reason", reasonText.getText().toString());
        //图片
        String urls = picText.getText().toString();
        if(urls.endsWith(",")){
            urls = urls.substring(0, urls.length() -1);
        }
        map.put("pic", urls);

        String parmas = JSON.toJSONString(map);

        Logs.e(parmas);
        Log.i("wj", "onResponse: "+AppContext.API_ELE_RESCUE_WAIT_CONFIRM);
        OkHttpUtils.post().url(AppContext.API_ELE_RESCUE_WAIT_CONFIRM).addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    tip("保存成功!");
                    EventBus.getDefault().post(new RefreshEvent());
                    goActivity(RescueListActivity.class);
                } else {
                    tip("保存数据失败! " + responseData.getMsg());
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                tip("保存数据失败!");
            }
        });
    }

}
