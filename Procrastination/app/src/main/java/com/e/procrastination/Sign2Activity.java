package com.e.procrastination;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Sign2Activity extends Activity {
    EditText acc_signupUI,pass_signupUI,pass_againUI,secureAUI;
    Spinner secureQUI;
    Button finishUI;
    boolean[] OK = {false,false,false,false};
    private String name,major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup2_activity);

        name = (String)getIntent().getExtras().getString("name");
        major = (String)getIntent().getExtras().getString("major");

        getAllView();
        setAllView();
    }
    private void getAllView(){
        acc_signupUI = findViewById(R.id.accountSignET);
        pass_signupUI = findViewById(R.id.passSignET);
        pass_againUI = findViewById(R.id.passAgainET);
        secureQUI = findViewById(R.id.secureSp);
        secureAUI = findViewById(R.id.ansET);
        finishUI = findViewById(R.id.finishBtn);
    }
    private void setAllView(){
        // acc_signup
        acc_signupUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String account = acc_signupUI.getText().toString();
                if (account.isEmpty()){
                    acc_signupUI.setError("不可留白");
                    OK[0] = false;
                }else if(!account.matches("[a-zA-Z0-9._-]+@[a-z]?.+\\.[a-z]+")){
                    acc_signupUI.setError("請輸入E-mail");
                    OK[0] = false;
                }else {
                    OK[0] = true;
                }
                if(OK()){
                    finishUI.setEnabled(true);
                }else{
                    finishUI.setEnabled(false);
                }
            }
        });
        // pass_signup
        pass_signupUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = pass_signupUI.getText().toString();
                if (password.isEmpty()){
                    pass_signupUI.setError("不可留白");
                    OK[1] = false;
                }else if (!password.matches("^.{8,15}$")){
                    pass_signupUI.setError("密碼應介於8至15個字元");
                    OK[1] = false;
                }else if(!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}$")){
                    pass_signupUI.setError("請輸入至少一個數字一個英文字母");
                    OK[1] = false;
                }else{
                    OK[1] = true;
                }
                if(OK()){
                    finishUI.setEnabled(true);
                }else{
                    finishUI.setEnabled(false);
                }
            }
        });
        // pass_again
        pass_againUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = pass_signupUI.getText().toString();
                String again = pass_againUI.getText().toString();
                if (again.isEmpty()){
                    pass_againUI.setError("不可留白");
                    OK[2] = false;
                }else if(!password.equals(again)){
                    pass_againUI.setError("密碼不一致");
                    OK[2] = false;
                }else{
                    OK[2] = true;
                }
                if(OK()){
                    finishUI.setEnabled(true);
                }else{
                    finishUI.setEnabled(false);
                }
            }
        });
        // security question

        // security answer
        secureAUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ans = secureAUI.getText().toString();
                if(ans.isEmpty()){
                    secureAUI.setError("不可留白");
                    OK[3] = false;
                }else{
                    OK[3] = true;
                }
                if(OK()){
                    finishUI.setEnabled(true);
                }else{
                    finishUI.setEnabled(false);
                }
            }
        });
        // finish
        finishUI.setEnabled(false);
        finishUI.setOnClickListener(v -> {
            String account = acc_signupUI.getText().toString();
            String password = pass_signupUI.getText().toString();
            String question = secureQUI.getSelectedItem().toString();
            String answer = secureAUI.getText().toString();
            LoginActivity.DBHelper.addData(account,password,name,null,major,question,answer);

            // connect to main page. For testing,connect to login page instead.
            Intent intent = new Intent();
            intent.setClass(Sign2Activity.this,LoginActivity.class);
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