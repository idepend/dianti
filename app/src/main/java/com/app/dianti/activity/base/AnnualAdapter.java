package com.app.dianti.activity.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.event.FinishAnnualEvent;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.DateUtils;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 14:57
 */
public final class AnnualAdapter extends ArrayAdapter<Map<String, Object>> {

    private final boolean mIsHistory;

    public AnnualAdapter(boolean isHistory, Context context, List<Map<String, Object>> data) {
        super(context, R.layout.annual_table_row2, data);
        this.mIsHistory = isHistory;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.annual_table_row2, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Map<String, Object> row = getItem(position);
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            Map<String, String> col = new HashMap<String, String>();
            col.put("name", "    电梯地址");
            try {
                col.put("value", row.get("ele_address") + "");
            } catch (Exception e) {
                col.put("value", "");
                e.printStackTrace();
            }
            data.add(col);
            Map<String, String> col2 = new HashMap<String, String>();
            col2.put("name", "    注册代码");
            col2.put("value", row.get("ele_code").toString());
            data.add(col2);
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

            TextView rowTitle = (TextView) holder.itemView.findViewById(R.id.rowTitle);
            LinearLayout contextView = (LinearLayout) holder.itemView.findViewById(R.id.contextView);
            contextView.removeAllViews();

            rowTitle.setText("任务");
            for (Map<String, String> map : data) {
                View rowColView = LayoutInflater.from(getContext()).inflate(R.layout.table_row_list_row, contextView, false);
                TextView nameText = (TextView) rowColView.findViewById(R.id.nameText);
                nameText.setText(map.get("name"));
                TextView valueText = (TextView) rowColView.findViewById(R.id.valueText);
                valueText.setText(map.get("value"));
                contextView.addView(rowColView);
            }

            final String id = row.get("id").toString();
            if (mIsHistory) {
                convertView.findViewById(R.id.button_layout).setVisibility(View.GONE);
            } else {
                // 上传年检照片
                convertView.findViewById(R.id.row_btn1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new FinishAnnualEvent(id));
                    }
                });

                convertView.findViewById(R.id.row_btn2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("token", AppContext.userInfo.getToken());
                        map.put("id", id);
                        String parmas = JSON.toJSONString(map);
                        OkHttpUtils.post().url(AppContext.API_ELE_YEAR_REFORM).addParams("token", AppContext.userInfo.getToken()).addParams("data", parmas).build().execute(new StringCallback() {
                            @Override
                            public void onResponse(String respData, int arg1) {
                                Logs.e(respData + "");

                                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                                if (responseData.getCode().equals("200")) {
                                    Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                                    EventBus.getDefault().post(new RefreshEvent());
                                } else {
                                    Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Call arg0, Exception arg1, int arg2) {
                                Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getContext().startActivity(AnnualDetailActivity.getIntent(getContext(), row));
                }
            });


        }catch (Exception e){
            //
            convertView.findViewById(R.id.button_layout).setVisibility(View.GONE);

        }

        return convertView;
    }

    static class ViewHolder {

        public View itemView;

        public ViewHolder(View view) {
            itemView = view;
        }
    }
}
