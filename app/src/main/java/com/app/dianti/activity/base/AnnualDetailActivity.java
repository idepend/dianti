package com.app.dianti.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.util.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 11:30
 */
public class AnnualDetailActivity extends CommonActivity {

    private static final String EXTRA_DATA = "data.extra";
    private static final String TAG="wj";
    public static Intent getIntent(@NonNull Context context, Map<String, Object> data) {
        final Intent intent = new Intent(context, AnnualDetailActivity.class);

        intent.putExtra(EXTRA_DATA, JSON.toJSONString(data));

        return intent;
    }

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = (ListView) findViewById(R.id.list_view);

        final String item = getIntent().getStringExtra(EXTRA_DATA);
        final Map<String, Object> row = JSON.parseObject(item);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> col = new HashMap<String, String>();
        col.put("name", "    电梯地址");
        try {
            col.put("value", row.get("an_dep_name") + "");
        } catch (Exception e) {
            col.put("value", "");
            e.printStackTrace();
        }
        data.add(col);
        Map<String, String> col2 = new HashMap<String, String>();
        col2.put("name", "    注册代码");
        col2.put("value", row.get("ele_code").toString());
        data.add(col2);
        Log.i("wj", "onCreate: "+row.get("ele_code").toString());
        Map<String, String> col3 = new HashMap<String, String>();
        col3.put("name", "应完成时间");
        if (row.get("plan_date") == null) {
            col3.put("value", "");
        } else {
            col3.put("value", DateUtils.secondToDateStr(row.get("plan_date") + ""));
        }
        data.add(col3);
        Map<String, String> col4 = new HashMap<String, String>();
        col4.put("name", "    当前状态");
        if ("1".equals(row.get("status").toString())) {
            col4.put("value", "待处理");
        } else if ("2".equals(row.get("status") + "")) {
            col4.put("value", "待整改");
        } else if ("3".equals(row.get("status") + "")) {
            col4.put("value", "已完成");
        } else {
            col4.put("value", "已完成");
        }
        data.add(col4);

        mListView.setAdapter(new ArrayAdapter<Map<String, String>>(this, R.layout.table_row_list_row, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.table_row_list_row, parent, false);

                final TextView name = (TextView) convertView.findViewById(R.id.nameText);
                final TextView value = (TextView) convertView.findViewById(R.id.valueText);

                final Map<String, String> item = getItem(position);

                name.setText(item.get("name"));
                value.setText(item.get("value"));

                return convertView;
            }
        });
    }

    @Override
    protected int getResId() {
        return R.layout.activity_maintenance_detail;
    }

}
