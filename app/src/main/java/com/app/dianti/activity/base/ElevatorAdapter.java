package com.app.dianti.activity.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.dianti.R;

import java.util.List;
import java.util.Map;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 15:39
 */
public class ElevatorAdapter extends ArrayAdapter<Map<String, String>> {

    public ElevatorAdapter(Context context, List<Map<String, String>> data) {
        super(context, R.layout.elevator_list_row, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elevator_list_row, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Map<String, String> item = getItem(position);

        holder.mNameTextView.setText(item.get("name"));

        holder.mDescTextView.setText(item.get("desc"));

        return convertView;
    }

    static class ViewHolder {

        public View itemView;
        TextView mNameTextView;
        TextView mDescTextView;

        public ViewHolder(View view) {
            this.itemView = view;
            mNameTextView = (TextView) view.findViewById(R.id.name);
            mDescTextView = (TextView) view.findViewById(R.id.desc);
        }
    }
}
