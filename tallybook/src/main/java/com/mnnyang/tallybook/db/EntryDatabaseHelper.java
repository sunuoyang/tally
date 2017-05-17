package com.mnnyang.tallybook.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mnnyang.tallybook.app.Contents;
import com.mnnyang.tallybook.app.app;

/**
 * Created by mnnyang on 17-5-16.
 */

public class EntryDatabaseHelper extends SQLiteOpenHelper {

    /**
     * 建表语句
     */
    private String CREATE_ENTRY_TABLE = "CREATE TABLE entry (" +
            "_id integer primary key autoincrement, " +
            "title text, " +
            "value real)";

    public EntryDatabaseHelper() {
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
