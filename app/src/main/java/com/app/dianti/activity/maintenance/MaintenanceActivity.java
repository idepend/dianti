package com.app.dianti.activity.maintenance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MaintenanceActivity extends BaseActivity {//implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

//    private int totalPage = 1;
//    private int currentPage = 1;
//
//    private boolean isHistory;
//    private int type = 2;
//    private String id = "";
//
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//
//    private ListView mListView;
//
////    private NestedScrollView mScrollView;
//
//    private final List<Map<String, Object>> mMapList = new ArrayList<>();
//    private MaintenanceAdapter mAdapter;
////    private LinearLayout mRootView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        super.setContentView(R.layout.elevator_detail);
//        super.initTitleBar("电梯维保");
//
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//
//        mListView = (ListView) findViewById(R.id.list_view);
//        mAdapter = new MaintenanceAdapter(this, mMapList, false);
//        mListView.setAdapter(mAdapter);
//
//        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                if (currentPage >= totalPage) {
//                    return;
//                }
//
//                if (totalItemCount - (firstVisibleItem + visibleItemCount) < 3) {
//                    initData(currentPage + 1);
//                }
//
//            }
//        });
//
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        Intent intent = this.getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            isHistory = extras.getBoolean("isHistory", false);
//            type = extras.getInt("type", 2);
//            id = extras.getString("id", "");
//
//        }
//
//        initView();
//        initData(1);
//        // initTest();
//
//        if (isHistory) {
//            findViewById(R.id.right_btn).setVisibility(View.GONE);
//        }
//    }
//
//    private void initView() {
//        View right_btn = findViewById(R.id.right_btn);
//        right_btn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                goActivity(StartMaintenanceActivity.class);
//            }
//        });
//    }
//
//    private long mLastRequestTime;
//
//    private boolean mIsLoading = false;
//
//    private void initData(final int pageNum) {
//        if (mIsLoading) {
//            return;
//        }
//
//        final long curr = System.currentTimeMillis();
//        if (curr - mLastRequestTime < 1000) {
//            return;
//        }
//        mLastRequestTime = curr;
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("token", AppContext.userInfo.getToken());
//
//        map.put("type", type);
//
//        map.put("pageNum", pageNum);
//        map.put("id", id);
//        String parmas = JSON.toJSONString(map);
//        OkHttpUtils.post().url(AppContext.API_LOGIN_MAINTENANCE).addParams("data", parmas).build().execute(new StringCallback() {
//            @Override
//            public void onResponse(String respData, int arg1) {
//                mIsLoading = false;
//                mSwipeRefreshLayout.setRefreshing(false);
//                ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
//                if (responseData.getCode().equals("200")) {
//                    totalPage = responseData.getDataInt("totalPage");
//                    currentPage = responseData.getDataInt("currentPage");
//
//                    List<Map<String, Object>> list = responseData.getDataMap("list");
//                    if (list == null) {
//                        return;
//                    }
//
//                    if (pageNum == 1) {
//                        mMapList.clear();
//                    }
////                    mMapList.addAll(list);
////                    mRootView.removeAllViews();
//
//                    mMapList.addAll(list);
//                    mAdapter.notifyDataSetChanged();
//
//                    Logs.e(mMapList.toString());
//
//                    /*for (Map<String, Object> row : mMapList) {
//                        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//                        Map<String, String> colCode = new HashMap<String, String>();
//                        colCode.put("name", "    注册编码");
//                        colCode.put("value", DataUtil.mapGetString(row, "code"));
//                        data.add(colCode);
//                        Map<String, String> col = new HashMap<String, String>();
//                        col.put("name", "    电梯名称");
//                        col.put("value", DataUtil.mapGetString(row, "address"));
//                        data.add(col);
//                        Map<String, String> col2 = new HashMap<String, String>();
//                        col2.put("name", "    维保类型");
//                        col2.put("value", DataUtil.mapGetString(row, "typeName"));
//                        data.add(col2);
//                        Map<String, String> col3 = new HashMap<String, String>();
//                        col3.put("name", "应完成时间");
//                        if (row.get("planTime") == null) {
//                            col3.put("value", "");
//                        } else {
//                            col3.put("value", DateUtils.secondToDateStr(DataUtil.mapGetString(row, "planTime")));
//                        }
//                        data.add(col3);
//                        Map<String, String> col4 = new HashMap<String, String>();
//                        col4.put("name", "    当前状态");
//                        String status = DataUtil.mapGetString(row, "status");
//                        if ("1".equals(status)) {
//                            col4.put("value", "待处理");
//                        } else if ("2".equals(status)) {
//                            col4.put("value", "已签到,待完成");
//                        } else if ("3".equals(status)) {
//                            col4.put("value", "已完成");
//                        } else {
//                            col4.put("value", "");
//                        }
//                        data.add(col4);
//
//                        addRow("任务", row, data);
//                    }*/
//                } else {
//                    tip("加载数据失败!请返回后重试。");
//                }
//            }
//
//            @Override
//            public void onError(Call arg0, Exception arg1, int arg2) {
//                mIsLoading = false;
//                mSwipeRefreshLayout.setRefreshing(false);
//                System.out.println("error");
//            }
//        });
//    }
//
//    public void addRow(String title, final Map<String, Object> row, List<Map<String, String>> data) {
//
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.mainten_table_row2, null);
////        mRootView.addView(rowView);
//
//        TextView rowTitle = (TextView) rowView.findViewById(R.id.rowTitle);
//        rowTitle.setText(title);
//        for (Map<String, String> map : data) {
//            LinearLayout contextView = (LinearLayout) rowView.findViewById(R.id.contextView);
//            View rowColView = inflater.inflate(R.layout.table_row_list_row, null);
//            TextView nameText = (TextView) rowColView.findViewById(R.id.nameText);
//            nameText.setText(map.get("name"));
//            TextView valueText = (TextView) rowColView.findViewById(R.id.valueText);
//            valueText.setText(map.get("value"));
//            contextView.addView(rowColView);
//        }
//
//        if (isHistory) {
//            rowView.findViewById(R.id.start_maintenance).setVisibility(View.GONE);
//        }
//
//        // 我要开始维保
//        rowView.findViewById(R.id.start_maintenance).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), StartMaintenanceActivity.class);
//                Bundle bundle = new Bundle(); // 创建Bundle对象
//                bundle.putString("id", row.get("id").toString()); // 装入数据
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//    }
//
//    @Override
//    public void onRefresh() {
//        initData(1);
//    }
}