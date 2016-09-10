package com.app.dianti.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.app.dianti.R;

public class CopyOfMyWarnActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.my_warn);
		super.initTitleBar("电梯列表");
		init();
	}

	private void init() {
	}

	@Override
	public void onClick(View v) {

	}
}
