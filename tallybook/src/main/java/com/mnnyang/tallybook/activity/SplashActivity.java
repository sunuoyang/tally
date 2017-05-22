package com.mnnyang.tallybook.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.base.BaseActivity;
import com.mnnyang.tallybook.db.BillDatabaseHelper;

public class SplashActivity extends BaseActivity {

    private final static int TIME_DELATED = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoActivity(MainActivity.class, null, null, true);
            }
        }, TIME_DELATED);

        SQLiteDatabase database = new BillDatabaseHelper().getWritableDatabase();
    }
}
