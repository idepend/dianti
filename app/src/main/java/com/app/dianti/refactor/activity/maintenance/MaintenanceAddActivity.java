package com.app.dianti.refactor.activity.maintenance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.NetService2;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.entity.MaintenanceAddEntity;
import com.app.dianti.refactor.activity.base.CommonToolbarActivity;
import com.app.dianti.refactor.net.entity.MaintenanceType;
import com.app.dianti.refactor.net.entity.MaintenanceTypeList;
import com.app.dianti.refactor.util.Toasts;
import com.app.dianti.util.Logs;
import com.bumptech.glide.Glide;
import com.mycroft.qrscan.skill.activity.CaptureActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public final class MaintenanceAddActivity extends CommonToolbarActivity implements AdapterView.OnItemSelectedListener {

    private static final String EXTRA_ID = "id.extra";

    public static Intent getIntent(@NonNull Context context) {
        return getIntent(context, -1L);
    }

    public static Intent getIntent(@NonNull Context context, long id) {
        final Intent intent = new Intent(context, MaintenanceAddActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    private static final int REQUEST_PHOTO_COMPLETE = 103;
    private static final int REQUEST_PHOTO_SIGN = 102;
    private static final int REQUEST_QRSCAN = 101;

    @BindView(R.id.location_text_view)
    TextView mLocationTextView;
    @BindView(R.id.qrcode_text_view)
    TextView mQrcodeTextView;
    @BindView(R.id.photo_text_view)
    TextView mPhotoTextView;
    @BindView(R.id.type_spinner)
    Spinner mTypeSpinner;
    @BindView(R.id.all_check_box)
    CheckBox mAllCheckBox;
    @BindView(R.id.type_container)
    LinearLayout mTypeContainer;
    @BindView(R.id.desc_edit_text)
    EditText mDescEditText;
    @BindView(R.id.prove_text_view)
    TextView mProveTextView;

    private final String[] mType = new String[]{"半月维保", "半年维保", "季度维保", "年度维保"};

    private long mId;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        final Intent intent = getIntent();
        mId = intent.getLongExtra(EXTRA_ID, -1);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);

        mTypeSpinner = (Spinner) findViewById(R.id.type_spinner);
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mType);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setSelection(0);

        mTypeSpinner.setOnItemSelectedListener(this);

        if (mId <= 0) {
            mTypeSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_maintenance_add2;
    }

    @OnClick({R.id.location_image_view, R.id.qrcode_image_view, R.id.photo_image_view, R.id.sign_button, R.id.commit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_image_view:
                // TODO: 16/7/5  获取当前地址
                break;
            case R.id.qrcode_image_view:
                scanQRCode();
                break;
            case R.id.photo_image_view:
                takePhoto(REQUEST_PHOTO_SIGN);
                break;
            case R.id.take_image_view:
                takePhoto(REQUEST_PHOTO_COMPLETE);
                break;
            case R.id.sign_button:
                addOrSign();
                break;
            case R.id.commit_button:
                finishMaintenance();
                break;
        }
    }

    private final StringBuilder mCheckBuilder = new StringBuilder();

    private void finishMaintenance() {
        if (mId <= 0) {
            Toasts.show(this, "请先签到");
            return;
        }

        final String desc = mDescEditText.getText().toString();
        final String prove = mProveTextView.getText().toString();

        for (int i = 0, size = mTypeContainer.getChildCount(); i < size; i++) {
            final ViewHolder holder = new ViewHolder(mTypeContainer.getChildAt(i));
            if (holder.mCheckBox.isChecked()) {
                mCheckBuilder.append(holder.mCheckBox.getTag()).append(",");
            }
        }
        final String checkList = mCheckBuilder.length() == 0 ? "" : mCheckBuilder.substring(0, mCheckBuilder.length() - 1);

        NetService.getInstance(this).finishMaintenance(AppContext.userInfo.getToken(), mId, desc, prove, checkList, new OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                Toasts.show(getApplicationContext(), "维保完成");
                finish();
            }

            @Override
            public void onFailure(String info) {
                Toasts.show(getApplicationContext(), info);
            }
        });
    }

    /**
     * 添加或者签到
     */
    private void addOrSign() {
        if (mId <= 0) {
            addMaintenance();
        } else {
            signMaintenance();
        }
    }

    private void addMaintenance() {
        final String qrCode = mQrcodeTextView.getText().toString();
        if (TextUtils.isEmpty(qrCode) || "请点击右侧二维码".equals(qrCode)) {
            Toast.makeText(getApplicationContext(), "请扫描二维码", Toast.LENGTH_SHORT).show();
            return;
        }

        NetService.getInstance(this)
                .addMaintenance(AppContext.userInfo.getToken(), mTypeSpinner.getSelectedItemPosition() + 1, qrCode, new OnResponseListener<MaintenanceAddEntity>() {
                    @Override
                    public void onSuccess(MaintenanceAddEntity data) {
                        mId = data.getId();
                        signMaintenance();
                    }

                    @Override
                    public void onFailure(String info) {
                        Toasts.show(getApplicationContext(), info);
                    }
                });
    }

    /**
     * 签到
     */
    private void signMaintenance() {
        // TODO: 16/7/5 获取定位地址
        String location = mLocationTextView.getText().toString();
        location = "杭州市 西湖区";

        String qrCode = mQrcodeTextView.getText().toString();
        if (TextUtils.isEmpty(qrCode) || "请点击右侧二维码".equals(qrCode)) {
            Toasts.show(this, "请扫描二维码");
            return;
        }

        String picture = mPhotoTextView.getText().toString();
        if (TextUtils.isEmpty(picture) || "请点击右侧拍照".equals(qrCode)) {
            Toasts.show(this, "请先拍照");
            return;
        }

        showLoadingDialog(false);
        NetService.getInstance(this).sign(AppContext.userInfo.getToken(), mId, qrCode, location, picture, new OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                hideLoadingDialog();
                Toasts.show(getApplicationContext(), "签到完成");
            }

            @Override
            public void onFailure(String info) {
                hideLoadingDialog();
                Toasts.show(getApplicationContext(), info);
            }
        });
    }

    private String mTmpPath;

    private void takePhoto(int requestCode) {
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

        startActivityForResult(intent, requestCode);
    }

    private void scanQRCode() {
        startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_QRSCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_QRSCAN) {
            mQrcodeTextView.setText(data.getStringExtra("result"));
        } else if (requestCode == REQUEST_PHOTO_SIGN || requestCode == REQUEST_PHOTO_COMPLETE) {
            handlePhoto(requestCode);
        }
    }

    private void handlePhoto(final int requestCode) {
        if (TextUtils.isEmpty(mTmpPath)) {
            return;
        }

        final Activity thiz = this;

        showLoadingDialog(false);
        NetService.getInstance(this).getHttpClient().dispatcher().executorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final File file = Glide.with(thiz).load(mTmpPath).downloadOnly(960, 960).get();

                    Logs.e(file.getAbsolutePath() + ": " + file.length());

                    NetService2.getInstance(thiz).uploadImage(AppContext.userInfo.getToken(), file.getAbsolutePath(), new OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String data) {
                            hideLoadingDialog();
                            if (requestCode == REQUEST_PHOTO_SIGN) {
                                mPhotoTextView.setText(data);
                            } else if (requestCode == REQUEST_PHOTO_COMPLETE) {
                                final CharSequence sequence = mProveTextView.getText();
                                if (TextUtils.isEmpty(sequence)) {
                                    mProveTextView.setText(data);
                                } else {
                                    mProveTextView.append("," + data);
                                }
                            }
                        }

                        @Override
                        public void onFailure(String info) {
                            hideLoadingDialog();
                            Toast.makeText(thiz, info, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    hideLoadingDialog();
                    Toast.makeText(thiz, "图片处理出现问题,请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadMaintenanceTypeList(int type) {
        NetService.getInstance(this).getMaintenanceTypeList(AppContext.userInfo.getToken(), type, new OnResponseListener<MaintenanceTypeList>() {
            @Override
            public void onSuccess(MaintenanceTypeList data) {
                if (data != null) {
                    showTypes(data.getList());
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }

    private static final String EXP_NAME = "%s %s";

    private void showTypes(List<MaintenanceType> list) {
        mTypeContainer.removeAllViews();
        if (list != null && !list.isEmpty()) {
            for (int i = 0, size = list.size(); i < size; i++) {
                final MaintenanceType type = list.get(i);

                final View view = getLayoutInflater().inflate(R.layout.item_maintenance_type, mTypeContainer, false);
                final ViewHolder holder = new ViewHolder(view);

                holder.mNameTextView.setText(String.format(Locale.CHINA, EXP_NAME, type.getId(), type.getDesc()));
                holder.mQualityTextView.setText(type.getDesc());
                holder.mCheckBox.setTag(type.getId());
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        loadMaintenanceTypeList(position + 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // ignore
    }

    static class ViewHolder {

        @BindView(R.id.check_box)
        CheckBox mCheckBox;
        @BindView(R.id.name_text_view)
        TextView mNameTextView;
        @BindView(R.id.quality_text_view)
        TextView mQualityTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
