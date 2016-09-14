package com.app.dianti.activity.inspection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.NetService2;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.entity.MaintenanceAddEntity;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mycroft.qrscan.skill.activity.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class InspectionAddActivity extends BaseActivity implements OnClickListener {

    private static final int REQUEST_PHOTO_COMPLETE = 103;
    private static final int REQUEST_QRSCAN = 101;

    private TextView mQuzhengTextView;
    private TextView mQRTextView;
    private LinearLayout mRootView;
    private CheckBox mAllCheckBox;

    private Long id;
    private boolean mIgnore = false;
    private String mTmpPath;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.inspection_add);
        super.initTitleBar("电梯巡查");
        initView();
        initData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        mQRTextView = (TextView) findViewById(R.id.qrCode);
        findViewById(R.id.scan_image_view).setOnClickListener(this);
        findViewById(R.id.take_button).setOnClickListener(this);
        mQuzhengTextView = (TextView) findViewById(R.id.quzheng);
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
//                    Log.i("wj", "onCheckedChanged: "+mRootView.getChildCount());
                    final CheckBox box = (CheckBox) mRootView.getChildAt(i).findViewById(R.id.checkbox);
                    box.setChecked(b);
                }
            }
        });
        findViewById(R.id.saveBtn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void initData() {
        //默认为半月维保
        String type = "1";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String idStr = bundle.getString("id", null);
            type = bundle.getString("type", "1");
            if (idStr != null) {
                id = Long.parseLong(idStr);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("typeId", type);
        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_ELEVATOR_DETAIL).addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
//                Log.i("wj", "onResponse: "+responseData.getCode()+"  "+responseData.getData()+"  "+responseData.getMsg());
                if (responseData.getCode().equals("200")) {
                    List<Map<String, Object>> list = responseData.getDataMap("list");
                    if (list == null) {
                        return;
                    }

                    int i = 0;
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
                    tip("加载数据失败!请返回后重试。");
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                tip("加载数据失败!请返回后重试。");
            }
        });
    }

    /**
     *  系统版本编号
     * @return
     */
    private boolean canMakeSmores(){

        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    /**
     * 是否有权限许可
     * @param permission
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String permission){

        if(canMakeSmores()){
            return(checkSelfPermission(permission)== PackageManager.PERMISSION_GRANTED);
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                if(cameraAccepted){
                    //授权成功之后，调用系统相机进行拍照操作等
//                    startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_QRSCAN);
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
                }else{
                    //用户授权拒绝之后，友情提示一下就可以了
                    Toast.makeText(this,"相机授权失败！请重新检查权限",Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        Log.i("wj", "onClick: "+canMakeSmores()+" API= "+Build.VERSION.SDK_INT);
        if (canMakeSmores()){
            String[] perms = {"android.permission.CAMERA"};
            int permsRequestCode = 200;
            this.requestPermissions(perms, permsRequestCode);
            if (view.getId() == R.id.scan_image_view) {

                startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_QRSCAN);

            } else if (view.getId() == R.id.take_button) {
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

                startActivityForResult(intent, REQUEST_PHOTO_COMPLETE);//http://gold.xitu.io/entry/57b580596be3ff006a13c3e0
                Logs.e(mTmpPath);
            }
        }else {
//            Toast.makeText(this,"没有权限",Toast.LENGTH_SHORT).show();
            if (view.getId() == R.id.scan_image_view) {

                startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_QRSCAN);

            } else if (view.getId() == R.id.take_button) {
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

                startActivityForResult(intent, REQUEST_PHOTO_COMPLETE);//http://gold.xitu.io/entry/57b580596be3ff006a13c3e0
                Logs.e(mTmpPath);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_QRSCAN) {
            final TextView qrTextView = (TextView) findViewById(R.id.qrCode);
            qrTextView.setText(data.getStringExtra("result"));
        } else if (requestCode == REQUEST_PHOTO_COMPLETE) {
            if (TextUtils.isEmpty(mTmpPath)) {
                return;
            }

            NetService.getInstance(this).getHttpClient().dispatcher().executorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final File file = Glide.with(InspectionAddActivity.this).load(mTmpPath).downloadOnly(480, 480).get();

                        Logs.e(file.getAbsolutePath());
                        Logs.e(String.valueOf(file.length()));

                        NetService2.getInstance(InspectionAddActivity.this).uploadImage(AppContext.userInfo.getToken(), file.getAbsolutePath(), new OnResponseListener<String>() {
                            @Override
                            public void onSuccess(String data) {
                                final CharSequence sequence = mQuzhengTextView.getText();
                                if (TextUtils.isEmpty(sequence)) {
                                    mQuzhengTextView.setText(data);
                                } else {
                                    mQuzhengTextView.append("," + data);
                                }
                            }

                            @Override
                            public void onFailure(String info) {
                                Toast.makeText(InspectionAddActivity.this, info, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(InspectionAddActivity.this, "图片处理出现问题,请重试", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void addRow(Map<String, String> row) {
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
            }
        });
    }

    /**
     * 保存提交数据事件
     */
    private void saveData() {
        /*二维码code*/
        TextView qrCodeEditText = (TextView) findViewById(R.id.qrCode);
        String qrCodeStr = qrCodeEditText.getText().toString();

        /*问题描述*/
        EditText descriptionEditText = (EditText) findViewById(R.id.description);
        String desc = descriptionEditText.getText().toString();

        /*取证*/
        EditText proveEditText = (EditText) findViewById(R.id.quzheng);
        String prove = proveEditText.getText().toString();

        if (qrCodeEditText.getText().toString().equals(getString(R.string.default_qr_code))) {
            tip(getString(R.string.default_qr_code));
            return;
        }

        /*
         * 巡查项目明细 -- start
         */
        final StringBuilder result = new StringBuilder();
        for (int i = 0, size = mRootView.getChildCount(); i < size; i++) {
            final CheckBox checkBox = (CheckBox) mRootView.getChildAt(i).findViewById(R.id.checkbox);
            if (checkBox.isChecked()) {
                result.append(checkBox.getTag().toString()).append(",");
            }
        }
        final String checkList;
        final String app = result.toString();
        if (app.endsWith(",")) {
            checkList = result.substring(0, result.length() - 1);
        } else {
            checkList = "";
        }
        /*巡查项目明细 --end*/

        /*提交数据*/
        NetService.getInstance(this).addInspection(AppContext.userInfo.getToken(), prove, desc, checkList, qrCodeStr, new OnResponseListener<MaintenanceAddEntity>() {
            @Override
            public void onSuccess(MaintenanceAddEntity data) {
                Toast.makeText(InspectionAddActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new RefreshEvent());
                finish();
            }

            @Override
            public void onFailure(String info) {
                Toast.makeText(InspectionAddActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("InspectionAdd Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
