package com.app.dianti.refactor.activity.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.app.dianti.R;
import com.app.dianti.refactor.activity.base.CommonToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ElevatorQueryActivity extends CommonToolbarActivity {

    public static Intent getIntent(@NonNull Context context) {
        return new Intent(context, ElevatorQueryActivity.class);
    }

    @BindView(R.id.register_code_edit)
    EditText mRegisterCodeEdit;
    @BindView(R.id.elevator_name_edit)
    EditText mElevatorNameEdit;
    @BindView(R.id.tenements_edit)
    EditText mTenementsEdit;
    @BindView(R.id.maintenance_edit)
    EditText mMaintenanceEdit;

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
        return R.layout.activity_elevator_query;
    }

    @OnClick(R.id.query_button)
    public void onClick() {
        final String registerCode = mRegisterCodeEdit.getText().toString();
        final String elevatorName = mElevatorNameEdit.getText().toString();
        final String tenements = mTenementsEdit.getText().toString();
        final String maintenance = mMaintenanceEdit.getText().toString();

        startActivity(com.app.dianti.activity.base.ElevatorListActivity.getIntent(this, true, registerCode, elevatorName, tenements, maintenance));
    }
}
