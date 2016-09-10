package com.app.dianti.activity.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.app.dianti.common.AppContext;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.refactor.util.Toasts;
import com.app.dianti.util.DataUtil;
import com.app.dianti.util.DensityUtil;
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
 * @time 20:27
 */
public class InspectionAdapter extends ArrayAdapter<Map<String, Object>> {

    private final boolean mIsHistory;

    public InspectionAdapter(boolean isHistory, Context context, List<Map<String, Object>> data) {
        super(context, R.layout.mainten_table_row2, data);
        this.mIsHistory = isHistory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inspection_table_row2, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Map<String, Object> row = getItem(position);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        data.add(DataUtil.getColMap("    巡查编号", DataUtil.mapGetString(row, "code")));
        data.add(DataUtil.getColMap("    注册编码", DataUtil.mapGetString(row, "ele_code")));
        data.add(DataUtil.getColMap("    电梯名称", DataUtil.mapGetString(row, "ele_name")));
        data.add(DataUtil.getColMap("    电梯地址", DataUtil.mapGetString(row, "ele_addr")));
        String status = DataUtil.mapGetString(row, "status");
        Map<String, String> col4 = new HashMap<String, String>();
        col4.put("name", "    当前状态");
        if ("1".equals(status)) {
            col4.put("value", "待处理");
        } else if ("2".equals(status)) {
            col4.put("value", "已完成");
        } else if ("3".equals(status)) {
            col4.put("value", "待物业处理");
        } else {
            col4.put("value", status);
        }
        data.add(col4);

        data.add(DataUtil.getColMap("      发起人", DataUtil.mapGetString(row, "admin_name")));

        String createDate = DataUtil.mapGetString(row, "create_date");
        if ("".equals(createDate) || "0".equals(createDate)) {
            data.add(DataUtil.getColMap("    创建时间", ""));
        } else {
            data.add(DataUtil.getColMap("    创建时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(createDate) * 1000))));
        }

        String endDate = DataUtil.mapGetString(row, "update_date");
        if ("".equals(endDate) || "0".equals(endDate)) {
            data.add(DataUtil.getColMap("    更新时间", ""));
        } else {
            data.add(DataUtil.getColMap("    更新时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(endDate) * 1000))));
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

        final String id = DataUtil.mapGetString(row, "id");

        if (mIsHistory) {
            holder.itemView.findViewById(R.id.button_layout).setVisibility(View.GONE);
        } else {
            // 关闭
            holder.itemView.findViewById(R.id.closeInsBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirm(false, id, "");
                }
            });

            //巡查人员转给物业角色进行确认
            holder.itemView.findViewById(R.id.confirmTo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("token", AppContext.userInfo.getToken());
                    map.put("id", id);
                    String parmas = JSON.toJSONString(map);
                    OkHttpUtils.post().url(AppContext.API_ELE_INSPECTION_WAIT_CONFIRM).addParams("data", parmas)
                            .build().execute(new StringCallback() {

                        @Override
                        public void onResponse(String respData, int arg1) {
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

            //物业角色确认需要处理
            holder.itemView.findViewById(R.id.confirmDo).setOnClickListener(new View.OnClickListener() {
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

            //物业角色确认无需处理
            holder.itemView.findViewById(R.id.noDo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirm(false, row.get("id").toString(), "");
                }
            });

            if (AppContext.userInfo.getRole().startsWith("patrol")) {
                holder.itemView.findViewById(R.id.closeInsBtn).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.confirmTo).setVisibility(View.VISIBLE);

                holder.itemView.findViewById(R.id.confirmDo).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.noDo).setVisibility(View.GONE);
            } else if (AppContext.userInfo.getRole().startsWith("property")) {
                holder.itemView.findViewById(R.id.closeInsBtn).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.confirmTo).setVisibility(View.GONE);

                holder.itemView.findViewById(R.id.confirmDo).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.noDo).setVisibility(View.VISIBLE);
            } else if (AppContext.userInfo.getRole().startsWith("admin")) {
                holder.itemView.findViewById(R.id.closeInsBtn).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.confirmTo).setVisibility(View.VISIBLE);

                holder.itemView.findViewById(R.id.confirmDo).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.noDo).setVisibility(View.VISIBLE);
            } else {
                holder.itemView.findViewById(R.id.closeInsBtn).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.confirmTo).setVisibility(View.GONE);

                holder.itemView.findViewById(R.id.confirmDo).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.noDo).setVisibility(View.GONE);
            }
            //待处理
            if ("1".equals(status)) {
            }
            //已完成
            else if ("2".equals(status)) {
                holder.itemView.findViewById(R.id.closeInsBtn).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.confirmTo).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.confirmDo).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.noDo).setVisibility(View.GONE);
            }
            //待物业处理
            else if ("3".equals(status)) {
                holder.itemView.findViewById(R.id.confirmTo).setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(InspectionDetailActivity.getIntent(getContext(), row));
            }
        });

        return convertView;
    }

    private void confirm(boolean yes, String id, String reason) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", AppContext.userInfo.getToken());
        map.put("id", id);
        if (yes) {
            map.put("type", "1");
            map.put("reason", "确认需要处理");
        } else {
            //完成
            map.put("type", "0");
        }

        if (!reason.equals("")) {
            map.put("reason", reason);
        }

        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_ELE_INSPECTION_UPDATE_STATUS).addParams("data", parmas)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
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

    static class ViewHolder {

        public View itemView;

        public ViewHolder(View view) {
            itemView = view;
        }
    }
}
