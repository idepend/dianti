package com.app.dianti.activity.notice;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;

/**
 * Created by Lenovo on 2016/9/9.
 */

public class NoticeDetailActivity extends BaseActivity {

    private TextView titleTv;
    private TextView contentTv;
    private TextView timeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        initTitleBar("公告详情");
        initView();
        initData();
    }
    protected void initTitleBar(String title) {
        leftBtn = findViewById(R.id.left_btn);
        if (leftBtn != null) {

            leftBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        TextView titleBarName = (TextView) findViewById(R.id.titleBarName);
        if (titleBarName != null) {
            titleBarName.setText(title);
        }
    }

    private void initView() {
        titleTv= (TextView) findViewById(R.id.titleTv);
        contentTv= (TextView) findViewById(R.id.contentTv);
        timeTv= (TextView) findViewById(R.id.timeTv);
    }

    /**
     * 字符串
     *
     * @return
     */
    private String descString() {
        return "不是让自己痛苦，就是让自己坚强，花的力气其实相同。同样的遭遇，可能使你不快乐，觉得处处不如意，换个角度，有可能就让你感到愉快和如愿。你的心，永远是最忠诚于你的东西。你的任务，就是学着让你的心灵为你工作，而不是处处和你作对。一个人，千万不能跟自己作对。\n" + "<img src='" + R.drawable.dianziditu
                + "'/>" + "+志不立，天下无可成之事。赚钱之道很多，但是找不到赚钱的种子，便成不了事业家。自古成功在尝试。\n" + "<img src='" + R.drawable.dangan
                + "'/>" + "+兜兜转转爱恨间，几人快乐几人欢。感情总是这样，明明很在乎，却口是心非地装着无所谓；明明很看重，却言不由衷说不需要谁懂。人生是场缘，" +
                "聚散总会有，擦肩的是最客，携手的是情，不来不往的没缘分，珍惜人生梦一场。别怨，情深意浓缘分簿；别恨，快乐时少烦恼多。无怨无悔才是人生最美。" +
                "路有短有长，事有喜有伤，味有涩有凉。走过千山万水，还是小家最美，经过姹紫嫣红，还是淡然长久，越过繁华喧闹，还是平淡最好，唯有经历，" +
                "才能懂得。"; //"<img src='"+ http://images.csdn.net/20130609/zhuanti.jpg + "'/>" + "";

    }

    private void initData() {
        contentTv.setText(Html.fromHtml(descString(), getImageGetterInstance(), null));
    }

    /**
     * ImageGetter用于text图文混排
     *
     * @return
     */
    public Html.ImageGetter getImageGetterInstance() {
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int fontH = (int) (getResources().getDimension(
                        R.dimen.dp_60) * 1.5);
                int id = Integer.parseInt(source);
                Drawable d = getResources().getDrawable(id);
                int height = fontH;
                int width = (int) ((float) d.getIntrinsicWidth() / (float) d
                        .getIntrinsicHeight()) * fontH;
                if (width == 0) {
                    width = d.getIntrinsicWidth();
                }
                d.setBounds(0, 0, width, height);
                return d;
            }
        };
        return imgGetter;
    }

}
