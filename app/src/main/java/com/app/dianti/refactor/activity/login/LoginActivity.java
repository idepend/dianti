package com.app.dianti.refactor.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.NetService;
import com.app.dianti.net.OnResponseListener;
import com.app.dianti.net.entity.UserEntity;
import com.app.dianti.refactor.activity.base.BaseActivity;
import com.app.dianti.refactor.util.Toasts;
import com.app.dianti.vo.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class LoginActivity extends BaseActivity {

    @BindView(R.id.name_edit_text)
    EditText mNameEditText;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.login_button)
    public void onClick() {
        final String name = mNameEditText.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toasts.show(this, "用户名不能为空");
            return;
        }

        final String pwd = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            Toasts.show(this, "密码不能为空");
            return;
        }

        showLoadingDialog(false);
        NetService.getInstance(this).login(name, pwd, new OnResponseListener<UserEntity>() {
            @Override
            public void onSuccess(UserEntity data) {
                final UserInfo userInfo = new UserInfo();
                userInfo.setUsername(name);
                userInfo.setName(data.getName());
                userInfo.setToken(data.getToken());
                userInfo.setRole(data.getRole());
                userInfo.setRoleName(data.getRoleName());
                AppContext.userInfo = userInfo;


            }

            @Override
            public void onFailure(String info) {

            }
        });

    }
}
