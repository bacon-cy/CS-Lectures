package com.e.procrastination;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

public class ProfileSettingActivity extends Activity {
    Toolbar toolbar;
    Spinner majorSp;
    EditText nameEdit;
    ImageView nameUI,majorUI;
    private String account;
    private HashMap<String, Object> data;
    private static boolean[] isModify = {false,false}; // inx 0 is for name, 1 for major
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting);
        account = (String)getIntent().getExtras().getString("account");
        data = ProfileMainActivity.DBHelper.searchByAccount(account);
        getAllView();
        setAllView();
    }
    private void getAllView(){
        toolbar = findViewById(R.id.toolbar);
        majorSp = findViewById(R.id.majorSpinner);
        nameEdit = findViewById(R.id.nameET);
        nameUI = findViewById(R.id.nameModifyIV);
        majorUI = findViewById(R.id.majorModifyIV);
    }
    private void setAllView(){
        // Toolbar
        toolbar.setNavigationOnClickListener(v -> {
            updateDB();
            Intent intent = new Intent();
            intent.setClass(ProfileSettingActivity.this, ProfileMainActivity.class);
            startActivity(intent);
        });
        // majorSpinner
        majorSp.setSelection(((ArrayAdapter<String>)majorSp.getAdapter())
                            .getPosition((String) data.get("major")));
        majorSp.setEnabled(false);
        // nameEditText
        nameEdit.setText((String)data.get("name"));
        nameEdit.setEnabled(false);
        // nameModifyIV
        nameUI.setClickable(true);
        nameUI.setOnClickListener(v -> {
            if(!isModify[0]) {
                nameUI.setImageDrawable(getResources().getDrawable(R.drawable.ic_check2));
                isModify[0] = true;
                nameEdit.setEnabled(true);
            }else{
                nameUI.setImageDrawable(getResources().getDrawable(R.drawable.profile_edit));
                isModify[0] = false;
                nameEdit.setEnabled(false);
            }
        });
        // majorModifyIV
        majorUI.setClickable(true);
        majorUI.setOnClickListener(v -> {
            if(!isModify[1]) {
                majorUI.setImageDrawable(getResources().getDrawable(R.drawable.ic_check2));
                isModify[1] = true;
                majorSp.setEnabled(true);
            }else{
                majorUI.setImageDrawable(getResources().getDrawable(R.drawable.profile_edit));
                isModify[1] = false;
                majorSp.setEnabled(false);
            }
        });
    }

    private void updateDB(){
        String id = (String)data.get("id");
        String account = (String) data.get("account");
        String password = (String) data.get("password");
        String newName = null;
        if(isModify[0]){
            newName = (String) data.get("name");
        }else{
            newName = nameEdit.getText().toString();
        }
        byte[] photo = (byte[]) data.get("photo");
        String newMajor = null;
        if(isModify[1]){
            newMajor = (String) data.get("major");
        }else{
            newMajor = majorSp.getSelectedItem().toString();
        }
        String question = (String) data.get("secureQ");
        String answer = (String) data.get("secureA");
        ProfileMainActivity.DBHelper.modify(id,account,password,newName,photo,newMajor,question,answer);
    }
}
