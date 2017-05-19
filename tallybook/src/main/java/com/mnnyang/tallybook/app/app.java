package com.mnnyang.tallybook.app;

import android.app.Application;
import android.content.Context;

import com.mnnyang.tallybook.utils.ScreenUtils;
import com.mnnyang.tallybook.utils.ToastUtils;

/**
 * Created by mnnyang on 17-5-16.
 */

public class app extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initUtils();
    }

    private void initUtils() {
        ScreenUtils.init(this);
        ToastUtils.init(this);
    }
}
