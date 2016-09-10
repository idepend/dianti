package com.app.dianti.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.dianti.R;

@SuppressWarnings("unused")
public class ElevatorAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> listViewData;
	private int rowLayout;
	private Handler handler;

	public ElevatorAdapter(Handler handler, Context context, List<Map<String, String>> listViewData, int rowLayout) {
		this.handler = handler;
		this.context = context;
		this.listViewData = listViewData;
		this.rowLayout = rowLayout;
	}

	@Override
	public int getCount() {
		return listViewData.size();
	}

	@Override
	public Object getItem(int position) {
		return listViewData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		try {
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(rowLayout, null);
			}

			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(listViewData.get(position).get("name"));

			TextView desc = (TextView) view.findViewById(R.id.desc);
			desc.setText(listViewData.get(position).get("desc"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
}
