package com.app.dianti.refactor.activity.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.dianti.R;

/**
 * @user MycroftWong
 * @date 16/7/4
 * @time 20:38
 */
public abstract class CommonToolbarActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView mTitleTextView;

    @Override
    protected void initViews() {
        setContentView(getResId());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTitleTextView = (TextView) findViewById(R.id.title_text_view);
        mTitleTextView.setText(getTitle());
    }

    protected abstract int getResId();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
