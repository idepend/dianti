package com.app.dianti.activity.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.activity.base.CommonListActivity;
import com.app.dianti.activity.base.ElevatorAdapter;
import com.app.dianti.common.AppContext;
import com.app.dianti.util.DateUtils;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class NoticeActivity extends CommonListActivity implements AdapterView.OnItemClickListener {

	private ArrayAdapter<Map<String, String>> mAdapter;

	private final List<Map<String, String>> mMapList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		mAdapter = new ElevatorAdapter(this, mMapList);
		super.setTitle("通知公告");
		super.onCreate(savedInstanceState);
		mListView.setOnItemClickListener(this);
	}

	private boolean mIsLoading = false;

	private long mLastRequestTime;
	@Override
	protected void loadData(final int currentPage) {
		if (mIsLoading) {
			return;
		}

		final long curr = System.currentTimeMillis();
		if (curr - mLastRequestTime < 1000) {
			return;
		}
		mLastRequestTime = curr;

		mIsLoading = true;

		OkHttpUtils.post().url(AppContext.API_NOTICE_LIST).addParams("token", AppContext.userInfo.getToken()).build().execute(new StringCallback() {

			@Override
			public void onResponse(String respData, int i) {
				mIsLoading = false;
				mSwipeRefreshLayout.setRefreshing(false);
				ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
				if (responseData.getCode().equals("200")) {
					mTotalPage = responseData.getDataInt("totalPage");
					mCurrentPage = responseData.getDataInt("currentPage");
					List<Map<String, Object>> list = responseData.getDataMap("list");
					if (list == null) {
						return;
					}

					if (currentPage == 1) {
						mMapList.clear();
					}

					for (Map<String, Object> row : list) {
						Map<String, String> rowMap = new HashMap<>();
						try {
							rowMap.put("name", (row.get("title") == null ? "" : row.get("title") + ""));
						} catch (Exception e) {
							rowMap.put("value", "");
							e.printStackTrace();
						}

						try {
							rowMap.put("desc", "发布时间："+DateUtils.secondToDateStr2(row.get("create_date") == null ? "" : row.get("create_date") + ""));
						} catch (Exception e) {
							rowMap.put("value", "");
							e.printStackTrace();
						}

						rowMap.put("id", row.get("id").toString());

						mMapList.add(rowMap);
					}

					mAdapter.notifyDataSetChanged();

				} else {
					Toast.makeText(NoticeActivity.this, "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onError(Call call, Exception e, int i) {
				mIsLoading = false;
				mSwipeRefreshLayout.setRefreshing(false);
				Toast.makeText(NoticeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}

		});
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent intent = new Intent(this, NoticeDetailActivity.class);
		intent.putExtra("id", mMapList.get(i).get("id"));
		startActivity(intent);
	}
}