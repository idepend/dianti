package com.app.dianti.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * listview和scrollview一起使用的时候，会导致listview的高度变得很小，需要根据每个item的高度重新计算总高度  <br/>
 * 注意：listview的布局必须为线性布局
 * @author SongFei
 * @date 2016-5-15
 */
public class ListViewUtils {

	// public static int dp2px(float dp) {
	// return (int) (dp * pixelDensity + 0.5f);
	// }

	// 此方法要求每个item项必须是线性布局
	public static void setListViewHeightBasedOnChildren(Context context, ListView listView, int itemHight) {

		// 获取listview的适配器
		ListAdapter listAdapter = listView.getAdapter();

		// item的高度
		// int itemHeight = 60;
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			// totalHeight += dp2px(itemHight) + listView.getDividerHeight();
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// params.height = totalHeight;
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);
	}

}
