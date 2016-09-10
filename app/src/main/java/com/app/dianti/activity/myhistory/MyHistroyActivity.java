package com.app.dianti.activity.myhistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.activity.base.AnnualActivity;
import com.app.dianti.activity.base.InspectionActivity;
import com.app.dianti.activity.base.RescueListActivity;
import com.app.dianti.activity.base.MaintenanceActivity;
import com.app.dianti.activity.reskju.ReskjuActivity;
import com.app.dianti.common.AppContext;

public class MyHistroyActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.my_histroy_list);
		super.initTitleBar("我的历史");
		init();
	}

	private void init() {
		findViewById(R.id.weibaoRow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MaintenanceActivity.class);
				intent.putExtra("isHistory", true);
				//type为3代表查询个人历史记录
				intent.putExtra("type", AppContext.history_my_data_type);
				startActivity(intent);
			}
		});
		findViewById(R.id.nianjianRow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AnnualActivity.class);
				intent.putExtra("isHistory", true);
				//type为3代表查询个人历史记录
				intent.putExtra("type", AppContext.history_my_data_type);
				startActivity(intent);
			}
		});
		findViewById(R.id.jiuyuanRow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RescueListActivity.class);
				intent.putExtra("isHistory", true);
				//type为3代表查询个人历史记录
				intent.putExtra("type", AppContext.history_my_data_type);
				startActivity(intent);
			}
		});
		findViewById(R.id.xunchaRow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), InspectionActivity.class);
				intent.putExtra("isHistory", true);
//				type为3代表查询个人历史记录
				intent.putExtra("type", AppContext.history_my_data_type);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {

	}
}