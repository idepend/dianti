package com.app.dianti.refactor.activity.maintenance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.app.dianti.R;
import com.app.dianti.refactor.activity.base.CommonToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MaintenanceActivity extends CommonToolbarActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static Intent getIntent(@NonNull Context context) {
        return getIntent(context, 2, null);
    }

    private static final String EXTRA_TYPE = "type.extra";
    private static final String EXTRA_ID = "id.extra";

    public static Intent getIntent(@NonNull Context context, int type, String id) {
        final Intent intent = new Intent(context, MaintenanceActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mType;
    private String mId;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        final Intent intent = getIntent();
        mType = intent.getIntExtra(EXTRA_TYPE, 2);
        mId = intent.getStringExtra(EXTRA_ID);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_elevator_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maintenance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            // TODO: 16/7/4 跳转到维保添加界面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }
}
