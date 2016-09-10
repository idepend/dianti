package com.app.dianti.refactor.activity.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.dianti.R;
import com.app.dianti.refactor.activity.base.CommonToolbarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public final class HistoryListActivity extends CommonToolbarActivity {

    public static Intent getIntent(@NonNull Context context) {
        return new Intent(context, HistoryListActivity.class);
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);

    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected int getResId() {
        return R.layout.activity_history_list;
    }

    @OnClick({R.id.dtxc_text_view, R.id.dtnj_text_view, R.id.dtwb_text_view, R.id.yjjy_text_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dtxc_text_view:
                break;
            case R.id.dtnj_text_view:
                break;
            case R.id.dtwb_text_view:
                break;
            case R.id.yjjy_text_view:
                break;
        }
    }
}
