package com.app.dianti.activity.annual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.util.DateUtils;
import com.app.dianti.util.Logs;
import com.app.dianti.util.StringUtils;
import com.app.dianti.vo.ResponseData;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class AnnualActivity extends BaseActivity implements OnClickListener {

    private int totalPage;
    private int currentPage;

    private boolean isHistory;

    private int type;
    private String id = "";

    private String mTmpPath;
    private static final int REQUEST_PHOTO = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.elevator_detail);
        super.initTitleBar("电梯年检");

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            isHistory = extras.getBoolean("isHistory", false);
            type = extras.getInt("type", 2);
            id = extras.getString("id", "");
        }

        findViewById(R.id.right_btn).setVisibility(View.GONE);

        // init();

        initData();
    }

    //
    // private void init() {
    // List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    // Map<String, String> col = new HashMap<String, String>();
    // col.put("name", "电梯名称");
    // col.put("value", "海宁市人民医院15号楼");
    // data.add(col);
    // Map<String, String> col2 = new HashMap<String, String>();
    // col2.put("name", "安装地点");
    // col2.put("value", "海宁市人民医院15号楼");
    // data.add(col2);
    // Map<String, String> col3 = new HashMap<String, String>();
    // col3.put("name", "注册代码");
    // col3.put("value", "3001211");
    // data.add(col3);
    // Map<String, String> col4 = new HashMap<String, String>();
    // col4.put("name", "当前状态");
    // col4.put("value", "待处理");
    // data.add(col4);
    //
    // addRow("任务", data);
    // }

    private void initData() {
        String status = "";
        if (type == 3) {
            status = "3";
        } else if (type == 1) {
            status = "3";
        } else if (type == 2) {
            status = "1,2";
        }

        OkHttpUtils.post().url(AppContext.API_LOGIN_ANNUAL).addParams("token", AppContext.userInfo.getToken()).addParams("id", id).addParams("type", type + "").addParams("status", status)
                .addParams("pageNum", 1 + "").build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    totalPage = responseData.getDataInt("totalPage");
                    List<Map<String, Object>> list = responseData.getDataMap("list");
                    if (list == null) {
                        return;
                    }

                    for (Map<String, Object> row : list) {
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
                        col2.put("value", row.get("code").toString());
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

                        addRow("任务", row, data);
                    }
                } else {
                    tip("加载数据失败!请返回后重试。");
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                System.out.println("error");
            }
        });
    }

    public void addRow(String title, final Map<String, Object> row, List<Map<String, String>> data) {
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.annual_table_row2, null);
        rootView.addView(rowView);

        TextView rowTitle = (TextView) rowView.findViewById(R.id.rowTitle);
        rowTitle.setText(title);
        for (Map<String, String> map : data) {
            LinearLayout contextView = (LinearLayout) rowView.findViewById(R.id.contextView);
            View rowColView = inflater.inflate(R.layout.table_row_list_row, null);
            TextView nameText = (TextView) rowColView.findViewById(R.id.nameText);
            nameText.setText(map.get("name"));
            TextView valueText = (TextView) rowColView.findViewById(R.id.valueText);
            valueText.setText(map.get("value"));
            contextView.addView(rowColView);
        }

        if (isHistory) {
            rowView.findViewById(R.id.row_btn1).setVisibility(View.GONE);
            rowView.findViewById(R.id.row_btn2).setVisibility(View.GONE);
        }

        // 我要开始维保
        rowView.findViewById(R.id.row_btn1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tip("待实现");
            }
        });
        rowView.findViewById(R.id.row_btn2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tip("待实现");
            }
        });
    }

    // public void addRow(String title, List<Map<String, String>> data) {
    // LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
    // LayoutInflater inflater = (LayoutInflater)
    // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    // View rowView = inflater.inflate(R.layout.annual_table_row_view, null);
    // rootView.addView(rowView);
    //
    // TextView rowTitle = (TextView) rowView.findViewById(R.id.rowTitle);
    // rowTitle.setText(title);
    //
    // ListView listView = (ListView) rowView.findViewById(R.id.listview);
    //
    // TableRowAdapter listViewAdapter = new
    // TableRowAdapter(getApplicationContext(), data,
    // R.layout.table_row_list_row);
    // listView.setAdapter(listViewAdapter);
    // listView.setOnItemClickListener(new OnItemClickListener() {
    //
    // @Override
    // public void onItemClick(AdapterView<?> parent, View view, int
    // itemPosition, long id) {
    //
    // }
    // });
    // }

    @Override
    public void onClick(View v) {

    }

}