package com.app.dianti.activity.reskju;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.entity.ResureListEntity;
import com.app.dianti.util.DataUtil;
import com.app.dianti.util.DateUtils;
import com.app.dianti.util.Logs;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ReskjuActivity extends BaseActivity implements OnClickListener {

    private int totalPage;
    private int currentPage;

    private boolean isHistory;
    private int type = 2;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.reskju);
        super.initTitleBar("应急救援");
        // init();

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            isHistory = extras.getBoolean("isHistory", false);
            type = extras.getInt("type", 2);
            id = extras.getString("id", "");

        }

        initView();
        initData();

        if (isHistory) {
            findViewById(R.id.right_btn).setVisibility(View.GONE);
        }
    }

    private void initView() {
        View right_btn = findViewById(R.id.right_btn);
        right_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                goActivity(StatementActivity.class);
            }
        });

    }

    private final Gson mGson = new Gson();

    private void initData() {
        String status = null;
        if (type == 3) {
            status = "4";
        } else if (type == 1) {
            status = "4";
        } else if (type == 2) {
            status = "1";
        }

        Logs.e(Arrays.asList(AppContext.userInfo.getToken(), type, status, 1).toString());
        NetService.getInstance(this)
                .getResureList(AppContext.userInfo.getToken(), type, String.valueOf(4), 1, new OnResponseListener<ResureListEntity>() {
                    @Override
                    public void onSuccess(ResureListEntity data1) {
                        totalPage = data1.getTotalPage();
                        List<ResureListEntity.ListEntity> list = data1.getList();

                        if (list == null) {
                            return;
                        }

                        for (ResureListEntity.ListEntity row : list) {
                            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
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
                            }
                            data.add(DataUtil.getColMap("    故障类型", typeName));

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
                            }
                            data.add(DataUtil.getColMap("    当前状态", statusName));
                            data.add(DataUtil.getColMap("    发生时间", DateUtils.secondToDateStr(row.getCreate_date())));

                            addRow("任务", row, data);
                        }
                    }

                    @Override
                    public void onFailure(String info) {
                        tip(info);
                    }
                });
    }

    public void addRow(String title, final ResureListEntity.ListEntity row, List<Map<String, String>> data) {
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.reskju_table_row2, null);
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
            rowView.findViewById(R.id.start_maintenance).setVisibility(View.GONE);
        }

        // 结单
        rowView.findViewById(R.id.start_maintenance).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StatementActivity.class);
                Bundle bundle = new Bundle(); // 创建Bundle对象
                bundle.putString("id", row.getId().toString()); // 装入数据
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //
    // public void addRow(String title, List<Map<String, String>> data) {
    // LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
    // LayoutInflater inflater = (LayoutInflater)
    // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    // View rowView = inflater.inflate(R.layout.reskju_table_row, null);
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
    // @Override
    // public void onItemClick(AdapterView<?> parent, View view, int
    // itemPosition, long id) {
    //
    // }
    // });
    //
    // // 结单
    // Button people_rescue_form = (Button) rowView.findViewById(R.id.start_maintenance);
    //
    // people_rescue_form.setOnClickListener(new OnClickListener() {
    // @Override
    // public void onClick(View view) {
    // // tip("我要开始维保");
    // goActivity(StatementActivity.class);
    // }
    // });
    // }

    // private void init() {
    // List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    // Map<String, String> col = new HashMap<String, String>();
    // col.put("name", "电梯名称");
    // col.put("value", "海宁市人民医院15号楼");
    // data.add(col);
    // Map<String, String> col3 = new HashMap<String, String>();
    // col3.put("name", "应完成时间");
    // col3.put("value", "2016-05-20");
    // data.add(col3);
    // Map<String, String> col4 = new HashMap<String, String>();
    // col4.put("name", "当前状态");
    // col4.put("value", "待确认");
    // data.add(col4);
    //
    // addRow("任务", data);
    // addRow("任务", data);
    // }

    @Override
    public void onClick(View v) {

    }
}