package com.app.dianti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.view.adapter.ElevatorAdapter;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ElevatorListActivity extends BaseActivity implements OnClickListener {

	private int totalPage;
	private int currentPage;

	private ListView listView;
	private ElevatorAdapter listViewAdapter;

	private List<Map<String, String>> listViewData = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.elevator_list);
		super.initTitleBar("电梯列表");

		init();
	}

	private void init() {

		findViewById(R.id.right_btn).setOnClickListener(this);

		listView = (ListView) findViewById(R.id.listview);

		listViewAdapter = new ElevatorAdapter(null, getApplicationContext(), listViewData, R.layout.elevator_list_row);
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {
				Intent intent = new Intent(getApplicationContext(), ElevatorDetailListActivity.class);
				intent.putExtra("id", listViewData.get(itemPosition).get("id"));
				startActivity(intent);
			}
		});

		initData();
		// initData2();
	}

	private void initData2() {
		for (int i = 0; i < 3; i++) {
			Map<String, String> rowMap = new HashMap<String, String>();
			rowMap.put("name", "海宁市人民医院15号楼");
			rowMap.put("desc", "海宁市人民医院");
			listViewData.add(rowMap);
		}

		listViewAdapter.notifyDataSetChanged();
	}

	private void initData() {
		OkHttpUtils.post().url(AppContext.API_LOGIN_DANGAN).addParams("token", AppContext.userInfo.getToken()).addParams("pageNum", 1 + "").build().execute(new StringCallback() {

			@Override
			public void onResponse(String respData, int arg1) {
				ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
				if (responseData.getCode().equals("200")) {
					totalPage = responseData.getDataInt("totalPage");
					List<Map<String, Object>> list = responseData.getDataMap("list");
					if (list == null) {
						return;
					}

					for (Map<String, Object> row : list) {
						Map<String, String> rowMap = new HashMap<String, String>();
						try {
							rowMap.put("name", (row.get("ele_addr") == null ? "" : row.get("ele_addr") + "") + " " + (row.get("ele_name") == null ? "" : row.get("ele_name") + ""));
						} catch (Exception e) {
							rowMap.put("value", "");
							e.printStackTrace();
						}
						try {
							rowMap.put("desc", (row.get("use_dep_name") == null ? "" : row.get("use_dep_name") + ""));
						} catch (Exception e) {
							rowMap.put("value", "");
							e.printStackTrace();
						}

						rowMap.put("id", row.get("id").toString());

						listViewData.add(rowMap);
					}

					listViewAdapter.notifyDataSetChanged();

				} else {
					tip("加载数据失败!请返回后重试。");
				}
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				System.out.println("error");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.right_btn:
				goActivity(ElevatorQueryActivity.class);
				break;

			default:
				break;
		}
	}
}
