package com.e.procrastination;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Sign1Activity extends Activity {
    EditText nameUI;
    Spinner majorUI;
    Button nextUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup1_activity);
        getAllView();
        setAllView();
    }
    private void getAllView(){
        nameUI= findViewById(R.id.nameET);
        majorUI= findViewById(R.id.majorSp);
        nextUI= findViewById(R.id.nextBtn);
    }
    private void setAllView(){
        // name
        nameUI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(nameUI.getText().toString().isEmpty()){
                    nameUI.setError("不可留白");
                    nextUI.setEnabled(false);
                }else{
                    nextUI.setEnabled(true);
                }
            }
        });
        // major

        // next
        nextUI.setEnabled(false);
        nextUI.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(Sign1Activity.this,Sign2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",nameUI.getText().toString());
            bundle.putString("major",majorUI.getSelectedItem().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}
