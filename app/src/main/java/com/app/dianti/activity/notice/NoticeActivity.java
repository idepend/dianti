package com.app.dianti.activity.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.adapter.NoticeAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends BaseActivity implements OnClickListener {

	private ListView noticeList;
private List<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.bulletin_list);
		super.initTitleBar("通知公告");
//		init();
		initView();
		initData();
	}

	private void initData() {
		list=new ArrayList<String>();
		for (int i=0; i < 10; i++){
			list.add(i+"");
		}
		NoticeAdapter adapter=new NoticeAdapter(this,list);
		noticeList.setAdapter(adapter);
		noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent=new Intent();
				intent.setClass(NoticeActivity.this,NoticeDetailActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		noticeList= (ListView) findViewById(R.id.notice_lsit);
	}

//	private void init() {
//		List<Map<String, String>> data1 = new ArrayList<Map<String, String>>();
//		Map<String, String> col = new HashMap<String, String>();
//		col.put("name", "标题");
//		col.put("value", "注意安全");
//		data1.add(col);
//		Map<String, String> col1 = new HashMap<String, String>();
//		col1.put("name", "时间");
//		col1.put("value", "2016-05-01");
//		data1.add(col1);
//		Map<String, String> col2 = new HashMap<String, String>();
//		col2.put("name", "内容");
//		col2.put("value", "如果你无法简洁的表达你的想法，那只说明你还不够了解它。如果你无法简洁的表达你的想法，那只说明你还不够了解它。如果你无法简洁的表达你的想法，那只说明你还不够了解它。");
//		data1.add(col2);
//
//		addRow("公告", data1);
//	}
//
//	public void addRow(String title, List<Map<String, String>> data) {
//		LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.table_detail, null);
//		rootView.addView(rowView);
//
//		TextView rowTitle = (TextView) rowView.findViewById(R.id.rowTitle);
//		rowTitle.setText(title);
//
//		ListView listView = (ListView) rowView.findViewById(R.id.listview);
//
//		TableRowAdapter listViewAdapter = new TableRowAdapter(getApplicationContext(), data, R.layout.table_row_list_row);
//		listView.setAdapter(listViewAdapter);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {
//
//			}
//		});
//	}

	@Override
	public void onClick(View v) {

	}
}