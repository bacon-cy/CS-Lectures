package com.e.procrastination;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MindfulnessResultActivity extends AppCompatActivity{
    //initialize db
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "MindfulnessDatabase";
    protected static MindfulnessSqlDBHelper DBHelper;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mindfulness_result_page);
        //initialize
        final EditText posture = findViewById(R.id.mindfulness_result_posture);
        final EditText memo = findViewById(R.id.mindfulness_result_memo);
        final Button sure = findViewById(R.id.mindfulness_result_sure);
        final TextView date = findViewById(R.id.mindfulness_result_date);
        final TextView time = findViewById(R.id.mindfulness_result_time);
        DBHelper = new MindfulnessSqlDBHelper(this, TABLE_NAME);
        //DBHelper.checkTable();
        //get value from intent
        Intent intent = getIntent();
        String startTime = intent.getStringExtra("calendar");
        date.setText(startTime);
        time.setText(intent.getStringExtra("usedTime"));
        //button listener
        sure.setOnClickListener(v -> {
            //data save into database
            String postureText = posture.getText().toString();
            String memoText = memo.getText().toString();
            DBHelper.addData(startTime,postureText,memoText);
            Intent intent1 = new Intent(MindfulnessResultActivity.this, MyMainActivity.class);
            startActivity(intent1);
            finish();
        });
    }
}
