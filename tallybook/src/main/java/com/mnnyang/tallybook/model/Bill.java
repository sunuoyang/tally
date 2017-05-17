package com.mnnyang.tallybook.model;

/**
 * 账单
 * Created by mnnyang on 17-5-16.
 */

public class Bill {
    /**
     * 标题
     */
    String title;
    /**
     * 金额
     */
    float money;
    /**
     * <p>主要类型</p>
     * <P>收入 支出</P>
     */
    String mainType;

    /**
     * <P>次要类型</P>
     * <P>餐饮 娱乐 购物...</P>
     */
    String minorType;
    /**
     * 日期
     */
    int date;
    /**
     * 备注
     */
    String notes;

    long addTime;

    public long getAddTime() {
        return addTime;
    }

    public Bill setAddTime(long addTime) {
        this.addTime = addTime;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Bill setTitle(String title) {
        this.title = title;
        return this;
    }

    public float getMoney() {
        return money;
    }

    public Bill setMoney(float money) {
        this.money = money;
        return this;
    }

    public String getMainType() {
        return mainType;
    }

    public Bill setMainType(String mainType) {
        this.mainType = mainType;
        return this;
    }

    public String getMinorType() {
        return minorType;
    }

    public Bill setMinorType(String minorType) {
        this.minorType = minorType;
        return this;
    }

    public int getDate() {
        return date;
    }

    public Bill setDate(int date) {
        this.date = date;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Bill setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
