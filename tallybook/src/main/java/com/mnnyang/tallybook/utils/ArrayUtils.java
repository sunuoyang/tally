package com.mnnyang.tallybook.utils;

import android.content.res.TypedArray;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;

import com.mnnyang.tallybook.app.app;

/**
 * Created by mnnyang on 17-5-18.
 */

public class ArrayUtils {
    /**
     * 数据获取ids
     *
     * @return
     */
    @NonNull
    public static int[] getIds(@ArrayRes int arrayResId) {
        TypedArray minorTypeArray = app.context.getResources().obtainTypedArray(arrayResId);
        int length = minorTypeArray.length();
        int[] monorTypeIconResIds = new int[length];
        for (int i = 0; i < length; i++) {
            monorTypeIconResIds[i] = minorTypeArray.getResourceId(i, 0);
        }
        minorTypeArray.recycle();

        return monorTypeIconResIds;
    }

    public static String[] getStringArray(@ArrayRes int arrayResId) {
        return app.context.getResources().getStringArray(arrayResId);
    }

    public static int[] getIntArray(@ArrayRes int arrayResId) {
        return app.context.getResources().getIntArray(arrayResId);
    }
}
