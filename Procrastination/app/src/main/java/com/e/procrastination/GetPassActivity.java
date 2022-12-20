package com.e.procrastination;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class GetPassActivity extends Activity {
    EditText mailUI,ansUI,secureUI;
    Button mailCheckUI,checkUI,returnUI;
    private HashMap<String, Object> userdata;
    boolean[] OK = {false,false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_getpass_activity);
        getAllView();
        setAllView();
    }

    private void getAllView(){
        secureUI = findViewById(R.id.secureET);
        ansUI = findViewById(R.id.ansET);
        checkUI = findViewById(R.id.checkBtn);
        returnUI = findViewById(R.id.returnBtn);
        mailUI = findViewById(R.id.mailGetPassET);
        mailCheckUI = findViewById(R.id.mailBtn);
    }

    private void setAllView(){
        // mail
        mailUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String account = mailUI.getText().toString();
                if (account.isEmpty()){
                    mailUI.setError("不可留白");
                    mailCheckUI.setEnabled(false);
                }else if(!account.matches("[a-zA-Z0-9._-]+@[a-z]?.+\\.[a-z]+")){
                    mailUI.setError("請輸入E-mail");
                    mailCheckUI.setEnabled(false);
                }else {
                    mailCheckUI.setEnabled(true);
                }
            }
        });
        // check mail
        mailCheckUI.setEnabled(false);
        mailCheckUI.setOnClickListener(v -> {
            String account = mailUI.getText().toString();
            userdata = LoginActivity.DBHelper.searchByAccount(account);
            if(userdata == null){
                Toast.makeText(this, "查無此號", Toast.LENGTH_LONG).show();
                OK[0] = false;
            }else{
                secureUI.setText((String) userdata.get("secureQ"));
                OK[0] = true;
            }
        });
        // security
        secureUI.setEnabled(false);
        // answer
        ansUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ansUI.getText().toString().isEmpty()){
                    ansUI.setError("不可留白");
                    OK[1] = false;
                }else{
                    OK[1] = true;
                }
                if(OK()){
                    checkUI.setEnabled(true);
                }else{
                    checkUI.setEnabled(false);
                }
            }
        });
        // check
        checkUI.setEnabled(false);
        checkUI.setOnClickListener(v -> {
            String inputAns = ansUI.getText().toString();
            String realAns = (String) userdata.get("secureA");
            if (inputAns.equals(realAns)){
                String password = (String) userdata.get("password");
                Toast.makeText(this, "您的密碼: "+password, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "安全問題回答錯誤", Toast.LENGTH_LONG).show();
            }
        });
        // return
        returnUI.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(GetPassActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }
    private boolean OK(){
        for (boolean ok: OK)
            if(!ok)
                return false;
        return  true;
    }
}

