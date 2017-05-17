package com.mnnyang.tallybook.fragment.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnnyang.tallybook.utils.LogUtils;


/**
 * Created by mnnyang on 17-5-15.
 */

public abstract class BaseFragment extends BaseLazyFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.d(this, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }
}
