package com.mnnyang.tallybook.db;

import android.database.sqlite.SQLiteDatabase;

import com.mnnyang.tallybook.model.Entry;

/**
 * Created by mnnyang on 17-5-16.
 */

public class EntryHelpter {

    private SQLiteDatabase database;

    public EntryHelpter() {
    }

    /**
     * 查询全部
     */
    public boolean queryAll(Entry entry) {
        database = openDb();

        closeDb(database);
        return false;
    }

    /**
     * 添加
     */
    public boolean add(Entry entry) {
        database = openDb();

        closeDb(database);
        return false;
    }

    /**
     * 删除
     */
    public boolean delete(Entry entry) {
        database = openDb();

        closeDb(database);
        return false;
    }

    /**
     * 更新
     */
    public boolean update(Entry entry) {
        database = openDb();

        closeDb(database);
        return false;
    }

    /**
     * 打开
     */
    private SQLiteDatabase openDb() {
        return new EntryDatabaseHelper().getWritableDatabase();
    }

    /**
     * 关闭
     */
    private void closeDb(SQLiteDatabase database) {
        database.close();
    }
}
