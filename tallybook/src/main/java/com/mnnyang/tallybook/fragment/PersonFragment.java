package com.mnnyang.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.fragment.base.BaseFragment;

/**
 * Created by mnnyang on 17-5-18.
 */

public class PersonFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_home, null);
    }
}
