package com.lxkj.xpp.mvpdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

/**
 * Created by XPP on 2016/12/4/004.
 */

public abstract class BaseActionBarActivity extends ActionBarActivity {
    protected Toolbar mToolbar;
    private String TAG = BaseActionBarActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        setupToolBar();
        initWidgets();
        afterOncreate();
    }

    /**
     * 获取Activity的布局ID
     *
     * @return
     */
    protected abstract int getContentViewResId();

    /**
     * 初始化toolbar
     */
    protected void setupToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initWidgets() {

    }

    protected void afterOncreate() {

    }
}
