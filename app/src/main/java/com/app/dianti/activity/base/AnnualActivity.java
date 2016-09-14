package com.app.dianti.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.NetService2;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.event.FinishAnnualEvent;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 14:53
 */
public final class AnnualActivity extends CommonListActivity {

    private ArrayAdapter<Map<String, Object>> mAdapter;

    private boolean isHistory;
    private int type = 2;
    private String id = "";

    private final List<Map<String, Object>> mMapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            isHistory = extras.getBoolean("isHistory", false);
            type = extras.getInt("type", 2);
            id = extras.getString("id", "");
        }

        EventBus.getDefault().register(this);
        mAdapter = new AnnualAdapter(isHistory, this, mMapList);
        super.onCreate(savedInstanceState);

        loadData(1);

    }

    private boolean mIsLoading = false;

    @Override
    protected void loadData(final int currentPage) {
        if (mIsLoading) {
            return;
        }

        String status = "";
        if (type == 3) {
            status = "3";
        } else if (type == 1) {
            status = "3";
        } else if (type == 2) {
            status = "1,2";
        }
        int queryType = type;

        mIsLoading = true;
//        Log.i("wj", "id="+id+" status: "+status+"  type== "+queryType);
        OkHttpUtils.post().url(AppContext.API_LOGIN_ANNUAL)
                .addParams("token", AppContext.userInfo.getToken())
                .addParams("id", id)
                .addParams("status_class", queryType + "")
                .addParams("status", status)
                .addParams("pageNum", 1 + "").build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                Logs.e("" + respData);
                mIsLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    mTotalPage = responseData.getDataInt("totalPage");
                    mCurrentPage = responseData.getDataInt("currentPage");
                    List<Map<String, Object>> list = responseData.getDataMap("list");
                    if (list == null) {
                        return;
                    }

                    if (currentPage == 1) {
                        mListView.smoothScrollToPosition(0);
                        mMapList.clear();
                    }
                    mMapList.addAll(list);
                    mAdapter.notifyDataSetChanged();
//                    EventBus.getDefault().post(new RefreshEvent());
                } else {
                    Toast.makeText(AnnualActivity.this, "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                mIsLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(AnnualActivity.this, "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent event) {
        loadData(1);
    }

    private String mTmpId;
    private String mTmpPath;

    private static final int REQUEST_PHOTO_COMPLETE = 103;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishAnnualEvent(FinishAnnualEvent event) {
        mTmpId = event.id;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            mTmpId = null;
            mTmpPath = null;
            return;
        }

        if (requestCode == REQUEST_PHOTO_COMPLETE) {
            if (TextUtils.isEmpty(mTmpPath)) {
                return;
            }

//            final AlertDialog waitDialog = new  AlertDialog.Builder(getApplicationContext())
//                    .setTitle("提示" )
//                    .setMessage("正在上传, 请等待..." )
//                    .setPositiveButton("确定" ,  new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    })
//                    .show();

            NetService.getInstance(this).getHttpClient().dispatcher().executorService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final File file = Glide.with(AnnualActivity.this).load(mTmpPath).downloadOnly(480, 480).get();

                        Logs.e(file.getAbsolutePath());
                        Logs.e(String.valueOf(file.length()));

                        NetService2.getInstance(AnnualActivity.this).uploadImage(AppContext.userInfo.getToken(), file.getAbsolutePath(), new OnResponseListener<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Toast.makeText(AnnualActivity.this, "上传成功!", Toast.LENGTH_LONG).show();
                                finishAnnual(data);
                            }

                            @Override
                            public void onFailure(String info) {
                                Toast.makeText(AnnualActivity.this, "上传失败!" + info, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(AnnualActivity.this, "上传异常,请重试!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void finishAnnual(String imageUrl) {
        NetService.getInstance(this).finishAnnual(mTmpId, AppContext.userInfo.getToken(), imageUrl, new OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                Toast.makeText(AnnualActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String info) {
                Toast.makeText(AnnualActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected ArrayAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getMenuResId() {
        return 0;
    }

    @Override
    protected boolean isHistory() {
        return true;
    }
}
