package com.app.dianti.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService2;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.entity.ResureListEntity;
import com.app.dianti.net.event.RefreshEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 17:19
 */
public class RescueListActivity extends CommonListActivity {

    private boolean isHistory;
    private int type = 2;
    private String id = "";
    private boolean mIsLoading = false;
    private ArrayAdapter mAdapter;

    private final List<ResureListEntity.ListEntity> mMapList = new ArrayList<>();

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
        mAdapter = new RescueListAdapter(isHistory, updateListHandler, this, mMapList);
        super.onCreate(savedInstanceState);

    }

    private Handler updateListHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            loadData(1);
        }
    };

    @Subscribe
    public void onEvent(RefreshEvent event){
        loadData(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void loadData(final int currentPage) {
        if (mIsLoading) {
            return;
        }

        //查询类型
        int queryType = type;
        NetService2.getInstance(this)
                .getResureList(AppContext.userInfo.getToken(), type, queryType + "", mCurrentPage, new OnResponseListener<ResureListEntity>() {
                    @Override
                    public void onSuccess(ResureListEntity data1) {
                        mIsLoading = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        mTotalPage = data1.getTotalPage();
                        mCurrentPage = data1.getCurrentPage();
                        List<ResureListEntity.ListEntity> list = data1.getList();
                        if (list == null) {
                            return;
                        }
                        if (currentPage == 1) {
                            mMapList.clear();
                        }
                        mMapList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String info) {
                        mIsLoading = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected ArrayAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_rescue;
    }

    @Override
    protected boolean isHistory() {
        return isHistory;
    }
}
