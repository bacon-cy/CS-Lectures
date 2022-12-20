package com.e.procrastination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends Activity {
    private static final String DB_NAME = "DBUser";
    private static final int DB_VERSION = 1;
    public static SharedPreferences.Editor editor;
    private static String TABLE_NAME = "Root";
    private static SQLiteDatabase db;
    protected static MysqlDBHelper DBHelper;
    public static String account;
    public static SharedPreferences share;
    EditText acc_loginUI,pass_loginUI;
    Button loginUI;
    TextView get_passUI,signupUI;
    boolean[] OK = {false,false};

    HashMap<String, Object> getNowHashMap = new HashMap<>();//取得被選中的項目資料

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper = new MysqlDBHelper(this, DB_NAME
                , null, DB_VERSION, TABLE_NAME);//初始化資料庫
        DBHelper.checkTable();
        share = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = share.edit();
        if (share.getBoolean("AUTO_CHECK", false)) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MyMainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.login_main_activity);
            getAllView();
            setAllView();
        }
    }
    private void getAllView(){
        acc_loginUI= findViewById(R.id.accountET);
        pass_loginUI= findViewById(R.id.passwordET);
        loginUI= findViewById(R.id.loginBtn);
        get_passUI= findViewById(R.id.getpassTV);
        signupUI = findViewById(R.id.signupTV);
    }
    private void setAllView(){
        // acc_login
        acc_loginUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String account = acc_loginUI.getText().toString();
                if (account.isEmpty()){
                    acc_loginUI.setError("不可留白");
                    OK[0] = false;
                }else if(!account.matches("[a-zA-Z0-9._-]+@[a-z]?.+\\.[a-z]+")){
                    acc_loginUI.setError("請輸入E-mail");
                    OK[0] = false;
                }else {
                    OK[0] = true;
                }
                if(OK()){
                    loginUI.setEnabled(true);
                }else{
                    loginUI.setEnabled(false);
                }
            }
        });
        // pass_login
        pass_loginUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = pass_loginUI.getText().toString();
                if (password.isEmpty()){
                    pass_loginUI.setError("不可留白");
                    OK[1] = false;
                }else if (!password.matches("^.{8,15}$")){
                    pass_loginUI.setError("密碼應介於8至15個字元");
                    OK[1] = false;
                }else if(!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}$")){
                    pass_loginUI.setError("請輸入至少一個數字一個英文字母");
                    OK[1] = false;
                }else{
                    OK[1] = true;
                }
                if(OK()){
                    loginUI.setEnabled(true);
                }else{
                    loginUI.setEnabled(false);
                }
            }
        });
        // login
        loginUI.setEnabled(false);
        loginUI.setOnClickListener(v -> {
            getNowHashMap = DBHelper.searchByAccount(acc_loginUI.getText().toString());
            if(!login_check()){ // login failed
                Toast.makeText(this,"無此帳號/密碼",Toast.LENGTH_LONG).show();
                editor.putString("account",null).apply();
                editor.putBoolean("AUTO_CHECK",false).apply();
                editor.commit();
            }else{ // enter main page
                Toast.makeText(this,"登入成功",Toast.LENGTH_LONG).show();
                account = (String) getNowHashMap.get("account");
                editor.putString("account",account).apply();
                editor.putBoolean("AUTO_CHECK",true).apply();
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,MyMainActivity.class);
                startActivity(intent);
            }
        });
        // get password
        get_passUI.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,GetPassActivity.class);
            startActivity(intent);
        });
        // sign up
        signupUI.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,Sign1Activity.class);
            startActivity(intent);
        });
    }
    private boolean OK(){
        for (boolean ok: OK)
            if(!ok)
                return false;
        return  true;
    }
    private boolean login_check(){
        if(getNowHashMap == null)
            return false;
        String password = (String) getNowHashMap.get("password");
        if(!password.equals(pass_loginUI.getText().toString()))
            return false;
        return true;
    }
}