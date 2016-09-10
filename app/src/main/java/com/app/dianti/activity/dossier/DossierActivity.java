package com.app.dianti.activity.dossier;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;

public class DossierActivity extends BaseActivity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.common_webview);
		super.initTitleBar("电梯档案");
		init();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init() {
		leftBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (webView.canGoBack()) {
					webView.goBack();// 返回上一页面
					return;
				}
				finish();
			}
		});

		webView = (WebView) findViewById(R.id.commonWebView);
		// 启用支持javascript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		webView.loadUrl("http://192.168.1.14:8088/hh/index.html");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();// 返回上一页面
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
