package com.mnnyang.tallybook.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mnnyang.tallybook.model.Bill;
import com.mnnyang.tallybook.model.Budget;

import java.util.ArrayList;

/**
 * bills表操作助手
 * Created by mnnyang on 17-5-16.
 */

public class EntryHelpter {

    public EntryHelpter() {
    }

    /**
     * 查询全部
     */
    public ArrayList<Bill> queryAll() {
        ArrayList<Bill> billList = new ArrayList<>();
        SQLiteDatabase database = openDb();
        Cursor cursor = database.query(BillDatabaseHelper.bills, null, null, null, null, null,
                BillDatabaseHelper.date + " desc");

        while (cursor.moveToNext()) {
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

            System.out.println(title);
        }

        cursor.close();
        closeDb(database);
        return billList;
    }

    /**
     * 查询预算
     */
    public Budget queryBudget(int yearMonth) {
        Budget budget = null;

        SQLiteDatabase database = openDb();
        Cursor cursor = database.query(BillDatabaseHelper.bills,
                new String[]{BillDatabaseHelper.value},
                BillDatabaseHelper.yearMonth + " = ?",
                new String[]{yearMonth + ""},
                null, null, null);
        while (cursor.moveToNext()) {
            int value = cursor.getInt(cursor.getColumnIndex(BillDatabaseHelper.value));
            budget = new Budget();
            budget.setValue(value);
            budget.setYearMonth(yearMonth);
        }
        cursor.close();
        closeDb(database);
        return budget;
    }

    /**
     * 添加
     */
    public void add(Bill bill) {
        SQLiteDatabase database = openDb();

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
    }

    /**
     * 添加预算
     */
    public void addBudget(Budget budget) {
        ContentValues values = new ContentValues();
        values.put(BillDatabaseHelper.value, budget.getValue());
        values.put(BillDatabaseHelper.yearMonth, budget.getYearMonth());

        SQLiteDatabase database = openDb();
        database.insert(BillDatabaseHelper.budget, null, values);
        closeDb(database);
    }

    /**
     * 删除
     */
    public boolean delete(Bill bill) {
        SQLiteDatabase database = openDb();
        database.delete(BillDatabaseHelper.bills, "addTime = ?",
                new String[]{bill.getAddTime() + ""});

        closeDb(database);
        return false;
    }

    /**
     * 更新
     */
    public void update(Bill bill) {
        SQLiteDatabase database = openDb();
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
    }

    /**
     * 更新预算
     */
    public void updateBudget(Budget budget) {
        SQLiteDatabase database = openDb();
        ContentValues values = new ContentValues();
        values.put(BillDatabaseHelper.value, budget.getValue());

        database.update(BillDatabaseHelper.budget, values, BillDatabaseHelper.yearMonth + " = ?",
                new String[]{budget.getYearMonth() + ""});

        closeDb(database);
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
