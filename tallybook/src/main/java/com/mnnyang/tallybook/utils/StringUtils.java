package com.mnnyang.tallybook.utils;

import android.support.annotation.StringRes;

import com.mnnyang.tallybook.app.app;

/**
 * Created by mnnyang on 17-5-21.
 */

public class StringUtils {
    public static String getString(@StringRes int stringResId) {
        return app.context.getString(stringResId);
    }
}
