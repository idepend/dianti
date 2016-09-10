package com.app.dianti.refactor.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.app.dianti.R;
import com.app.dianti.activity.base.ElevatorListActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.refactor.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.title_text_view)
    TextView mTitleTextView;
    @BindView(R.id.username_text_view)
    TextView mUsernameTextView;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final String name = AppContext.userInfo.getName() + "\n" + AppContext.userInfo.getRoleName();
        mUsernameTextView.setText(name);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.dtda_layout, R.id.dzdt_layout, R.id.wdls_layout, R.id.dtwb_layout, R.id.dtxc_layout, R.id.dtnj_layout, R.id.yjjy_layout, R.id.tzgg_layout, R.id.quit_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dtda_layout:
                startActivity(ElevatorListActivity.getIntent(this));
                break;
            case R.id.dzdt_layout:
                break;
            case R.id.wdls_layout:
                break;
            case R.id.dtwb_layout:
                break;
            case R.id.dtxc_layout:
                break;
            case R.id.dtnj_layout:
                break;
            case R.id.yjjy_layout:
                break;
            case R.id.tzgg_layout:
                break;
            case R.id.quit_layout:
                break;
        }
    }
}
