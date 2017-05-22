package com.mnnyang.tallybook.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.utils.LogUtils;
import com.mnnyang.tallybook.utils.building.Binder;

/**
 * <p>Activity 基类</p>
 * Created by mnnyang on 17-4-11.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();

    protected Toolbar toolbar;

    protected Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");

        initWindow();
        Binder.bind(this);
        initView();
        initListener();
        initData();
    }

    /**
     * Binder.bind(this);之前<br>
     */
    protected void initWindow() {
    }

    /**
     * <p>初始化view</p>
     * <p>基类会初始化Toolbar为返回操作 如果存在</p>
     */
    protected void initView() {
        initBaseToolbar();
    }

    /**
     * <p>统一对有toobar的activity设置点击toobar的home按钮作返回操作</p>
     */
    private void initBaseToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            LogUtils.i(this, "have toolbar");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            return;
        }
        LogUtils.i(this, "no have toolbar");
    }


    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //toolbar home按钮返回
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }

    /**
     * 启动Activity
     */
    public void gotoActivity(Class<? extends BaseActivity> activityClass, String name, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this, activityClass);
        if (!TextUtils.isEmpty(name) && bundle != null) {
            intent.putExtra(name, bundle);
        }
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }
}
