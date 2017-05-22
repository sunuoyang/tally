package com.mnnyang.tallybook.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mnnyang.tallybook.app.Contants;
import com.mnnyang.tallybook.app.app;

/**
 * Created by mnnyang on 17-5-16.
 */

public class BillDatabaseHelper extends SQLiteOpenHelper {

    public static final String bills = "bills";
    public static final String budget = "budget";

    public static final String title = "title";
    public static final String money = "money";
    public static final String mainType = "mainType";
    public static final String minorType = "minorType";
    public static final String date = "date";
    public static final String addTime = "addTime";
    public static final String notes = "notes";

    public static final String value = "value";
    public static final String yearMonth = "yearMonth";

    /**
     * 账单建表语句
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

    /**
     * 预算表建表语句
     */
    private String CREATE_BUDGET_TABLE = "CREATE TABLE " + budget + " (" +
            "_id integer primary key autoincrement, " +
            value + " real," +
            yearMonth + " integer)";


    public BillDatabaseHelper() {
        super(app.context, Contants.NAME_DB_ENTRY, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY_TABLE);
        db.execSQL(CREATE_BUDGET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
