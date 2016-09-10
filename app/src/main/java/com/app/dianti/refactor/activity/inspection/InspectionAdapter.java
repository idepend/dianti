package com.app.dianti.refactor.activity.inspection;

import android.content.Context;

import com.app.dianti.refactor.net.entity.Inspection;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @user MycroftWong
 * @date 16/7/5
 * @time 21:10
 */
final class InspectionAdapter extends BaseQuickAdapter<Inspection> {

    public InspectionAdapter(Context context, int layoutResId, List<Inspection> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Inspection inspection) {

    }
}
