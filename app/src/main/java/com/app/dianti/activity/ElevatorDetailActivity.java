package com.app.dianti.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.dianti.R;
import com.app.dianti.view.adapter.TableRowAdapter;

public class ElevatorDetailActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.elevator_detail);
		super.initTitleBar("电梯详情");
		init();
	}

	private void init() {
		List<Map<String, String>> data1 = new ArrayList<Map<String, String>>();
//		data1.add("电梯名称");
//		data1.add("电梯编号");
//		data1.add("设备类型");
//		data1.add("安装地点");
//		data1.add("注册代码");
//		data1.add("生产厂家");
		
		Map<String, String> row = new HashMap<String, String>();
		row.put("name", "电梯名称");
		row.put("value", "1海宁市人民医院15号楼");
		data1.add(row);

		List<Map<String, String>> data2 = new ArrayList<Map<String, String>>();
//		data2.add("使用单位");
//		data2.add("维保单位");
//		data2.add("所属社区");
//		data2.add("社区电话");
		
		Map<String, String> row2 = new HashMap<String, String>();
		row2.put("name", "使用单位");
		row2.put("value", "海宁市人民医院15号楼");
		data2.add(row2);
		

		addRow("电梯信息", data1);
//		addRow("使用信息", data2);

	}

	public void addRow(String title, List<Map<String, String>> data) {
		LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.table_detail, null);
		rootView.addView(rowView);

		TextView rowTitle = (TextView) rowView.findViewById(R.id.rowTitle);
		rowTitle.setText(title);

		ListView listView = (ListView) rowView.findViewById(R.id.listview);

		TableRowAdapter listViewAdapter = new TableRowAdapter(getApplicationContext(), data, R.layout.table_row_list_row);
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {

			}
		});
	}

	@Override
	public void onClick(View v) {

	}
}
