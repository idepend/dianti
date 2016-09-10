package com.app.dianti.activity.dangan;

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
import com.app.dianti.util.StringUtils;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class EleRealMonitorActivity extends BaseActivity implements OnClickListener {
	private String id = "";

	private TextView eleName;
	private TextView eleAddress;
	private TextView doorState;
	private TextView eleDirection;
	private TextView isHasPeople;
	private TextView currentFloor;
	private TextView failureInfo;

	private boolean stopGetRealInfoTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.elevator_real_monitor_info);
		super.initTitleBar("电梯实时监测");

		Intent intent = this.getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			id = extras.getString("id", "");

		}

		initView();
		initData();
	}

	private void initView() {
		eleName = (TextView) findViewById(R.id.eleName);
		eleAddress = (TextView) findViewById(R.id.eleAddress);
		doorState = (TextView) findViewById(R.id.doorState);
		eleDirection = (TextView) findViewById(R.id.eleDirection);
		isHasPeople = (TextView) findViewById(R.id.isHasPeople);
		currentFloor = (TextView) findViewById(R.id.currentFloor);
		failureInfo = (TextView) findViewById(R.id.failureInfo);
	}

	private void initData() {
		//获取电梯基本信息--电梯名称和电梯地址
		OkHttpUtils.post().url(AppContext.API_GET_ELE_BASE_INFO).addParams("token", AppContext.userInfo.getToken()).addParams("id", id).build().execute(new StringCallback() {

			@Override
			public void onResponse(String respData, int arg1) {
				ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
				if (responseData.getCode().equals("200")) {
					Map<String, Object> dataMap = responseData.getData();
					eleName.setText(DataUtil.mapGetString(dataMap, "ele_name"));
					eleAddress.setText(DataUtil.mapGetString(dataMap, "ele_addr"));
				} else {
					tip("加载电梯信息失败!请返回后重试。");
				}
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				tip("加载电梯信息失败!请返回后重试。");
			}
		});

		//开启任务一直,获取电梯的实时信息,退出此界面后要关闭此任务
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stopGetRealInfoTask){
					loadRealInfo();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	public void loadRealInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", AppContext.userInfo.getToken());
		map.put("id", id);
		String parmas = JSON.toJSONString(map);
		OkHttpUtils.post().url(AppContext.API_GET_ELE_REALTIME_INFO).addParams("data", parmas).build().execute(new StringCallback() {

			@Override
			public void onResponse(String respData, int arg1) {
				ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
				if (responseData.getCode().equals("200")) {
					Map<String, Object> realInfoMap = responseData.getData();

					//当前楼层
					String currentFloorText = DataUtil.mapGetString(realInfoMap, "currentFloor");
					currentFloor.setText(currentFloorText);

					//电梯门状态, 0关 1开
					String doorStatus = DataUtil.mapGetString(realInfoMap, "doorState");
					String doorStatusName = doorStatus;
					if(doorStatus.equals("0")){
						doorStatusName = "关";
					}else if(doorStatus.equals("1")){
						doorStatusName = "开";
					}
					doorState.setText(doorStatusName);

					//方向， 0停止， 1上， 2下
					String direction = DataUtil.mapGetString(realInfoMap, "moveDirection");
					String directionName = direction;
					if(direction.equals("0")){
						directionName = "停止";
					}else if(direction.equals("1")){
						directionName = "上";
					}else if(direction.equals("2")){
						directionName = "下";
					}
					eleDirection.setText(directionName);

					//是否有人, 0没人， 1有人
					String haspeople = DataUtil.mapGetString(realInfoMap, "isHasSomebody");
					String haspeopleName = direction;
					if(haspeople.equals("0")){
						haspeopleName = "没人";
					}else if(haspeople.equals("1")){
						haspeopleName = "有人";
					}
					isHasPeople.setText(haspeopleName);

					//故障类型告警信息: 0:无告警 1:门区外停梯故障 2:电梯超速运行故障 3:电梯冲顶（超限值）故障  4:电梯蹲底（超限值）故障  5:电梯运行中开门故障  6:电梯困人故障  7:电梯电源故障  8:电梯安全回路故障 9:开门走楼10:用户按下(求救按钮)',
					String failureInfoCode = DataUtil.mapGetString(realInfoMap, "failureInfo");
					String failureInfoName = failureInfoCode;
					if(failureInfoCode.equals("0")){
						failureInfoName = "无告警 ";
					}else if(failureInfoCode.equals("1")){
						failureInfoName = "门区外停梯故障";
					}else if(failureInfoCode.equals("2")){
						failureInfoName = "电梯超速运行故障";
					}else if(failureInfoCode.equals("3")){
						failureInfoName = "电梯冲顶（超限值）故障";
					}else if(failureInfoCode.equals("4")){
						failureInfoName = "电梯蹲底（超限值）故障";
					}else if(failureInfoCode.equals("5")){
						failureInfoName = "电梯运行中开门故障";
					}else if(failureInfoCode.equals("6")){
						failureInfoName = "电梯困人故障";
					}else if(failureInfoCode.equals("7")){
						failureInfoName = "电梯电源故障";
					}else if(failureInfoCode.equals("8")){
						failureInfoName = "电梯安全回路故障";
					}else if(failureInfoCode.equals("9")){
						failureInfoName = "开门走楼";
					}else if(failureInfoCode.equals("10")){
						failureInfoName = "用户按下(求救按钮)";
					}
					failureInfo.setText(failureInfoName);
				} else {
					tip("加载电梯信息失败!请返回后重试。");
				}
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				tip("加载电梯信息失败!请返回后重试。");
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopGetRealInfoTask = true;
	}
}