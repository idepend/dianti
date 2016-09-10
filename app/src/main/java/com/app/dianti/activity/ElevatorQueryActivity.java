package com.app.dianti.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.app.dianti.R;

public class ElevatorQueryActivity extends BaseActivity implements OnClickListener {

    private EditText mRegisterCodeEdit;
    private EditText mElevatorNameEdit;
    private EditText mTenementsEdit;
    private EditText mMaintenanceEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.elevator_query);
        super.initTitleBar("电梯查询");

        mRegisterCodeEdit = (EditText) findViewById(R.id.register_code_edit);
        mElevatorNameEdit = (EditText) findViewById(R.id.elevator_name_edit);
        mTenementsEdit = (EditText) findViewById(R.id.tenements_edit);
        mMaintenanceEdit = (EditText) findViewById(R.id.maintenance_edit);
        init();
    }

    private void init() {
        findViewById(R.id.queryBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String registerCode = mRegisterCodeEdit.getText().toString();
        final String elevatorName = mElevatorNameEdit.getText().toString();
        final String tenements = mTenementsEdit.getText().toString();
        final String maintenance = mMaintenanceEdit.getText().toString();

        startActivity(com.app.dianti.activity.base.ElevatorListActivity.getIntent(this, true, registerCode, elevatorName, tenements, maintenance));
    }
}
