package com.mnnyang.tallybook.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mnnyang.tallybook.model.Bill;

import java.util.ArrayList;

/**
 * bills表操作助手
 * Created by mnnyang on 17-5-16.
 */

public class EntryHelpter {
    private SQLiteDatabase database;

    public EntryHelpter() {
    }

    /**
     * 查询全部
     */
    public ArrayList<Bill> queryAll() {
        ArrayList<Bill> billList = new ArrayList<>();
        database = openDb();
        Cursor cursor = database.query(BillDatabaseHelper.bills, null, null, null, null, null,
                "order by " + BillDatabaseHelper.date + " desc");

        if (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(BillDatabaseHelper.title));
            String mainType = cursor.getString(cursor.getColumnIndex(BillDatabaseHelper.mainType));
            String minorType = cursor.getString(cursor.getColumnIndex(BillDatabaseHelper.minorType));
            String notes = cursor.getString(cursor.getColumnIndex(BillDatabaseHelper.notes));
            float money = cursor.getFloat(cursor.getColumnIndex(BillDatabaseHelper.money));
            int date = cursor.getInt(cursor.getColumnIndex(BillDatabaseHelper.date));

            long addTime = System.currentTimeMillis();

            Bill bill = new Bill()
                    .setTitle(title)
                    .setDate(date)
                    .setAddTime(addTime)
                    .setMoney(money)
                    .setMainType(mainType)
                    .setMinorType(minorType)
                    .setNotes(notes);

            billList.add(bill);
        }

        cursor.close();
        closeDb(database);
        return billList;
    }

    /**
     * 添加
     */
    public boolean add(Bill bill) {
        database = openDb();

        ContentValues values = new ContentValues();
        values.put(BillDatabaseHelper.title, bill.getTitle());
        values.put(BillDatabaseHelper.addTime, bill.getAddTime());
        values.put(BillDatabaseHelper.date, bill.getDate());
        values.put(BillDatabaseHelper.money, bill.getMoney());
        values.put(BillDatabaseHelper.mainType, bill.getMainType());
        values.put(BillDatabaseHelper.minorType, bill.getMinorType());
        values.put(BillDatabaseHelper.notes, bill.getNotes());

        database.insert(BillDatabaseHelper.bills, null, values);
        closeDb(database);
        return false;
    }

    /**
     * 删除
     */
    public boolean delete(Bill bill) {
        database = openDb();
        database.delete(BillDatabaseHelper.bills, "addTime = ?",
                new String[]{bill.getAddTime() + ""});

        closeDb(database);
        return false;
    }

    /**
     * 更新
     */
    public boolean update(Bill bill) {
        database = openDb();
        ContentValues values = new ContentValues();
        values.put(BillDatabaseHelper.title, bill.getTitle());
        values.put(BillDatabaseHelper.date, bill.getDate());
        values.put(BillDatabaseHelper.money, bill.getMoney());
        values.put(BillDatabaseHelper.mainType, bill.getMainType());
        values.put(BillDatabaseHelper.minorType, bill.getMinorType());
        values.put(BillDatabaseHelper.notes, bill.getNotes());

        database.update(BillDatabaseHelper.bills, values, "addTime = ?",
                new String[]{bill.getAddTime() + ""});

        closeDb(database);
        return false;
    }

    /**
     * 打开
     */
    private SQLiteDatabase openDb() {
        return new BillDatabaseHelper().getWritableDatabase();
    }

    /**
     * 关闭
     */
    private void closeDb(SQLiteDatabase database) {
        database.close();
    }
}
