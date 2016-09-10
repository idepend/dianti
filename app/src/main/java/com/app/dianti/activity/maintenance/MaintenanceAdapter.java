package com.app.dianti.activity.maintenance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.base.MaintenanceDetailActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.refactor.util.Toasts;
import com.app.dianti.util.DataUtil;
import com.app.dianti.util.DateUtils;
import com.app.dianti.util.DensityUtil;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 13:14
 */
public class MaintenanceAdapter extends ArrayAdapter<Map<String, Object>> {

    private final boolean mIsHistory;
    private Context context;
    private Handler updateHandler;

    public MaintenanceAdapter(Context context, Handler updateHandler, List<Map<String, Object>> mapList, boolean isHistory) {
        super(context, R.layout.mainten_table_row2, mapList);
        this.context = context;
        this.mIsHistory = isHistory;
        this.updateHandler = updateHandler;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mainten_table_row2, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Map<String, Object> row = getItem(position);

        Logs.e(row.toString());

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> colCode = new HashMap<String, String>();
        colCode.put("name", "    注册编码");
        colCode.put("value", DataUtil.mapGetString(row, "code"));
        data.add(colCode);

        data.add(DataUtil.getColMap("    电梯名称", DataUtil.mapGetString(row, "name")));
        data.add(DataUtil.getColMap("    电梯地址", DataUtil.mapGetString(row, "address")));


        Map<String, String> col2 = new HashMap<String, String>();
        col2.put("name", "    维保类型");
        col2.put("value", DataUtil.mapGetString(row, "typeName"));
        data.add(col2);
        Map<String, String> col3 = new HashMap<String, String>();
        col3.put("name", "    计划时间");
        if (row.get("planTime") == null) {
            col3.put("value", "");
        } else {
            col3.put("value", DateUtils.secondToDateStr2(DataUtil.mapGetString(row, "planTime")));
        }
        data.add(col3);
        Map<String, String> col4 = new HashMap<String, String>();
        col4.put("name", "    当前状态");
        String status = DataUtil.mapGetString(row, "status");
        if ("1".equals(status)) {
            col4.put("value", "待处理");
        } else if ("2".equals(status)) {
            col4.put("value", "已签到,待维保");
        } else if ("3".equals(status)) {
            col4.put("value", "已完成");
        } else if ("4".equals(status)) {
            col4.put("value", "待物业处理");
        } else {
            col4.put("value", status);
        }
        data.add(col4);

        String createDate = DataUtil.mapGetString(row, "createTime");
        if("".equals(createDate) || "0".equals(createDate)){
            data.add(DataUtil.getColMap("    创建时间", ""));
        }else{
            data.add(DataUtil.getColMap("    创建时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(createDate)*1000))));
        }

        String endDate = DataUtil.mapGetString(row, "endTime");
        if("".equals(endDate) || "0".equals(endDate)){
            data.add(DataUtil.getColMap("    更新时间", ""));
        }else{
            data.add(DataUtil.getColMap("    更新时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(endDate)*1000))));
        }

        TextView rowTitle = (TextView) holder.itemView.findViewById(R.id.rowTitle);
        rowTitle.setText("任务");

        LinearLayout contextView = (LinearLayout) holder.itemView.findViewById(R.id.contextView);
        contextView.removeAllViews();

        for (Map<String, String> map : data) {
            View rowColView = LayoutInflater.from(getContext()).inflate(R.layout.table_row_list_row, null);
            TextView nameText = (TextView) rowColView.findViewById(R.id.nameText);
            nameText.setText(map.get("name"));
            TextView valueText = (TextView) rowColView.findViewById(R.id.valueText);
            valueText.setText(map.get("value"));
            contextView.addView(rowColView);
        }

        if (mIsHistory) {
            convertView.findViewById(R.id.button_layout).setVisibility(View.GONE);
        }

        // 我要开始维保
        holder.itemView.findViewById(R.id.start_maintenance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StartMaintenanceActivity.class);
                Bundle bundle = new Bundle(); // 创建Bundle对象
                bundle.putString("id", row.get("id").toString()); // 装入数据
                bundle.putString("maintenTypeId", row.get("typeId").toString()); // 装入数据
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

        //物业角色确认需要维保
        holder.itemView.findViewById(R.id.confirmMaintenance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText reasonEditText = new EditText(getContext());
                reasonEditText.setHeight(DensityUtil.dip2px(getContext(), 80));
                reasonEditText.setSingleLine(false);
                reasonEditText.setHint("请输入描述");
                new AlertDialog.Builder(getContext()).setTitle("确认")
                        .setView(reasonEditText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = reasonEditText.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getContext(), "请输入描述！" + input, Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    confirm(true, row.get("id").toString(), reasonEditText.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        //物业角色确认无需维保
        holder.itemView.findViewById(R.id.noMaintenance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText reasonEditText = new EditText(getContext());
                reasonEditText.setHeight(DensityUtil.dip2px(getContext(), 80));
                reasonEditText.setSingleLine(false);
                reasonEditText.setHint("请输入描述");
                new AlertDialog.Builder(getContext()).setTitle("确认")
                        .setView(reasonEditText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = reasonEditText.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getContext(), "请输入描述！" + input, Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    confirm(false, row.get("id").toString(), reasonEditText.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(MaintenanceDetailActivity.getIntent(getContext(), row));
            }
        });

        if(AppContext.userInfo.getRole().startsWith("maintain")){
            holder.itemView.findViewById(R.id.start_maintenance).setVisibility(View.VISIBLE);

            holder.itemView.findViewById(R.id.confirmMaintenance).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.noMaintenance).setVisibility(View.GONE);
        }else if(AppContext.userInfo.getRole().startsWith("property")){
            holder.itemView.findViewById(R.id.start_maintenance).setVisibility(View.GONE);

            holder.itemView.findViewById(R.id.confirmMaintenance).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.noMaintenance).setVisibility(View.VISIBLE);
        }else if(AppContext.userInfo.getRole().startsWith("admin")){
            holder.itemView.findViewById(R.id.start_maintenance).setVisibility(View.VISIBLE);

            holder.itemView.findViewById(R.id.confirmMaintenance).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.noMaintenance).setVisibility(View.VISIBLE);
        }else{
            holder.itemView.findViewById(R.id.start_maintenance).setVisibility(View.GONE);

            holder.itemView.findViewById(R.id.confirmMaintenance).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.noMaintenance).setVisibility(View.GONE);
        }

        if ("1".equals(status) || "2".equals(status)) {
            holder.itemView.findViewById(R.id.start_maintenance).setVisibility(View.VISIBLE);
        } else {
            holder.itemView.findViewById(R.id.start_maintenance).setVisibility(View.GONE);
        }

        return convertView;
    }

    private void confirm(boolean yes, String id, String reason){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("id", id);
        if(yes){
            map.put("type", "1");
            map.put("reason", "确认需要处理");
        }else{
            //完成
            map.put("type", "0");
        }

        if (!reason.equals("")) {
            map.put("reason", reason);
        }

        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_MAINTENANCE_CONFIRM)
                .addParams("data", parmas)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    Toasts.show(context, "操作成功!");
                    EventBus.getDefault().post(new RefreshEvent());
                } else {
                    Toasts.show(context, "操作失败!");
                }
                updateHandler.sendEmptyMessage(0);
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                Toasts.show(context, "操作失败!");
            }
        });
    }
    static class ViewHolder {

        public View itemView;

        public ViewHolder(View view) {
            itemView = view;
        }
    }


}
