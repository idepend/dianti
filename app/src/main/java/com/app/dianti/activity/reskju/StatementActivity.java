package com.app.dianti.activity.reskju;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.bumptech.glide.Glide;
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

public class StatementActivity extends BaseActivity implements OnClickListener {

    private static final int REQUEST_QRSCAN = 101;

    private TextView startTime;
    private TextView uploadTipTextView;
    private TextView endTime;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //救援单子的id,有的话就是更新信息，没有此字段的话就是插入信息
    private Long id;
    private Spinner mSpinner2;
    private Spinner mSpinner1;
    private EditText mPicEditText;
    private EditText reasonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.reskju_end_from);
        super.initTitleBar("故障报警");
        initData();

        new Thread() {
            @Override
            public void run() {
                initDatePicker();
            }
        }.start();

        mPicEditText = (EditText) findViewById(R.id.pic);

        reasonEditText = (EditText) findViewById(R.id.reason);

        mPicEditText = (EditText) findViewById(R.id.pic);
        uploadTipTextView = (TextView) findViewById(R.id.uploadTip);

        findViewById(R.id.take_button).setOnClickListener(this);
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
                finishRescue();
            }
        });
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
//                    final File file = Glide.with(getApplicationContext()).load(mTmpPath).downloadOnly(480, 480).get();
//
//                    Logs.e(file.getAbsolutePath());
//                    Logs.e(String.valueOf(file.length()));
                    Bitmap bitmap = ImageUtils.fileToBitmap(mTmpPath);
                    byte[] imageData = ImageUtils.compressToByte(bitmap, 300);
                    NetService2.getInstance(getApplicationContext()).uploadImage(AppContext.userInfo.getToken(), imageData, new OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String data) {
                            if (mPicEditText.getText().equals("")) {
                                mPicEditText.setText(data);
                            } else {
                                mPicEditText.setText(data + "," + mPicEditText.getText().toString());
                            }
                            uploadTipTextView.setText("");
                        }

                        @Override
                        public void onFailure(String info) {
                            Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "图片处理出现问题,请重试", Toast.LENGTH_SHORT).show();
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


    private void finishRescue() {
        EditText reasonText = (EditText) findViewById(R.id.reason);
        EditText picText = (EditText) findViewById(R.id.pic);

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("id", id);
        //开始救援时间
        map.put("startTime", startTimeLong);
        //结束时间
        map.put("endTime", endTimeLong);
        //原因
        map.put("reason", reasonText.getText().toString());
        //图片
        String urls = picText.getText().toString();
        if(urls.endsWith(",")){
            urls = urls.substring(0, urls.length() -1);
        }
        map.put("pic", urls);
        //困人数量
        map.put("tirPeople", 0);
        //有无人员伤亡 0无,1有
        map.put("injuries", 0);
        //解救人数
        map.put("rescuedPeople", 0);
        //是否解救成功 //0失败，1成功
        map.put("isSuccess", 1);

        String parmas = JSON.toJSONString(map);

        Logs.e(parmas);

        OkHttpUtils.post().url(AppContext.API_ELE_RESCUE_WAIT_CONFIRM).addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    tip("保存成功!");
                    EventBus.getDefault().post(new RefreshEvent());
                    finish();
                } else {
                    tip("保存失败! " + responseData.getMsg());
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                tip("保存失败!");
            }
        });
    }

}