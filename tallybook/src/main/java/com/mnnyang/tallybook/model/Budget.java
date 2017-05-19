package com.mnnyang.tallybook.model;

/**
 * 预算
 * Created by mnnyang on 17-5-19.
 */

public class Budget {
    private float value;
    private int yearMonth;

    public float getValue() {
        return value;
    }

    public Budget setValue(float value) {
        this.value = value;
        return this;
    }

    public int getYearMonth() {
        return yearMonth;
    }

    public Budget setYearMonth(int yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }
}
