package com.app.dianti.activity.maintenance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.util.DataUtil;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MainViewActivity extends BaseActivity implements OnClickListener {
	private String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.elevator_base_info);
		super.initTitleBar("电梯基本信息");

		Intent intent = this.getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			id = extras.getString("id", "");

		}

		initView();
		initData();
	}

	private void initView() {

	}

	private void initData() {
		OkHttpUtils.post().url(AppContext.API_GET_ELE_BASE_INFO).addParams("token", AppContext.userInfo.getToken()).addParams("id", id).build().execute(new StringCallback() {

			@Override
			public void onResponse(String respData, int arg1) {
				ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
				if (responseData.getCode().equals("200")) {
					// 获取使用信息
					Map<String, Object> dataMap = responseData.getData();
					Map<String, Object> useInfo = (Map<String, Object>) dataMap.get("use");
					Map<String, Object> maintainInfo = (Map<String, Object>) dataMap.get("maintain");
					List<Map<String, String>> data = new ArrayList<Map<String, String>>();

					data.add(DataUtil.getColMap("电梯名称", DataUtil.mapGetString(dataMap, "ele_name")));
					data.add(DataUtil.getColMap("电梯编号", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("设备类型", DataUtil.mapGetString(dataMap, "code")));
					data.add(DataUtil.getColMap("安装地点", DataUtil.mapGetString(dataMap, "ele_addr")));
					//data.add(DataUtil.getColMap("注册代码", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("生产厂家", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("出厂编号", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("  载重量", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("额定速度", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("电梯型号", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("最低楼层", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("最高楼层", DataUtil.mapGetString(dataMap, "code")));
					//data.add(DataUtil.getColMap("层站数", DataUtil.mapGetString(dataMap, "code")));

					addRow("基本信息", data);

					List<Map<String, String>> useInfoData = new ArrayList<Map<String, String>>();
					useInfoData.add(DataUtil.getColMap("物业名称", DataUtil.mapGetString(dataMap, "use_dep_name")));
					useInfoData.add(DataUtil.getColMap("使用单位", DataUtil.mapGetString(dataMap, "use_dep_name")));						useInfoData.add(DataUtil.getColMap("    地址", DataUtil.mapGetString(dataMap, "address")));
					useInfoData.add(DataUtil.getColMap("使用单位负责人电话", DataUtil.mapGetString(useInfo, "res_phone")));
					useInfoData.add(DataUtil.getColMap("维保单位", DataUtil.mapGetString(maintainInfo, "name")));
					useInfoData.add(DataUtil.getColMap("维保单位负责人", DataUtil.mapGetString(maintainInfo, "res_name")));
					useInfoData.add(DataUtil.getColMap("维保单位负责人电话", DataUtil.mapGetString(maintainInfo, "res_phone")));
					//useInfoData.add(DataUtil.getColMap("所属社区", DataUtil.mapGetString(dataMap, "address")));
					//useInfoData.add(DataUtil.getColMap("社区电话", DataUtil.mapGetString(dataMap, "address")));
					//useInfoData.add(DataUtil.getColMap("所属街道", DataUtil.mapGetString(dataMap, "address")));
					//useInfoData.add(DataUtil.getColMap("街道电话", DataUtil.mapGetString(dataMap, "address")));
					addRow("使用信息", useInfoData);
				} else {
					tip("加载数据失败!请返回后重试。");
				}
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				tip("加载数据失败!请返回后重试。");
			}
		});
	}

	public void addRow(String title, List<Map<String, String>> data) {
		LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.mainten_table_row2, null);
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

		rowView.findViewById(R.id.start_maintenance).setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
	}

}