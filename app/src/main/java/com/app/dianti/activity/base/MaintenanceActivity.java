package com.app.dianti.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.maintenance.MaintenanceAdapter;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MaintenanceActivity extends CommonListActivity {

    private long mLastRequestTime;

    private boolean mIsLoading = false;

    private boolean isHistory = false;
    private int type = 2;
    private String id;

    private final List<Map<String, Object>> mMapList = new ArrayList<>();

    private ArrayAdapter<Map<String, Object>> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            isHistory = extras.getBoolean("isHistory", false);
            type = extras.getInt("type", 2);
            id = extras.getString("id", "");
        }

        mAdapter = new MaintenanceAdapter(this, updateListHandler, mMapList, isHistory);
        super.onCreate(savedInstanceState);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Map<String, Object> map = mMapList.get(i);
                startActivity(MaintenanceDetailActivity.getIntent(MaintenanceActivity.this, map));
            }
        });

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {
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

        final Map<String, Object> map = new HashMap<>();
        map.put("token", AppContext.userInfo.getToken());

        map.put("type", type);

        map.put("pageNum", currentPage);
        map.put("id", id);
        String parmas = JSON.toJSONString(map);

        Logs.i("token: " + AppContext.userInfo.getToken());
        mIsLoading = true;

        OkHttpUtils.post().url(AppContext.API_LOGIN_MAINTENANCE).addParams("data", parmas).build().execute(new StringCallback() {
            @Override
            public void onResponse(String respData, int arg1) {
                Log.i("wj", "onResponse: ----->  "+respData.toString());
                mIsLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
//                Log.i("wj", "onResponse: "+responseData.getCode()+"  "+responseData.getData()+"  "+responseData.getMsg());
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

                    Logs.e(mMapList.toString());
                } else {
                    Toast.makeText(MaintenanceActivity.this, "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                mIsLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);
                System.out.println("error");
            }
        });
    }

    @Override
    protected ArrayAdapter<Map<String, Object>> getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_maintenance;
    }

    @Override
    protected boolean isHistory() {
        return isHistory;
    }

    private Handler updateListHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            loadData(1);
        }
    };


//    private void confirm(boolean yes, String id){
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("token", AppContext.userInfo.getToken());
//        map.put("id", id);
//        if(yes){
//            map.put("type", "1");
//            map.put("reason", "确认需要处理");
//        }else{
//            //完成
//            map.put("type", "0");
//        }
//
//        String parmas = JSON.toJSONString(map);
//        OkHttpUtils.post().url(AppContext.API_MAINTENANCE_CONFIRM)
//                .addParams("data", parmas)
//                .build().execute(new StringCallback() {
//
//            @Override
//            public void onResponse(String respData, int arg1) {
//                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
//                if (responseData.getCode().equals("200")) {
//                   // Toasts.show(context, "操作成功!");
//                } else {
//                    //Toasts.show(context, "操作失败!");
//                }
//            }
//
//            @Override
//            public void onError(Call arg0, Exception arg1, int arg2) {
//               // Toasts.show(context, "操作失败!");
//            }
//        });
//    }
}
