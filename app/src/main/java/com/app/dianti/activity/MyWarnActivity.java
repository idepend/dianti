package com.app.dianti.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.app.dianti.R;
import com.app.dianti.activity.annual.AnnualActivity;
import com.app.dianti.activity.maintenance.MaintenanceActivity;
import com.app.dianti.activity.patrol.PatrolActivity;

public class MyWarnActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.my_warn);
		super.initTitleBar("电梯档案");
		init();
	}

	private void init() {
		findViewById(R.id.patrolRow).setOnClickListener(this);
		findViewById(R.id.maintenanceRow).setOnClickListener(this);
		findViewById(R.id.annualRow).setOnClickListener(this);
		findViewById(R.id.faultRow).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.patrolRow:
			goActivity(PatrolActivity.class);
			break;
		case R.id.maintenanceRow:
			goActivity(MaintenanceActivity.class);
			break;
		case R.id.annualRow:
			goActivity(AnnualActivity.class);
			break;

		default:
			tip("正在开发中...");
			break;
		}
	}
}
