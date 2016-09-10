package com.app.dianti.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dianti.R;
import com.app.dianti.util.ExitActivityUtil;
import com.app.dianti.util.StatusBarCompat;

public class BaseActivity extends AppCompatActivity {
	
	protected View leftBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitActivityUtil.getInstance().addActivity(this);
		// 无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		StatusBarCompat.compat(this,getResources().getColor(R.color.main_color));
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);

		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.main_color);// 状态栏设置为透明色

		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setNavigationBarTintResource(Color.TRANSPARENT);// 导航栏设置为透明色*/
	}

	protected void initTitleBar(String title) {
		leftBtn = findViewById(R.id.left_btn);
		if (leftBtn != null) {

			leftBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		TextView titleBarName = (TextView) findViewById(R.id.titleBarName);
		if (titleBarName != null) {
			titleBarName.setText(title);
		}
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;// 状态栏
		final int bits1 = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;// 导航栏
		if (on) {
			winParams.flags |= bits1;
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits1;
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	protected void goActivity(Class<?> cla) {
		startActivity(new Intent(getApplicationContext(), cla));
	}

	protected View getRootView(Activity context) {
		return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
	}

	protected void tip(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
