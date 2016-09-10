package com.app.dianti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.dianti.R;

import java.util.List;

/**
 * Created by Lenovo on 2016/9/9.
 */

public class NoticeAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public NoticeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        ViewHolder mHolder = null;
        if (contentView == null) {
            mHolder=new ViewHolder();
            contentView = LayoutInflater.from(context).inflate(R.layout.activity_notice_item, null);
            mHolder.titleTv= (TextView) contentView.findViewById(R.id.titleTv);
            mHolder.contentTv= (TextView) contentView.findViewById(R.id.contentTv);
            mHolder.tiemTv= (TextView) contentView.findViewById(R.id.timeTv);
            contentView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) contentView.getTag();
        }
        mHolder.titleTv.setText("新消息的下发通知");
        mHolder.contentTv.setText("最痛苦的莫过于徘徊在放与不放之间的那一段。真正决心放弃了，反而，会有一种释然的感觉。人生的经历就像是铅笔一样，开始很尖，经历的多了也就变得圆滑了，如果承受不了就会断了");
        mHolder.tiemTv.setText("2016-09-09");
        return contentView;
    }

    public static class ViewHolder {
        private TextView titleTv;
        private TextView tiemTv;
        private TextView contentTv;
    }
}
