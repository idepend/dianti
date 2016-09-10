package com.app.dianti.activity.patrol;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.view.adapter.TableRowAdapter;

public class PatrolActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.elevator_detail);
		super.initTitleBar("电梯巡查");
		init();
	}

	private void init() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> col = new HashMap<String, String>();
		col.put("name", "电梯名称");
		col.put("value", "海宁市人民医院15号楼");
		data.add(col);
		Map<String, String> col2 = new HashMap<String, String>();
		col2.put("name", "巡查类型");
		col2.put("value", "A");
		data.add(col2);
		Map<String, String> col3 = new HashMap<String, String>();
		col3.put("name", "应完成时间");
		col3.put("value", "2016-05-20");
		data.add(col3);
		Map<String, String> col4 = new HashMap<String, String>();
		col4.put("name", "当前状态");
		col4.put("value", "待确认");
		data.add(col4);

		addRow("任务", data);
		addRow("任务", data);
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