package com.e.procrastination;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ProfileMainActivity extends AppCompatActivity {
    private static final String DB_NAME = "DBUser";
    private static final int DB_VERSION = 1;
    private static String TABLE_NAME = "Root";
    private static SQLiteDatabase db;
    protected static MysqlDBHelper DBHelper;

    TextView nameUI,majorUI,accountUI;
    ImageView photoUI,settingUI,backUI;

    HashMap<String, Object> getNowHashMap = new HashMap<>();//取得被選中的項目資料

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main_activity);
        DBHelper = LoginActivity.DBHelper;
        DBHelper.checkTable();
        getAllView();
        setAllView();

        getNowHashMap = DBHelper.searchByAccount(MyMainActivity.account);
        UpdateInfo(getNowHashMap);
    }
    private void getAllView(){
        nameUI= findViewById(R.id.nameTV);
        majorUI= findViewById(R.id.majorTV);
        accountUI= findViewById(R.id.accountTV);
        photoUI= findViewById(R.id.photoIV);
        settingUI = findViewById(R.id.settingIV);
        backUI = findViewById(R.id.backIV);
    }
    protected void UpdateInfo(HashMap<String,Object> data){
        String account = (String) data.get("account");
        String name = (String) data.get("name");
        String major = (String) data.get("major");
        byte[] photo = (byte[]) data.get("photo");

        accountUI.setText(account);
        nameUI.setText(name);
        if(major == null){
            majorUI.setText("成功大學");
        }else{
            majorUI.setText("成大 "+major);
        }
    }
    protected void setAllView(){
        // settingUI
        settingUI.setClickable(true);
        settingUI.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(ProfileMainActivity.this, ProfileSettingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",(String) getNowHashMap.get("account"));
            intent.putExtras(bundle);
            startActivity(intent);
        });
        // backUI
        backUI.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(ProfileMainActivity.this, MyMainActivity.class);
            startActivity(intent);
        });
    }
}