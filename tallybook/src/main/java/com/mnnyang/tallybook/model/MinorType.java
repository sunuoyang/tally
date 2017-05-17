package com.mnnyang.tallybook.model;

/**
 * 账单基本类型
 * Created by mnnyang on 17-5-17.
 */

public class MinorType {
    private String typeName;
    private int typeIconId;
    private int tintColor = -1;

    public int getTintColor() {
        return tintColor;
    }

    public MinorType setTintColor(int tintColor) {
        this.tintColor = tintColor;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public MinorType setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public int getTypeIconId() {
        return typeIconId;
    }

    public MinorType setTypeIconId(int typeIconId) {
        this.typeIconId = typeIconId;
        return this;
    }
}
