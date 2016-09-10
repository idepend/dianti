package com.app.dianti.activity.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 20:26
 */
public class InspectionActivity extends CommonListActivity {

    public static final String EXTRA_HISTORY = "isHistory";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_ID = "id";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static Intent getIntent(@NonNull Context context, boolean history, int type, String id) {
        final Intent intent = new Intent(context, InspectionActivity.class);
        intent.putExtra(EXTRA_HISTORY, history);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_ID, id);

        return intent;
    }

    private long mLastRequestTime;

    private boolean mIsLoading = false;

    private InspectionAdapter mAdapter;

    private final List<Map<String, Object>> mMapList = new ArrayList<>();

    private int type = 2;
    private String id = "";
    private boolean isHistory;
    TextView mNullListTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            isHistory = extras.getBoolean(EXTRA_HISTORY, false);
            type = extras.getInt(EXTRA_TYPE, 2);
            id = extras.getString(EXTRA_ID, "");
        }

        mAdapter = new InspectionAdapter(isHistory, this, mMapList);
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent event) {
        loadData(1);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void loadData(final int currentPage) {
        if (mIsLoading) {
            return;
        }

        final long curr = System.currentTimeMillis();
        if (curr - mLastRequestTime < 1000) {
            return;
        }
        mLastRequestTime = curr;

        int queryType = type;

        mIsLoading = true;
        OkHttpUtils.post().url(AppContext.API_ELE_INSPECTION).addParams("token", AppContext.userInfo.getToken())
                .addParams("status_class", queryType + "")
                .addParams("pageNum", currentPage + "")
                .addParams("id", id)
                .build().execute(new StringCallback() {
            @Override
            public void onResponse(String respData, int arg1) {
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
                        mMapList.clear();
                    }
//                    mMapList.addAll(list);
//                    mRootView.removeAllViews();

                    mMapList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    EventBus.getDefault().post(new RefreshEvent());
                    Logs.e(mMapList.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                mIsLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected ArrayAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_inspection_add;
    }

    @Override
    protected boolean isHistory() {
        Logs.e("isHistory: " + isHistory);
        return isHistory;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Inspection Page") // TODO: Define a title for the content shown.
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
