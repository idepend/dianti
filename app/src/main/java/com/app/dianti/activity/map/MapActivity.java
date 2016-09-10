package com.app.dianti.activity.map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.activity.ElevatorDetailListActivity;
import com.app.dianti.activity.ElevatorQueryActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.util.DataUtil;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MapActivity extends BaseActivity implements OnClickListener, LocationSource, AMapLocationListener, OnCheckedChangeListener, OnInfoWindowClickListener,
		OnMarkerClickListener, OnMarkerDragListener {
	private MapView mapView;
	private AMap aMap;

	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;

	private Double latitude;
	private Double longitude;

	private String markerPri = "map_";

	private boolean isFirstAlreadyLoadData = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.elevator_map);
		super.initTitleBar("电子地图");

		mapView = (MapView) findViewById(R.id.map);
		// 此方法必须重写
		mapView.onCreate(savedInstanceState);

		init();
	}

	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}

		//地图位置初始为杭州市
		latitude = 30.299507;
		longitude = 120.131141;

		LatLng marker1 = new LatLng(latitude, longitude);
		// 设置中心点和缩放比例
		aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
		aMap.moveCamera(CameraUpdateFactory.zoomTo(12));

		findViewById(R.id.near_ele).setOnClickListener(this);

	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// aMap.setMyLocationType()

		// 设置点击marker事件监听器
		aMap.setOnMarkerClickListener(this);
		// 设置点击infoWindow事件监听器
		aMap.setOnInfoWindowClickListener(this);
		// 设置marker可拖拽事件监听器
		aMap.setOnMarkerDragListener(this);
	}


	private void initData(Double lng, Double lat) {
		Log.i("wj", "initData: "+AppContext.API_GET_ELE_NEAR+" lat="+lat+" lng="+lng);
		tip("正在加载周围电梯数据...");
		OkHttpUtils.post().url(AppContext.API_GET_ELE_NEAR).addParams("token", AppContext.userInfo.getToken())
				.addParams("lng", lng.toString())
				.addParams("lat", lat.toString())
				.build().execute(new StringCallback() {

			@Override
			public void onResponse(String respData, int arg1) {
				try{
					ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
					Log.i("wj", "onResponse: "+responseData.getData()+" ---> \n");
					if (responseData.getCode().equals("200")) {
						List<Map<String, Object>> list = responseData.getDataMap("list");
						Log.i("wj", "onResponse: "+list.size());
						if (list == null) {
							tip("周围没有电梯数据.");
							return;
						}
						for (Map<String, Object> row : list) {
							if (row.get("lat") != null && row.get("lng") != null) {
								MarkerOptions markerOptions = new MarkerOptions();
								markerOptions.title(DataUtil.mapGetString(row, "ele_addr"));
								markerOptions.snippet(DataUtil.mapGetString(row, "ele_name"));
								markerOptions.position(new LatLng(Double.parseDouble(row.get("lat").toString()), Double.parseDouble(row.get("lng").toString())));
								Marker addMarker = aMap.addMarker(markerOptions);
								addMarker.setObject(markerPri + row.get("id").toString());
							}
						}
						tip("已加载周围电梯数据.");
					} else {
						tip("加载数据失败!请返回后重试。");
					}
				}catch (Exception e){
					tip("解析服务端数据错误!");
				}
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				System.out.println("error");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.right_btn:
				goActivity(ElevatorQueryActivity.class);
				break;
			case R.id.near_ele:
				//获取地图中心点坐标
				CameraPosition cameraPosition = aMap.getCameraPosition();
				latitude = cameraPosition.target.latitude;
				longitude = cameraPosition.target.longitude;

				//移除marker
				List<Marker> saveMarkerList = aMap.getMapScreenMarkers();
				if (saveMarkerList == null || saveMarkerList.size() <= 0){
					return;
				}


				for (Marker marker : saveMarkerList) {
					//Marker1为地图当前位置的图标marker
					if(marker.getId() != null && !marker.getId().startsWith(markerPri)){
						continue;
					}
					marker.remove();
				}

				initData(longitude, latitude);

				break;

			default:
				break;
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				if(!isFirstAlreadyLoadData){
					isFirstAlreadyLoadData = true;
					initData(amapLocation.getLongitude(), amapLocation.getLatitude());
				}

				//定位成功后获取当前地址
				String address = amapLocation.getAddress();
				Double lng = amapLocation.getLongitude();
				Double lat = amapLocation.getLatitude();


			} else {
				String errText = "定位失败, " + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
			//	Log.e("AmapErr",errText);
				tip(errText);
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		String markerId = marker.getObject().toString();
		if(markerId != null && markerId.startsWith(markerPri)){
			Intent intent = new Intent(getApplicationContext(), ElevatorDetailListActivity.class);
			intent.putExtra("isHistory", true);
			//type为1代表查询电梯历史记录
			intent.putExtra("type", 1);
			//电梯id
			intent.putExtra("id", markerId.substring(markerPri.length(), markerId.length()));
			startActivity(intent);
		}
	}

	/**
	 * 监听拖动marker时事件回调
	 */
	@Override
	public void onMarkerDrag(Marker marker) {

	}

	/**
	 * 监听拖动marker结束事件回调
	 */
	@Override
	public void onMarkerDragEnd(Marker marker) {
	}

	/**
	 * 监听开始拖动marker事件回调
	 */
	@Override
	public void onMarkerDragStart(Marker marker) {

	}
}
