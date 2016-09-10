package com.app.dianti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.app.dianti.R;
import com.app.dianti.activity.base.AnnualActivity;
import com.app.dianti.activity.base.InspectionActivity;
import com.app.dianti.activity.base.RescueListActivity;
import com.app.dianti.activity.dangan.EleBaseInfoActivity;
import com.app.dianti.activity.dangan.EleRealMonitorActivity;
import com.app.dianti.activity.base.MaintenanceActivity;
import com.app.dianti.activity.reskju.ReskjuActivity;

public class ElevatorDetailListActivity extends BaseActivity implements OnClickListener {

	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.elevator_dossier_detail);
		super.initTitleBar("电梯档案");

		Intent intent = this.getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			id = extras.getString("id", "");
		}

		init();
		initData();
	}

	private void init() {
		findViewById(R.id.realMonitorRow).setOnClickListener(this);
		findViewById(R.id.weibaoRow).setOnClickListener(this);
		findViewById(R.id.nianjianRow).setOnClickListener(this);
		findViewById(R.id.jiuyuanRow).setOnClickListener(this);
		findViewById(R.id.detailInfoRow).setOnClickListener(this);
		findViewById(R.id.realMonitorRow).setOnClickListener(this);
		findViewById(R.id.xunchaRow).setOnClickListener(this);
	}

	private void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.realMonitorRow:
			Intent intent0 = new Intent(getApplicationContext(), EleRealMonitorActivity.class);
			intent0.putExtra("id", id);
			startActivity(intent0);
			break;
		case R.id.weibaoRow:
			Intent intent = new Intent(getApplicationContext(), MaintenanceActivity.class);
			intent.putExtra("isHistory", true);
			intent.putExtra("type", 1);
			intent.putExtra("id", id);
			startActivity(intent);
			break;
		case R.id.nianjianRow:
			Intent intent2 = new Intent(getApplicationContext(), AnnualActivity.class);
			intent2.putExtra("isHistory", true);
			intent2.putExtra("type", 1);
			intent2.putExtra("id", id);
			startActivity(intent2);
			break;
		case R.id.jiuyuanRow:
			Intent intent3 = new Intent(getApplicationContext(), RescueListActivity.class);
			intent3.putExtra("isHistory", true);
			intent3.putExtra("type", 1);
			intent3.putExtra("id", id);
			startActivity(intent3);
			break;
		case R.id.detailInfoRow:
			Intent intent4 = new Intent(getApplicationContext(), EleBaseInfoActivity.class);
			intent4.putExtra("isHistory", true);
			intent4.putExtra("type", 1);
			intent4.putExtra("id", id);
			startActivity(intent4);
			break;
		case R.id.xunchaRow:
			Intent intent5 = new Intent(getApplicationContext(), InspectionActivity.class);
			intent5.putExtra("isHistory", true);
			intent5.putExtra("type", 1);
			intent5.putExtra("id", id);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
}
