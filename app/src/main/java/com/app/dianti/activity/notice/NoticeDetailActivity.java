package com.app.dianti.activity.notice;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.BaseActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.util.DateUtils;
import com.app.dianti.vo.ResponseData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Lenovo on 2016/9/9.
 */

public class NoticeDetailActivity extends BaseActivity {

    private static final String TAG ="wj" ;
    private TextView titleTv;
    private TextView contentTv;
    private TextView timeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        initTitleBar("公告详情");
        String id = getIntent().getStringExtra("id");
        initView();
        initData(id);
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

    private void initData(String id) {
    PostFormBuilder postFormBuilder = OkHttpUtils.post().url(AppContext.API_NOTICE_DETAIL).addParams("token", AppContext.userInfo.getToken())
            .addParams("id", id);
    postFormBuilder.build().execute(new StringCallback() {
        @Override
        public void onResponse(String respData, int i) {
            ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                if (responseData.getCode().equals("200")) {
                    Map<String, Object> result = responseData.getData();
                    if (result == null) {
                        return;
                    }
                    try {
                        titleTv.setText(result.get("title").toString());
                        final String content=result.get("content").toString();
                        Thread t = new Thread(new Runnable() {
                            Message msg = Message.obtain();
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                /**
                                 * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
                                 * fromHtml (String source, Html.ImageGetterimageGetter,
                                 * Html.TagHandler
                                 * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
                                 * (String source)方法中返回图片的Drawable对象才可以。
                                 */
                                Html.ImageGetter imageGetter = new Html.ImageGetter() {

                                    @Override
                                    public Drawable getDrawable(String source) {
                                        // TODO Auto-generated method stub
                                        URL url;
                                        Drawable drawable = null;
                                        try {
                                            url = new URL(source);
                                            drawable = Drawable.createFromStream(
                                                    url.openStream(), null);
                                            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                                            int width = wm.getDefaultDisplay().getWidth();
                                            drawable.setBounds(0,0, width-55,350);
                                        } catch (MalformedURLException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        return drawable;
                                    }
                                };
                                CharSequence test = Html.fromHtml(content, imageGetter, null);
                                msg.what = 0x101;
                                msg.obj = test;
                                handler.sendMessage(msg);
                            }
                        });
                        t.start();

                        timeTv.setText("时间："+DateUtils.secondToDateStr2(result.get("create_date").toString()));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "数据解析失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

               } else {
                    Toast.makeText(NoticeDetailActivity.this,"加载数据失败!请返回后重试。",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }
        });
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
          super.handleMessage(msg);
            if (msg.what == 0x101) {
                contentTv.setText((CharSequence) msg.obj);
            }
        }
    };

}
