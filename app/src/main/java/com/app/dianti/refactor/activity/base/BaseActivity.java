package com.app.dianti.refactor.activity.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.app.dianti.R;

/**
 * Created by Mycroft_Wong on 2015/12/30.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        initFields(savedInstanceState);
        super.onCreate(savedInstanceState);
        initViews();
        loadData(savedInstanceState);
    }

    protected abstract void initFields(@Nullable Bundle savedInstanceState);

    protected abstract void initViews();

    protected abstract void loadData(@Nullable Bundle savedInstanceState);

    private Dialog mLoadingDialog;

    protected final void showLoadingDialog() {
        showLoadingDialog(true);
    }

    protected final void showLoadingDialog(boolean cancelable) {
        if (mLoadingDialog == null) {
            Dialog dialog = new Dialog(this, R.style.LoadingDialogStyle);
            dialog.setContentView(R.layout.dialog_loading);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
            dialog.setCancelable(cancelable);
            mLoadingDialog = dialog;
        }
        mLoadingDialog.show();
    }

    protected final void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.cancel();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            }
            mLoadingDialog = null;
        }
        super.onDestroy();
    }
}

