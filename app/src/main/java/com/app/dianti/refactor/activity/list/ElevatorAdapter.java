package com.app.dianti.refactor.activity.list;

import android.content.Context;
import android.text.TextUtils;

import com.app.dianti.R;
import com.app.dianti.refactor.net.entity.Elevator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Locale;

/**
 * @user MycroftWong
 * @date 16/7/4
 * @time 21:36
 */
public final class ElevatorAdapter extends BaseQuickAdapter<Elevator> {

    public ElevatorAdapter(Context context, int layoutResId, List<Elevator> data) {
        super(context, layoutResId, data);
    }

    private static final String EXP_NAME = "%s %s";

    @Override
    protected void convert(BaseViewHolder helper, Elevator elevator) {
        helper.setText(R.id.name_text_view, String.format(Locale.CHINA, EXP_NAME, elevator.getEle_addr(), elevator.getEle_name()))
                .setText(R.id.desc_text_view, TextUtils.isEmpty(elevator.getUse_dep_name()) ? "" : elevator.getUse_dep_name());
    }
}
