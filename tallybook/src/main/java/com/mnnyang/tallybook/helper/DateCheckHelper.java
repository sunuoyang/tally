package com.mnnyang.tallybook.helper;

import com.mnnyang.tallybook.utils.TimeUtils;

/**
 * <p>检查日期</p>
 * <P>不能选择未来的日期</P>
 * Created by mnnyang on 17-5-17.
 */

public class DateCheckHelper {
    private Listener listener;
    int year, month, day;

    public interface Listener {
        void succeed(int date, int year, int month, int dayOfMonth, String name);

        void fail();
    }

    public DateCheckHelper(int year, int month, int day, Listener listener) {
        this.listener = listener;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public DateCheckHelper start() {
        String month = String.valueOf(this.month);
        String day = String.valueOf(this.day);

        month = month.length() == 1 ? "0" + month : month;
        day = day.length() == 1 ? "0" + day : day;

        int selectTime = Integer.decode(year + month + day);
        String nowTimeText = TimeUtils.stampToDate(System.currentTimeMillis(), "yyyyMMdd");
        int nowTime = Integer.decode(nowTimeText);

        if (selectTime > nowTime) {
            listener.fail();
        } else {
            String name = "";
            if (nowTime == selectTime) {
                name = "今天";
            } else if (nowTime - selectTime == 1) {
                name = "昨天";
            }

            listener.succeed(selectTime, this.year, this.month, this.day, name);
        }
        return this;
    }

}
