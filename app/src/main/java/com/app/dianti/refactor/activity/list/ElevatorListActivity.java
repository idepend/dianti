package com.app.dianti.refactor.activity.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.refactor.activity.base.CommonToolbarActivity;
import com.app.dianti.refactor.net.entity.Elevator;
import com.app.dianti.refactor.net.entity.ElevatorList;
import com.app.dianti.refactor.util.Toasts;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class ElevatorListActivity extends CommonToolbarActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

    private static final String EXTRA_REGISTER_CODE = "register_code.extra";
    private static final String EXTRA_ELEVATOR_NAME = "elevatorName.extra";
    private static final String EXTRA_TENEMENTS = "tenements.extra";
    private static final String EXTRA_MAINTENANCE = "maintenance.extra";

    private static final String EXTRA_SEARCH = "search.extra";
    private ElevatorAdapter mAdapter;

    public static Intent getIntent(@NonNull Context context) {
        return getIntent(context, false, null, null, null, null);
    }

    public static Intent getIntent(@NonNull Context context, boolean isSearch,
                                   String registerCode, String elevatorName, String tenements, String maintenance) {
        final Intent intent = new Intent(context, ElevatorListActivity.class);
        intent.putExtra(EXTRA_SEARCH, isSearch);
        intent.putExtra(EXTRA_REGISTER_CODE, registerCode);
        intent.putExtra(EXTRA_ELEVATOR_NAME, elevatorName);
        intent.putExtra(EXTRA_TENEMENTS, tenements);
        intent.putExtra(EXTRA_MAINTENANCE, maintenance);
        return intent;
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mIsSearch;
    private String mRegisterCode;
    private String mElevatorName;
    private String mTenements;
    private String mMaintenance;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        final Intent intent = getIntent();
        mIsSearch = intent.getBooleanExtra(EXTRA_SEARCH, false);
        mRegisterCode = intent.getStringExtra(EXTRA_REGISTER_CODE);
        mElevatorName = intent.getStringExtra(EXTRA_ELEVATOR_NAME);
        mTenements = intent.getStringExtra(EXTRA_TENEMENTS);
        mMaintenance = intent.getStringExtra(EXTRA_MAINTENANCE);
    }

    private final List<Elevator> mElevatorList = new ArrayList<>();

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ElevatorAdapter(this, R.layout.item_elevator, mElevatorList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnRecyclerViewItemClickListener(this);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadElevatorList(mCurrentPage);
            }
        });
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        loadElevatorList(1);
    }

    private int mCurrentPage = 1;
    private int mTotalPage = 2;

    private void loadElevatorList(final int currentPage) {
        if (mCurrentPage >= mTotalPage) {
            return;
        }

        NetService.getInstance(this).getElevatorList(
                AppContext.userInfo.getToken(),
                currentPage,
                mRegisterCode,
                mElevatorName,
                mTenements,
                mMaintenance,
                new OnResponseListener<ElevatorList>() {
                    @Override
                    public void onSuccess(ElevatorList data) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (currentPage == 1) {
                            mElevatorList.clear();
                        }

                        mCurrentPage = data.getCurrentPage();
                        mTotalPage = data.getTotalPage();

                        mElevatorList.addAll(data.getList());
                        if (currentPage == 1) {
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mAdapter.notifyDataChangedAfterLoadMore(mCurrentPage < mTotalPage);
                        }
                    }

                    @Override
                    public void onFailure(String info) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toasts.show(getApplicationContext(), info);
                    }
                });
    }

    @Override
    protected int getResId() {
        return R.layout.activity_elevator_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsSearch) {
            getMenuInflater().inflate(R.menu.menu_elevator, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(ElevatorQueryActivity.getIntent(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadElevatorList(1);
    }

    @Override
    public void onItemClick(View view, int position) {
        final Elevator elevator = mElevatorList.get(position);
        startActivity(ElevatorDetailActivity.getIntent(this, elevator.getId()));
    }
}
