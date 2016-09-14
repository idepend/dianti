package com.app.dianti.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.util.DataUtil;
import com.app.dianti.util.DateUtils;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 11:30
 */
public class AnnualDetailActivity extends CommonActivity {

    private static final String EXTRA_DATA = "data.extra";
    private static final String TAG="wj";
    private ArrayAdapter<Map<String, String>> listAdapter;
    private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
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
//        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> col = new HashMap<String, String>();
        col.put("name", "    注册编号");
        col.put("value", row.get("ele_code").toString());
        data.add(col);
        Map<String, String> col2 = new HashMap<String, String>();
        col2.put("name", "   应完成时间");
        if (row.get("plan_date") == null) {
            col2.put("value", "");
        } else {
            col2.put("value", DateUtils.secondToDateStr(row.get("plan_date") + ""));
        }
        data.add(col2);
        Map<String, String> col3 = new HashMap<String, String>();
        col3.put("name", "    当前状态");
        if ("1".equals(row.get("status").toString())) {
            col3.put("value", "待处理");
        } else if ("2".equals(row.get("status") + "")) {
            col3.put("value", "待整改");
        } else if ("3".equals(row.get("status") + "")) {
            col3.put("value", "已完成");
        } else {
            col3.put("value", "已完成");
        }
        data.add(col3);

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

        String id= DataUtil.mapGetString(row, "id");

        initData(id);

        listAdapter = new ArrayAdapter<Map<String, String>>(this, R.layout.table_row_list_row, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.table_row_list_row, parent, false);

                final TextView name = (TextView) convertView.findViewById(R.id.nameText);
                final TextView value = (TextView) convertView.findViewById(R.id.valueText);

                final Map<String, String> item = getItem(position);
                name.setText(item.get("name"));
                value.setText(item.get("value"));
                /*if ("img".equals(item.get("type"))) {
                    final LinearLayout valueView = (LinearLayout) convertView.findViewById(R.id.valueView);
                    loadImgs(valueView, item.get("value") + "");
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ImageMatrixActivity.getInstance().showPopupWindow(AnnualDetailActivity.this, item.get("value") + "");
//                            isBack=true;
                        }
                    });
                } else {
                    value.setText(item.get("value"));
                }*/

                return convertView;
            }
        };
        mListView.setAdapter(listAdapter);


    }



    /**
     * 数据刷新加载
     */
    private void initData(String id) {
//        Log.i(TAG, "initData: "+AppContext.API_ANNUAL_DETAIL + "?token=" + AppContext.userInfo.getToken()+ "&id=" + id );
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(AppContext.API_ANNUAL_DETAIL).addParams("token", AppContext.userInfo.getToken()).addParams("id", id);
        postFormBuilder.build().execute(new StringCallback() {
            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
//                Log.i("wj", "onResponse:nainjain "+responseData.getData()+"  \n "+responseData.getMsg());
                if (responseData.getCode().equals("200")) {
//                    totalPage = responseData.getDataInt("totalPage");
                    Map<String, Object> result = responseData.getData();
                    if (result == null) {
                        return;
                    }
                    try {
                        Map<String, Object> row1 = (Map<String, Object>) result.get("elevator_info");
                        if(row1 == null){
                            return;
                        }
//                        Log.i(TAG, "All ---> : "+row1);

                        Map<String, Object> row = (Map<String, Object>) row1.get("args");
                        if(row == null){
                            return;
                        }
//                        Log.e(TAG, "row : "+row );

                        Map<String, Object> use = (Map<String, Object>) row1.get("use");
                        if(use == null){
                            return;
                        }
//                        Log.i(TAG, "use : "+use);

                        //device

                        Map<String, Object> reg = (Map<String, Object>) row1.get("reg");
                        if(reg == null){
                            return;
                        }
                        data.add(DataUtil.getColMap("    电梯地址", DataUtil.mapGetString(use, "address")));
                        data.add(DataUtil.getColMap("  电梯救援识别码", DataUtil.mapGetString(reg, "rescue_code")));
                        data.add(DataUtil.getColMap("    电梯型号", DataUtil.mapGetString(row, "model")));
                        data.add(DataUtil.getColMap(" 使用单位内部编号", DataUtil.mapGetString(reg, "inner_code")));

                        data.add(DataUtil.getColMap("    使用单位", DataUtil.mapGetString(use, "name")));
                        data.add(DataUtil.getColMap("      联系人", DataUtil.mapGetString(use, "res_name")));
                        data.add(DataUtil.getColMap("    联系电话", DataUtil.mapGetString(use, "res_phone")));

                        listAdapter.notifyDataSetInvalidated();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "数据解析失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(AnnualDetailActivity.this,"加载数据失败!请返回后重试。",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                System.out.println("error");
            }
        });

    }

    @Override
    protected int getResId() {
        return R.layout.activity_maintenance_detail;
    }

}
