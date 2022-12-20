package com.e.procrastination;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class MysqlDBHelper extends SQLiteOpenHelper{
    private static final String DataBaseName = "DBUser";
    private static final int DataBaseVersion = 1;
    String TAG = MysqlDBHelper.class.getSimpleName();
    protected String TableName;

    public MysqlDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
        this.TableName = TableName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS "+TableName+"( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account text not null,"    +
                "password TEXT not null,"   +
                "name TEXT not null,"       +
                "photo BLOB,"               +
                "major TEXT,"               +
                "secureQ TEXT not null,"     +
                "secureA TEXT not null"      +
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
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "account text not null,"    +
                        "password TEXT not null,"   +
                        "name TEXT not null,"       +
                        "photo BLOB,"               +
                        "major TEXT,"               +
                        "secureQ TEXT not null,"     +
                        "secureA TEXT not null"      +
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
    public void addData(String account, String password, String name, byte[] photo,String major,String question,String answer) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("password", password);
        values.put("name", name);
        values.put("photo",photo);
        values.put("major", major);
        values.put("secureQ", question);
        values.put("secureA", answer);
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
        String password = c.getString(2);
        String name = c.getString(3);
        byte[] photo = c.getBlob(4);
        String major = c.getString(5);
        String question = c.getString(6);
        String answer = c.getString(7);

        hashMap.put("id", id);
        hashMap.put("account", account);
        hashMap.put("password", password);
        hashMap.put("name", name);
        hashMap.put("photo", photo);
        hashMap.put("major", major);
        hashMap.put("secureQ", question);
        hashMap.put("secureA", answer);
        return hashMap;
    }

    //修改資料
    public void modify(String id, String account, String password, String name, byte[] photo,String major,String question,String answer) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("account", account);
        values.put("password", password);
        values.put("name", name);
        values.put("photo", photo);
        values.put("major", major);
        values.put("secureQ", question);
        values.put("secureA", answer);
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

