package com.e.procrastination;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseSqlDBHelper extends SQLiteOpenHelper{
    private static final String DataBaseName = "DBCourse";
    private static final int DataBaseVersion = 1;
    String TAG = MysqlDBHelper.class.getSimpleName();
    protected String TableName;

    public CourseSqlDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
        this.TableName = TableName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS "+TableName+"( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account text not null,"    +
                "week1 TEXT,"   +
                "week2 TEXT,"   +
                "week3 TEXT,"   +
                "week4 TEXT,"   +
                "week5 TEXT,"   +
                "week6 TEXT,"   +
                "week7 TEXT,"   +
                "week8 TEXT,"   +
                "week9 TEXT,"   +
                "week10 TEXT,"   +
                "week11 TEXT,"   +
                "week12 TEXT,"   +
                "week13 TEXT,"   +
                "week14 TEXT,"   +
                "week15 TEXT,"   +
                "week16 TEXT,"   +
                "week17 TEXT,"   +
                "week18 TEXT"    +
                ");";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE "+TableName;
        sqLiteDatabase.execSQL(SQL);
    }

    //檢查資料表狀態，若無指定資料表則新增
    public void checkTable(){
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS "+TableName+"( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "account text not null,"    +
                        "week1 TEXT,"   +
                        "week2 TEXT,"   +
                        "week3 TEXT,"   +
                        "week4 TEXT,"   +
                        "week5 TEXT,"   +
                        "week6 TEXT,"   +
                        "week7 TEXT,"   +
                        "week8 TEXT,"   +
                        "week9 TEXT,"   +
                        "week10 TEXT,"   +
                        "week11 TEXT,"   +
                        "week12 TEXT,"   +
                        "week13 TEXT,"   +
                        "week14 TEXT,"   +
                        "week15 TEXT,"   +
                        "week16 TEXT,"   +
                        "week17 TEXT,"   +
                        "week18 TEXT"    +
                        ");");
            cursor.close();
        }
    }
    //取得有多少資料表
    public ArrayList<String> getTables(){
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master", null);
        ArrayList<String> tables = new ArrayList<>();
        while (cursor.moveToNext()){
            String getTab = new String (cursor.getBlob(0));
            if (getTab.contains("android_metadata")){}
            else if (getTab.contains("sqlite_sequence")){}
            else tables.add(getTab.substring(0,getTab.length()-1));

        }
        return tables;
    }
    //新增資料
    public void addData(String account, String[] CourseRecord) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("week1", CourseRecord[0]);
        values.put("week2", CourseRecord[1]);
        values.put("week3", CourseRecord[2]);
        values.put("week4", CourseRecord[3]);
        values.put("week5", CourseRecord[4]);
        values.put("week6", CourseRecord[5]);
        values.put("week7", CourseRecord[6]);
        values.put("week8", CourseRecord[7]);
        values.put("week9", CourseRecord[8]);
        values.put("week10", CourseRecord[9]);
        values.put("week11", CourseRecord[10]);
        values.put("week12", CourseRecord[11]);
        values.put("week13", CourseRecord[12]);
        values.put("week14", CourseRecord[13]);
        values.put("week15", CourseRecord[14]);
        values.put("week16", CourseRecord[15]);
        values.put("week17", CourseRecord[16]);
        values.put("week18", CourseRecord[17]);
        int error = (int)db.insert(TableName, null, values);
        return;
    }

    //以帳號搜尋特定資料
    public HashMap<String, Object> searchByAccount(String getAccount) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE account =" + "'" + getAccount + "'", null);
        HashMap<String, Object> hashMap = new HashMap<>();
        c.moveToFirst();
        if(c.getCount() == 0){
            return null;
        }
        String id = c.getString(0);
        String account = c.getString(1);
        String[] CourseRecord = new String[18];
        for(int i = 2;i<20;i++){
            CourseRecord[i-2] = c.getString(i);
        }

        hashMap.put("id", id);
        hashMap.put("account", account);
        hashMap.put("course", CourseRecord);
        return hashMap;
    }

    //修改資料
    public void modify(String id, String account,String[] CourseRecord) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("week1", CourseRecord[0]);
        values.put("week2", CourseRecord[1]);
        values.put("week3", CourseRecord[2]);
        values.put("week4", CourseRecord[3]);
        values.put("week5", CourseRecord[4]);
        values.put("week6", CourseRecord[5]);
        values.put("week7", CourseRecord[6]);
        values.put("week8", CourseRecord[7]);
        values.put("week9", CourseRecord[8]);
        values.put("week10", CourseRecord[9]);
        values.put("week11", CourseRecord[10]);
        values.put("week12", CourseRecord[11]);
        values.put("week13", CourseRecord[12]);
        values.put("week14", CourseRecord[13]);
        values.put("week15", CourseRecord[14]);
        values.put("week16", CourseRecord[15]);
        values.put("week17", CourseRecord[16]);
        values.put("week18", CourseRecord[17]);
        db.update(TableName, values, "_id = " + id, null);
    }

    //刪除全部資料
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM"+TableName);
    }

    //以Account刪除資料
    public void deleteByAccount(String account){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE account =" + "'" + account + "'", null);
        String id = null;
        String searchAccount;
        while (c.moveToNext()) {
            id = c.getString(0);
            searchAccount = c.getString(1);
            if(searchAccount.equals(account)){
                break;
            }
        }
        db.delete(TableName,"_id = " + id,null);
    }

}


