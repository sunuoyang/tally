package com.mnnyang.tallybook.activity;

import android.view.Menu;
import android.view.MenuItem;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.base.BaseActivity;
import com.mnnyang.tallybook.utils.building.BindLayout;

/**
 * Created by mnnyang on 17-5-19.
 */

@BindLayout(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_set:
                setClick();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setClick() {
        gotoActivity(SetActivity.class, null, null, false);
    }
}
