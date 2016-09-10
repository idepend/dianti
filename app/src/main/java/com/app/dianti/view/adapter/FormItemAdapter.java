package com.app.dianti.view.adapter;

import java.util.List;
import java.util.Map;

import com.app.dianti.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FormItemAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;

	public FormItemAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		Map<String, Object> data = list.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.maintenance_item_detail, null);
		CheckBox check = (CheckBox) view.findViewById(R.id.checkBox1);
		TextView name = (TextView) view.findViewById(R.id.proj_name);
		TextView need = (TextView) view.findViewById(R.id.proj_need);
		TextView status = (TextView) view.findViewById(R.id.status);
		check.setChecked((Boolean) data.get("check"));
		name.setText((String) data.get("proj_name"));
		need.setText((String) data.get("proj_need"));
		status.setText((String) data.get("status"));
		return view;
	}

}
