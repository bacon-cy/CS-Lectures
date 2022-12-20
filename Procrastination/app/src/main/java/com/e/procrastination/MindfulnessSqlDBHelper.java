package com.e.procrastination;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class MindfulnessSqlDBHelper extends SQLiteOpenHelper{
    private static final String DataBaseName = "DBAPP";
    private static final int DataBaseVersion = 1;
    protected String TableName;

    public MindfulnessSqlDBHelper(@Nullable Context context, String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
        this.TableName = TableName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS "+ TableName+ "( " +
                "time TEXT PRIMARY KEY AUTOINCREMENT," +
                "posture text," +
                "memo TEXT," + ");";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE "+TableName;
        sqLiteDatabase.execSQL(SQL);
    }

    //檢查資料表狀態，若無指定資料表則新增
    public void checkTable(){
        Cursor c = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TableName + "'", null);
        if (c != null) {
            if (c.getCount() == 0)
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS "+ TableName+ "( " +
                        "time TEXT PRIMARY KEY AUTOINCREMENT," +
                        "posture TEXT," +
                        "memo TEXT," + ");");
            c.close();
        }
    }
    //新增資料
    public void addData(String time, String posture, String memo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", time);
        values.put("posture", posture);
        values.put("memo", memo);
        int error = (int)db.insert(TableName, null, values);
    }

    //以time搜尋特定資料
    public HashMap<String, Object> searchByTime(String getTime) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE time =" + "'" + getTime + "'", null);
        HashMap<String, Object> hashMap = new HashMap<>();
        c.moveToFirst();
        String time = c.getString(0);
        String posture = c.getString(1);
        String memo = c.getString(2);

        hashMap.put("time", time);
        hashMap.put("posture", posture);
        hashMap.put("memo", memo);
        c.close();
        return hashMap;
    }

    //修改資料
    public void modify(String time, String posture, String memo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", time);
        values.put("posture", posture);
        values.put("memo", memo);
        db.update(TableName, values, "time = " + time, null);
    }

    //刪除全部資料
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM"+TableName);
    }

    //刪除資料
    public void deleteRecord(String time) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE time =" + "'" + time + "'", null);
        db.delete(TableName, "time = " + time, null);
        c.close();
    }

}
