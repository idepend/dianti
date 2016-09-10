package com.app.dianti.activity.base;

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
import com.app.dianti.activity.reskju.StartPeopleRescueActivity;
import com.app.dianti.activity.reskju.StatementActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.entity.ResureListEntity;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.DataUtil;
import com.app.dianti.util.DateUtils;
import com.app.dianti.util.DensityUtil;
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
 * @time 17:21
 */
public class RescueListAdapter extends ArrayAdapter<ResureListEntity.ListEntity> {

    private boolean mIsHistory;
    private Handler updateHandler;

    public RescueListAdapter(boolean isHistory, Handler updateHandler, Context context, List<ResureListEntity.ListEntity> data) {
        super(context, R.layout.reskju_table_row2, data);
        mIsHistory = isHistory;
        this.updateHandler = updateHandler;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reskju_table_row2, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ResureListEntity.ListEntity row = getItem(position);
        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        data.add(DataUtil.getColMap("    注册编码", row.getEle_code()));
        data.add(DataUtil.getColMap("    电梯名称", row.getEle_name()));
        data.add(DataUtil.getColMap("    电梯地址", row.getEle_addr()));

        String type = row.getType();
        String typeName = "";
        if ("1".equals(type)) {
            typeName = "门区外停梯故障";
        } else if ("2".equals(type)) {
            typeName = "电梯超速运行故障";
        } else if ("3".equals(type)) {
            typeName = "电梯冲顶";
        } else if ("4".equals(type)) {
            typeName = "电梯蹲底";
        } else if ("5".equals(type)) {
            typeName = "电梯运行中开门故障";
        } else if ("6".equals(type)) {
            typeName = "电梯困人故障";
        } else if ("7".equals(type)) {
            typeName = "电梯电源故障";
        } else if ("8".equals(type)) {
            typeName = "电梯安全回路故障";
        } else if ("9".equals(type)) {
            typeName = "开门走楼";
        } else if ("10".equals(type)) {
            typeName = "用户按下(求救按钮)";
        } else if ("100".equals(type)) {
            typeName = "维保转故障";
        } else if ("101".equals(type)) {
            typeName = "巡查转故障";
        } else if ("200".equals(type)) {
            typeName = "手工新增";
        }else{
            typeName = type;
        }
        data.add(DataUtil.getColMap("    故障类型", typeName));

        String status = row.getStatus();
        String statusName = null;
        if ("1".equals(status)) {
            statusName = "待处理";
        } else if ("2".equals(status)) {
            statusName = "处理中";
        } else if ("3".equals(status)) {
            statusName = "延迟处理";
        } else if ("4".equals(status)) {
            statusName = "已处理";
        } else if ("5".equals(status)) {
            statusName = "待物业确认";
        } else{
            statusName = status;
        }
        data.add(DataUtil.getColMap("    当前状态", statusName));
        data.add(DataUtil.getColMap("    发生时间", DateUtils.secondToDateStr2(row.getCreate_date())));
        data.add(DataUtil.getColMap("    更新时间", DateUtils.secondToDateStr2(row.getLast_recv_date() + "")));

        TextView rowTitle = (TextView) convertView.findViewById(R.id.rowTitle);
        rowTitle.setText("任务");
        LinearLayout contextView = (LinearLayout) convertView.findViewById(R.id.contextView);
        contextView.removeAllViews();

        for (Map<String, String> map : data) {

            View rowColView = LayoutInflater.from(getContext()).inflate(R.layout.table_row_list_row, contextView, false);
            TextView nameText = (TextView) rowColView.findViewById(R.id.nameText);
            nameText.setText(map.get("name"));
            TextView valueText = (TextView) rowColView.findViewById(R.id.valueText);
            valueText.setText(map.get("value"));
            contextView.addView(rowColView);
        }
        if (mIsHistory) {
            convertView.findViewById(R.id.weibaoDoView).setVisibility(View.GONE);
            convertView.findViewById(R.id.confirmDoView).setVisibility(View.GONE);
        } else {
            // 维保人员--正常结单按钮
            convertView.findViewById(R.id.endBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), StatementActivity.class);
                    Bundle bundle = new Bundle(); // 创建Bundle对象
                    bundle.putString("id", row.getId().toString()); // 装入数据
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });
            // 维保人员--困人救援
            convertView.findViewById(R.id.peopleRescue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), StartPeopleRescueActivity.class);
                    Bundle bundle = new Bundle(); // 创建Bundle对象
                    bundle.putString("id", row.getId().toString()); // 装入数据
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });

            // 物业确认
            convertView.findViewById(R.id.confirmDo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   confirm(row.getId().toString());
                }
            });

            if (AppContext.userInfo.getRole().startsWith("maintain")) {
                convertView.findViewById(R.id.weibaoDoView).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.confirmDoView).setVisibility(View.GONE);
            } else if (AppContext.userInfo.getRole().startsWith("property")) {
                convertView.findViewById(R.id.weibaoDoView).setVisibility(View.GONE);
                convertView.findViewById(R.id.confirmDoView).setVisibility(View.VISIBLE);
            } else if (AppContext.userInfo.getRole().startsWith("admin")) {
                convertView.findViewById(R.id.weibaoDoView).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.confirmDoView).setVisibility(View.VISIBLE);
            } else {
                convertView.findViewById(R.id.weibaoDoView).setVisibility(View.GONE);
                convertView.findViewById(R.id.confirmDoView).setVisibility(View.GONE);
            }

            //困人救援
            if("6".equals(type)){
                convertView.findViewById(R.id.endBtn).setVisibility(View.GONE);
                convertView.findViewById(R.id.peopleRescue).setVisibility(View.VISIBLE);
            }else{
                convertView.findViewById(R.id.endBtn).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.peopleRescue).setVisibility(View.GONE);
            }
        }

        /**
         * 进入故障详情页
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(RescueDetailActivity.getIntent(getContext(), row));
            }
        });

        return convertView;
    }

    static class ViewHolder {

        public View itemView;

        public ViewHolder(View view) {
            itemView = view;
        }
    }

    private void confirm(final String id){
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
                            confirm(true, id, reasonEditText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
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

        map.put("startTime", "0");
        map.put("endTime", "0");

        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_ELE_RESCUE_CONFIRM).addParams("data", parmas)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new RefreshEvent());
                    updateHandler.sendEmptyMessage(0);
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
}
