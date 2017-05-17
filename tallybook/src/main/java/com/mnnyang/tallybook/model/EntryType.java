package com.mnnyang.tallybook.model;

/**
 * Created by mnnyang on 17-5-17.
 */

public class EntryType {
    private String typeName;
    private int typeIconId;
    private int tintColor = -1;

    public int getTintColor() {
        return tintColor;
    }

    public EntryType setTintColor(int tintColor) {
        this.tintColor = tintColor;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public EntryType setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public int getTypeIconId() {
        return typeIconId;
    }

    public EntryType setTypeIconId(int typeIconId) {
        this.typeIconId = typeIconId;
        return this;
    }
}
