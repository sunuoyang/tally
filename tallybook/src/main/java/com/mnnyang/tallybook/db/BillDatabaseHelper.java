package com.mnnyang.tallybook.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mnnyang.tallybook.app.Contents;
import com.mnnyang.tallybook.app.app;

/**
 * Created by mnnyang on 17-5-16.
 */

public class BillDatabaseHelper extends SQLiteOpenHelper {

    public static final String bills = "bills";

    public static final String title = "title";
    public static final String money = "money";
    public static final String mainType = "mainType";
    public static final String minorType = "minorType";
    public static final String date = "date";
    public static final String addTime = "addTime";
    public static final String notes = "notes";
    /**
     * 建表语句
     */
    private String CREATE_ENTRY_TABLE = "CREATE TABLE " + bills + " (" +
            "_id integer primary key autoincrement, " +
            title + " text, " +
            money + " real, " +
            mainType + " text, " +
            minorType + " text, " +
            date + " integer, " +
            addTime + " integer, " +
            notes + " text)";

    public BillDatabaseHelper() {
        super(app.context, Contents.NAME_DB_ENTRY, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
