package com.mnnyang.tallybook.activity;

import android.os.Bundle;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.base.BaseActivity;
import com.mnnyang.tallybook.fragment.SettingFragment;
import com.mnnyang.tallybook.utils.building.BindLayout;

@BindLayout(R.layout.activity_set)
public class SetActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            SettingFragment settingFragment = new SettingFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fl_fragment, settingFragment)
                    .commit();
        }

    }
}
