package com.app.dianti.refactor.activity.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.dianti.R;
import com.app.dianti.activity.base.AnnualActivity;
import com.app.dianti.activity.base.InspectionActivity;
import com.app.dianti.activity.base.MaintenanceActivity;
import com.app.dianti.activity.base.RescueListActivity;
import com.app.dianti.activity.dangan.EleBaseInfoActivity;
import com.app.dianti.activity.dangan.EleRealMonitorActivity;
import com.app.dianti.refactor.activity.base.CommonToolbarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ElevatorDetailActivity extends CommonToolbarActivity {

    private static final String EXTRA_ELEVATOR_ID = "elevator_id.extra";

    public static Intent getIntent(@NonNull Context context, @NonNull String elevatorId) {
        final Intent intent = new Intent(context, ElevatorDetailActivity.class);
        intent.putExtra(EXTRA_ELEVATOR_ID, elevatorId);
        return intent;
    }

    private String mId;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        final Intent intent = getIntent();
        mId = intent.getStringExtra(EXTRA_ELEVATOR_ID);
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
        return R.layout.activity_elevator_detail;
    }

    @OnClick({R.id.ssjk_text_view, R.id.dtxx_text_view, R.id.dtxc_text_view, R.id.dtnj_text_view, R.id.dtwb_text_view, R.id.yjjy_text_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ssjk_text_view:
                Intent intent0 = new Intent(getApplicationContext(), EleRealMonitorActivity.class);
                intent0.putExtra("id", mId);
                startActivity(intent0);
                break;
            case R.id.dtxx_text_view:
                Intent intent4 = new Intent(getApplicationContext(), EleBaseInfoActivity.class);
                intent4.putExtra("isHistory", true);
                intent4.putExtra("type", 1);
                intent4.putExtra("id", mId);
                startActivity(intent4);
                break;
            case R.id.dtxc_text_view:
                Intent intent5 = new Intent(getApplicationContext(), InspectionActivity.class);
                intent5.putExtra("isHistory", true);
                intent5.putExtra("type", 1);
                intent5.putExtra("id", mId);
                startActivity(intent5);
                break;
            case R.id.dtnj_text_view:
                Intent intent2 = new Intent(getApplicationContext(), AnnualActivity.class);
                intent2.putExtra("isHistory", true);
                intent2.putExtra("type", 1);
                intent2.putExtra("id", mId);
                startActivity(intent2);

                break;
            case R.id.dtwb_text_view:
                Intent intent = new Intent(getApplicationContext(), MaintenanceActivity.class);
                intent.putExtra("isHistory", true);
                intent.putExtra("type", 1);
                intent.putExtra("id", mId);
                startActivity(intent);

                break;
            case R.id.yjjy_text_view:
                Intent intent3 = new Intent(getApplicationContext(), RescueListActivity.class);
                intent3.putExtra("isHistory", true);
                intent3.putExtra("type", 1);
                intent3.putExtra("id", mId);
                startActivity(intent3);

                break;
        }
    }
}
