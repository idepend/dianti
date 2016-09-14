package com.app.dianti.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.ImageMatrixActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.entity.ResureListEntity;
import com.app.dianti.util.DataUtil;
import com.app.dianti.util.DateUtils;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static java.lang.Integer.parseInt;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 11:30
 */
public class RescueDetailActivity extends CommonActivity {

    private static final String EXTRA_DATA = "data.extra";
    private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    private ArrayAdapter<Map<String, String>> listAdapter;
    private ImageView imageView;
    private boolean isBack;
    private static final String TAG ="wj";
    private String startTime;
    private String endTime;

    public static Intent getIntent(@NonNull Context context, ResureListEntity.ListEntity data) {
        final Intent intent = new Intent(context, RescueDetailActivity.class);

        intent.putExtra(EXTRA_DATA, JSON.toJSONString(data));

        return intent;
    }

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = (ListView) findViewById(R.id.list_view);

        final String item = getIntent().getStringExtra(EXTRA_DATA);

        final ResureListEntity.ListEntity row = JSON.parseObject(item, ResureListEntity.ListEntity.class);
        Log.i(TAG, "onCreate: "+item);
        data.add(DataUtil.getColMap("    任务ID", row.getId()));
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

        if (row.getBrief()!=null) {
            data.add(DataUtil.getColMap("    故障描述", row.getBrief().toString()));
        }/*else{
        data.add(DataUtil.getColMap("    故障描述", "654321--->"));}*/
        String status = row.getStatus();
        String statusName = "";
        if ("1".equals(status)) {
            statusName = "待处理";
        } else if ("2".equals(status)) {
            statusName = "处理中";
        } else if ("3".equals(status)) {
            statusName = "延迟处理";
        } else if ("4".equals(status)) {
            statusName = "已处理";
        }else{
            statusName="待确定";
        }
        data.add(DataUtil.getColMap("    当前状态", statusName));
        data.add(DataUtil.getColMap("    发生时间", DateUtils.secondToDateStr2(row.getCreate_date())));
        startTime=row.getStart_date();
        endTime=row.getEnd_date();

        listAdapter = new ArrayAdapter<Map<String, String>>(this, R.layout.table_row_list_row, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.table_row_list_row, parent, false);

                final TextView name = (TextView) convertView.findViewById(R.id.nameText);
                final TextView value = (TextView) convertView.findViewById(R.id.valueText);

                final Map<String, String> item = getItem(position);

                name.setText(item.get("name"));
                if ("img".equals(item.get("type"))) {
                    final LinearLayout valueView = (LinearLayout) convertView.findViewById(R.id.valueView);
                    loadImgs(valueView, item.get("value") + "");
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ImageMatrixActivity.getInstance().showPopupWindow(RescueDetailActivity.this, item.get("value") + "");
                            isBack=true;
                        }
                    });
                } else {
                    value.setText(item.get("value"));
                }

                return convertView;
            }
        };
        mListView.setAdapter(listAdapter);

        String id = row.getId();
        String eleId = row.getElevator_id();
        loadData(id, eleId);
    }

    private void loadData(String id, String eleId) {
//        Log.i(TAG, "loadData: "+AppContext.API_RESCUE_VIEW+" id="+id+"  ele_id="+eleId+" token="+ AppContext.userInfo.getToken());
        GetBuilder postFormBuilder = OkHttpUtils.get().url(AppContext.API_RESCUE_VIEW + "?uid=" + id + "&ele_id=" + "eleId&token=" + AppContext.userInfo.getToken());
        postFormBuilder.build().execute(new StringCallback() {
            @Override
            public void onResponse(String respData, int arg1) {
                Logs.e(respData);
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    Map<String, Object> result = responseData.getData();
                    if (result == null) {
                        return;
                    }
                    try {
                        Map<String, Object> row1 = (Map<String, Object>) result.get("view_data");
                        if(row1 == null){
                            return;
                        }
//                        Log.i(TAG, "row: "+row1);
                        Map<String, Object> row = (Map<String, Object>) row1.get("rescue_result");
                        if(row == null){
                            return;
                        }
                        int suc= parseInt((String) row.get("is_success"));
                        String Success="";
                        if (suc==1){
                            Success="成功";
                        }else{
                            Success="失败";
                        }
                        int injur=Integer.parseInt((String) row.get("injuries"));
                        String InJuries="";
                        if (injur==0){
                            InJuries="无";
                        }else{
                            InJuries="有";
                        }
                        data.add(DataUtil.getColMap("    开始处理时间", DateUtils.secondToDateStr2(startTime)));
                        data.add(DataUtil.getColMap("    结束处理时间", DateUtils.secondToDateStr2(endTime)));
                        data.add(DataUtil.getColMap("    情况描述", DataUtil.mapGetString(row, "reason")));
                        data.add(DataUtil.getColMap("    困人数量", DataUtil.mapGetString(row, "tir_person")));
                        data.add(DataUtil.getColMap("有无人员伤亡", InJuries));
                        data.add(DataUtil.getColMap("是否解救成功", Success));
                        data.add(DataUtil.getColMap("解救人员数量", DataUtil.mapGetString(row, "rescue_person")));
                        String msg=DataUtil.mapGetString(row, "confirm_brief");
                        String msgData=DateUtils.secondToDateStr2(DataUtil.mapGetString(row, "wait_confirm_date"));
                        if (msg.equals("")){
                            msgData="";
                        }

                        data.add(DataUtil.getColMap("物业确认时间", msgData));
                        data.add(DataUtil.getColMap("物业反馈内容", msg));
                        List<List<Map<String, Object>>> imgsMap = (List<List<Map<String, Object>>>) row1.get("imgs");
                        if (imgsMap != null && imgsMap.size() > 0) {
                            for (List<Map<String, Object>> one : imgsMap) {
                                for (Map<String,Object> two:one){
                                    Map<String, String> colMap = DataUtil.getColMap("      图片", two.get("url") + "");
                                    colMap.put("type", "img");
                                    data.add(colMap);
                                }
                            }
                        }

                        listAdapter.notifyDataSetInvalidated();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "数据解析失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "加载数据失败: " + responseData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                Toast.makeText(getApplicationContext(), "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadImgs(final LinearLayout valueView, String url) {
        Logs.i("load img");
        imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 500);

        layoutParams.leftMargin = 100;
        layoutParams.bottomMargin = 100;
        imageView.setLayoutParams(layoutParams);
        valueView.addView(imageView);
        Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .resize(200, 300)
                .centerCrop()
                .into(imageView);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_maintenance_detail;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (isBack){
                ImageMatrixActivity.getInstance().dissMissPopuoWindow();
                isBack=false;
            }else{
                finish();
            }
        }
        return false;
    }

}
