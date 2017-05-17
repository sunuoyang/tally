package com.mnnyang.tallybook.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mnnyang on 17-5-17.
 */

public class TimeUtils {
    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String time, String format) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return ts;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long time, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(int y, int m, int d, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(y,m,d);
        res = simpleDateFormat.format(date);
        return res;
    }
}
