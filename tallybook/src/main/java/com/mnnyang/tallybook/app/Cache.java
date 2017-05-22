package com.mnnyang.tallybook.app;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.utils.ArrayUtils;

/**
 * 全局缓存
 * Created by mnnyang on 17-5-21.
 */

public class Cache {

    /**
     * 支出类型
     */
    public static String[] monorTypeTitleExpend;
    /**
     * 支出类型图标
     */
    public static int[] iconsIdExpend;
    /**
     * 对于着色
     */
    public static int[] tintColorsExpend;
    /**
     * 收入类型
     */
    public static String[] monorTypeTitleIncome;
    /**
     * 收入类型图标
     */
    public static int[] iconsIdIncome;
    /**
     * 收入着色
     */
    public static int[] tintColorsIncome;

    /**
     * 月份
     */
    public static String[] monthHan = {
            " ", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    public static void init() {
        monorTypeTitleExpend = ArrayUtils.getStringArray(R.array.minor_type_expend);
        iconsIdExpend = ArrayUtils.getIds(R.array.minor_type_drawable_ids_expend);
        tintColorsExpend = ArrayUtils.getIntArray(R.array.minor_type_tint_expend);
        monorTypeTitleIncome = ArrayUtils.getStringArray(R.array.minor_type_income);
        iconsIdIncome = ArrayUtils.getIds(R.array.minor_type_drawable_ids_income);
        tintColorsIncome = ArrayUtils.getIntArray(R.array.minor_type_tint_income);
    }
}
