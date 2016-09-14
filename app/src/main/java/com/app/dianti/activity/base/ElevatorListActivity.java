package com.app.dianti.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.ElevatorDetailListActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.util.Logs;
import com.app.dianti.vo.ResponseData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 15:38
 */
public final class ElevatorListActivity extends CommonListActivity implements AdapterView.OnItemClickListener {

    private static final String EXTRA_REGISTER_CODE = "register_code.extra";
    private static final String EXTRA_ELEVATOR_NAME = "elevatorName.extra";
    private static final String EXTRA_TENEMENTS = "tenements.extra";
    private static final String EXTRA_MAINTENANCE = "maintenance.extra";

    private static final String EXTRA_SEARCH = "search.extra";

    public static Intent getIntent(@NonNull Context context) {
        return getIntent(context, false, null, null, null, null);
    }

    public static Intent getIntent(@NonNull Context context, boolean isSearch,
                                   String registerCode, String elevatorName, String tenements, String maintenance) {
        final Intent intent = new Intent(context, ElevatorListActivity.class);
        intent.putExtra(EXTRA_SEARCH, isSearch);
        intent.putExtra(EXTRA_REGISTER_CODE, registerCode);
        intent.putExtra(EXTRA_ELEVATOR_NAME, elevatorName);
        intent.putExtra(EXTRA_TENEMENTS, tenements);
        intent.putExtra(EXTRA_MAINTENANCE, maintenance);
        return intent;
    }

    private String mRegisterCode;
    private String mElevatorName;
    private String mTenements;
    private String mMaintenance;

    private ArrayAdapter<Map<String, String>> mAdapter;

    private final List<Map<String, String>> mMapList = new ArrayList<>();

    private boolean mIsSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsSearch = getIntent().getBooleanExtra(EXTRA_SEARCH, false);

        final Intent intent = getIntent();
        mRegisterCode = intent.getStringExtra(EXTRA_REGISTER_CODE);
        mElevatorName = intent.getStringExtra(EXTRA_ELEVATOR_NAME);
        mTenements = intent.getStringExtra(EXTRA_TENEMENTS);
        mMaintenance = intent.getStringExtra(EXTRA_MAINTENANCE);

        mAdapter = new ElevatorAdapter(this, mMapList);
        super.onCreate(savedInstanceState);

        mListView.setOnItemClickListener(this);
    }

    private boolean mIsLoading = false;

    private long mLastRequestTime;

    @Override
    protected void loadData(final int currentPage) {
        if (mIsLoading) {
            return;
        }

        final long curr = System.currentTimeMillis();
        if (curr - mLastRequestTime < 1000) {
            return;
        }
        mLastRequestTime = curr;

        Logs.e(Arrays.asList(AppContext.userInfo.getToken(),
                mRegisterCode, mElevatorName, mTenements, mMaintenance,
                String.valueOf(currentPage)).toString());
        mIsLoading = true;
        NetService.getInstance(this).getElevatorList(AppContext.userInfo.getToken(),
                mRegisterCode, mElevatorName, mTenements, mMaintenance, String.valueOf(currentPage),
                new OnResponseListener<String>() {
                    @Override
                    public void onSuccess(String respData) {
                        Logs.e(respData);
                        mIsLoading = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                        if (responseData.getCode().equals("200")) {
                            mTotalPage = responseData.getDataInt("totalPage");
                            mCurrentPage = responseData.getDataInt("currentPage");
                            List<Map<String, Object>> list = responseData.getDataMap("list");
                            if (list == null) {
                                return;
                            }

                            if (currentPage == 1) {
                                mMapList.clear();
                            }

                            for (Map<String, Object> row : list) {
                                Map<String, String> rowMap = new HashMap<>();
                                try {
                                    rowMap.put("name", (row.get("ele_addr") == null ? "" : row.get("ele_addr") + "") + " " + (row.get("ele_name") == null ? "" : row.get("ele_name") + ""));
                                } catch (Exception e) {
                                    rowMap.put("value", "");
                                    e.printStackTrace();
                                }
                                try {
                                    rowMap.put("desc", (row.get("use_dep_name") == null ? "" : row.get("use_dep_name") + ""));
                                } catch (Exception e) {
                                    rowMap.put("value", "");
                                    e.printStackTrace();
                                }

                                rowMap.put("id", row.get("id").toString());

                                mMapList.add(rowMap);
                            }

                            mAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(ElevatorListActivity.this, "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String info) {
                        mIsLoading = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(ElevatorListActivity.this, info, Toast.LENGTH_SHORT).show();
                    }
                });
        /*

        OkHttpUtils.post().url(AppContext.API_LOGIN_DANGAN)
                .addParams("token", AppContext.userInfo.getToken())
                .addParams("code", mRegisterCode)
                .addParams("ele_name", mElevatorName)
                .addParams("use_dep_name", mTenements)
                .addParams("maintain_dep_name", mMaintenance)
                .addParams("pageNum", 1 + "").build()
                .execute(new StringCallback() {

                    @Override
                    public void onResponse(String respData, int arg1) {
                        mIsLoading = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                        if (responseData.getCode().equals("200")) {
                            mTotalPage = responseData.getDataInt("totalPage");
                            mCurrentPage = responseData.getDataInt("currentPage");
                            List<Map<String, Object>> list = responseData.getDataMap("list");
                            if (list == null) {
                                return;
                            }

                            if (currentPage == 1) {
                                mMapList.clear();
                            }

                            for (Map<String, Object> row : list) {
                                Map<String, String> rowMap = new HashMap<String, String>();
                                try {
                                    rowMap.put("name", (row.get("ele_addr") == null ? "" : row.get("ele_addr") + "") + " " + (row.get("ele_name") == null ? "" : row.get("ele_name") + ""));
                                } catch (Exception e) {
                                    rowMap.put("value", "");
                                    e.printStackTrace();
                                }
                                try {
                                    rowMap.put("desc", (row.get("use_dep_name") == null ? "" : row.get("use_dep_name") + ""));
                                } catch (Exception e) {
                                    rowMap.put("value", "");
                                    e.printStackTrace();
                                }

                                rowMap.put("id", row.get("id").toString());

                                mMapList.add(rowMap);
                            }

                            mAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(ElevatorListActivity.this, "加载数据失败!请返回后重试。", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call arg0, Exception arg1, int arg2) {
                        mIsLoading = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        System.out.println("error");
                    }
                });*/
    }

    @Override
    protected ArrayAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_elevator;
    }

    @Override
    protected boolean isHistory() {
        return mIsSearch;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, ElevatorDetailListActivity.class);
        intent.putExtra("id", mMapList.get(i).get("id"));
        startActivity(intent);
    }
}
