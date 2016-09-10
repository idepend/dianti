package com.app.dianti.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 19:11
 */
public final class SearchListActivity extends CommonListActivity {

    private static final String EXTRA_REGISTER_CODE = "register_code.extra";
    private static final String EXTRA_ELEVATOR_NAME = "elevatorName.extra";
    private static final String EXTRA_TENEMENTS = "tenements.extra";
    private static final String EXTRA_MAINTENANCE = "maintenance.extra";

    public static Intent getIntent(@NonNull Context context, String registerCode, String elevatorName, String tenements, String maintenance) {
        final Intent intent = new Intent(context, SearchListActivity.class);
        intent.putExtra(EXTRA_REGISTER_CODE, registerCode);
        intent.putExtra(EXTRA_ELEVATOR_NAME, elevatorName);
        intent.putExtra(EXTRA_TENEMENTS, tenements);
        intent.putExtra(EXTRA_MAINTENANCE, maintenance);

        return intent;
    }

    private String mRegisterCode;
    private String mElevatorName;
    private String mTenements;
    private String mMaintenance;

    private ArrayAdapter<Map<String, String>> mAdapter;

    private final List<Map<String, String>> mMapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent = getIntent();
        mRegisterCode = intent.getStringExtra(EXTRA_REGISTER_CODE);
        mElevatorName = intent.getStringExtra(EXTRA_ELEVATOR_NAME);
        mTenements = intent.getStringExtra(EXTRA_TENEMENTS);
        mMaintenance = intent.getStringExtra(EXTRA_MAINTENANCE);

        mAdapter = new ElevatorAdapter(this, mMapList);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData(int currentPage) {

    }

    @Override
    protected ArrayAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getMenuResId() {
        return 0;
    }

    @Override
    protected boolean isHistory() {
        return true;
    }
}
