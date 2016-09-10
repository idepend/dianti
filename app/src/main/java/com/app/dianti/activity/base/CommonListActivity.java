package com.app.dianti.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.dianti.R;
import com.app.dianti.activity.ElevatorQueryActivity;
import com.app.dianti.activity.inspection.InspectionAddActivity;
import com.app.dianti.activity.maintenance.StartMaintenanceActivity;
import com.app.dianti.util.ExitActivityUtil;

public abstract class CommonListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar mToolbar;
    private TextView mTitleTextView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ListView mListView;

    protected int mCurrentPage = 1;
    protected int mTotalPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitActivityUtil.getInstance().addActivity(this);
        setContentView(R.layout.activity_common_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTitleTextView = (TextView) findViewById(R.id.title_text_view);
        mTitleTextView.setText(getTitle());

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mListView = (ListView) findViewById(R.id.list_view);

        mListView.setAdapter(getAdapter());


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (mCurrentPage >= mTotalPage) {
                    return;
                }

                if (totalItemCount - (firstVisibleItem + visibleItemCount) < 3) {
                    loadData(mCurrentPage + 1);
                }

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);

        loadData(1);
    }

    protected abstract void loadData(int currentPage);

    protected abstract ArrayAdapter getAdapter();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isHistory()) {
            getMenuInflater().inflate(getMenuResId(), menu);
        }
        return true;
    }

    protected abstract int getMenuResId();

    protected abstract boolean isHistory();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(this, StartMaintenanceActivity.class));
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_OK);
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(this, ElevatorQueryActivity.class));
        }
//        else if (item.getItemId() == R.id.action_add_rescue) {
//            startActivity(new Intent(this, StatementActivity.class));
//        }
        else if (item.getItemId() == R.id.action_add_inspection) {
            startActivity(new Intent(this, InspectionAddActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadData(1);
    }
}
