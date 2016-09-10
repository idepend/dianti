package com.app.dianti.refactor.activity.inspection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.dianti.R;
import com.app.dianti.refactor.activity.base.CommonToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @user MycroftWong
 * @date 16/7/5
 * @time 21:00
 */
public final class InspectionActivity extends CommonToolbarActivity {

    public static Intent getIntent(@NonNull Context context) {
        return new Intent(context, InspectionActivity.class);
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_elevator_list;
    }

}
