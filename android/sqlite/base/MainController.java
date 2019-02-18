package com.hlw.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainController {

    public void add(SQLiteDatabase db, String name) {
        //创建存放数据的ContentValues对象
        ContentValues values = new ContentValues();
        values.put("name", name);
        //数据库执行插入命令
        db.insert("user", null, values);
    }

    public void delete(SQLiteDatabase db, String name) {
        db.delete("user", "name=?", new String[]{name});
    }

    public void update(SQLiteDatabase db, String oldName, String newName) {
        ContentValues values2 = new ContentValues();
        values2.put("name", newName);
        db.update("user", values2, "name = ?", new String[]{oldName});
    }

    public String query(SQLiteDatabase db) {
        if (db == null)
            throw new NullPointerException("SQLiteDatabase is null");
        //创建游标对象
        Cursor cursor =
                db.query("user", new String[]{"ID", "name"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String id = cursor.getString(cursor.getColumnIndex("ID"));
            sb.append(String.format("id = %s name = %s", id, name)).append("\n");
        }

        cursor.close();
        String result = sb.toString();
        sb.delete(0, sb.length());
        sb = null;
        return result;
    }

    public String[] queryTableColumnName(SQLiteDatabase db, String tableName) {
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE 0", null);
        try {
            return c.getColumnNames();
        } catch (Exception e) {
            return null;
        } finally {
            c.close();
        }
    }

}
