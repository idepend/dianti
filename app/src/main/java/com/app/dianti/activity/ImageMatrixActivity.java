package com.app.dianti.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.app.dianti.R;
import com.bumptech.glide.Glide;

/**
 * Created by Lenovo on 2016/8/24.
 */

public class ImageMatrixActivity extends PopupWindow {

    private PopupWindow popupWindow;

    private boolean isShow=true;

    private static ImageMatrixActivity instance;

    private ImageMatrixActivity() {
    }

    public static ImageMatrixActivity getInstance() {
        if (instance == null) {
            instance = new ImageMatrixActivity();
        }
        return instance;
    }

    /**
     * 显示pop页面
     */
    public void showPopupWindow(Context context,String url){
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics= new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int width=metrics.widthPixels;
        int height=metrics.heightPixels;
        View contentView = LayoutInflater.from(context).inflate(R.layout.activity_img_matrix, null);
        ImageView icon_matrix= (ImageView) contentView.findViewById(R.id.imag_matrix);
        icon_matrix.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context).load(url).into(icon_matrix);
        popupWindow= new PopupWindow(contentView, width, height);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true); // 设置是否允许在外点击使其消失
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        icon_matrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShow=false;
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.share_activity);
        popupWindow.showAsDropDown(contentView);
    }

    /**
     * 隐藏popWindow
     */
    public void dissMissPopuoWindow(){
            popupWindow.dismiss();
    }

    /**
     * 当前弹窗的状态
     * @return
     */
    public boolean isShowPopupWindow(){
        return isShow;
    }

}
