package com.e.procrastination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IPSScrollingActivity extends AppCompatActivity {
    final private int[] count= new int[10];
    RadioGroup g1,g2,g3,g4,g5,g6,g7,g8,g9;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ips_main_activity);
        //initialize
        g1 = findViewById(R.id.ips_radio_group1);
        g2 = findViewById(R.id.ips_radio_group2);
        g3 = findViewById(R.id.ips_radio_group3);
        g4 = findViewById(R.id.ips_radio_group4);
        g5 = findViewById(R.id.ips_radio_group5);
        g6 = findViewById(R.id.ips_radio_group6);
        g7 = findViewById(R.id.ips_radio_group7);
        g8 = findViewById(R.id.ips_radio_group8);
        g9 = findViewById(R.id.ips_radio_group9);
        // get Button
        Button ok = this.findViewById(R.id.check_button);
        ok.setOnClickListener(this::onClick);
        //正向題
        g1.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb1_1:
                    count[1]=1;
                    break;
                case (int)R.id.ips_rb1_2:
                    count[1]=2;
                    break;
                case (int)R.id.ips_rb1_3:
                    count[1]=3;
                    break;
                case (int)R.id.ips_rb1_4:
                    count[1]=4;
                    break;
                case (int)R.id.ips_rb1_5:
                    count[1]=5;
                    break;
                default:
                    break;
            }
            // 測試用
            // Toast.makeText(ScrollingActivity.this, count[1]+"", Toast.LENGTH_SHORT).show();
        });
        g3.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb3_1:
                    count[3]=1;
                    break;
                case (int)R.id.ips_rb3_2:
                    count[3]=2;
                    break;
                case (int)R.id.ips_rb3_3:
                    count[3]=3;
                    break;
                case (int)R.id.ips_rb3_4:
                    count[3]=4;
                    break;
                case (int)R.id.ips_rb3_5:
                    count[3]=5;
                    break;
                default:
                    break;
            }
        });
        g4.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb4_1:
                    count[4]=1;
                    break;
                case (int)R.id.ips_rb4_2:
                    count[4]=2;
                    break;
                case (int)R.id.ips_rb4_3:
                    count[4]=3;
                    break;
                case (int)R.id.ips_rb4_4:
                    count[4]=4;
                    break;
                case (int)R.id.ips_rb4_5:
                    count[4]=5;
                    break;
                default:
                    break;
            }
        });
        g6.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb6_1:
                    count[6]=1;
                    break;
                case (int)R.id.ips_rb6_2:
                    count[6]=2;
                    break;
                case (int)R.id.ips_rb6_3:
                    count[6]=3;
                    break;
                case (int)R.id.ips_rb6_4:
                    count[6]=4;
                    break;
                case (int)R.id.ips_rb6_5:
                    count[6]=5;
                    break;
                default:
                    break;
            }
        });
        g7.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb7_1:
                    count[7]=1;
                    break;
                case (int)R.id.ips_rb7_2:
                    count[7]=2;
                    break;
                case (int)R.id.ips_rb7_3:
                    count[7]=3;
                    break;
                case (int)R.id.ips_rb7_4:
                    count[7]=4;
                    break;
                case (int)R.id.ips_rb7_5:
                    count[7]=5;
                    break;
                default:
                    break;
            }
        });
        g9.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb9_1:
                    count[9]=1;
                    break;
                case (int)R.id.ips_rb9_2:
                    count[9]=2;
                    break;
                case (int)R.id.ips_rb9_3:
                    count[9]=3;
                    break;
                case (int)R.id.ips_rb9_4:
                    count[9]=4;
                    break;
                case (int)R.id.ips_rb9_5:
                    count[9]=5;
                    break;
                default:
                    break;
            }
        });
        //反向題
        g2.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb2_1:
                    count[2]=5;
                    break;
                case (int)R.id.ips_rb2_2:
                    count[2]=4;
                    break;
                case (int)R.id.ips_rb2_3:
                    count[2]=3;
                    break;
                case (int)R.id.ips_rb2_4:
                    count[2]=2;
                    break;
                case (int)R.id.ips_rb2_5:
                    count[2]=1;
                    break;
                default:
                    break;
            }
        });
        g5.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb5_1:
                    count[5]=5;
                    break;
                case (int)R.id.ips_rb5_2:
                    count[5]=4;
                    break;
                case (int)R.id.ips_rb5_3:
                    count[5]=3;
                    break;
                case (int)R.id.ips_rb5_4:
                    count[5]=2;
                    break;
                case (int)R.id.ips_rb5_5:
                    count[5]=1;
                    break;
                default:
                    break;
            }
        });
        g8.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case (int)R.id.ips_rb8_1:
                    count[8]=5;
                    break;
                case (int)R.id.ips_rb8_2:
                    count[8]=4;
                    break;
                case (int)R.id.ips_rb8_3:
                    count[8]=3;
                    break;
                case (int)R.id.ips_rb8_4:
                    count[8]=2;
                    break;
                case (int)R.id.ips_rb8_5:
                    count[8]=1;
                    break;
                default:
                    break;
            }
        });
    }


    private void onClick(View v) {
        //filled incorrectly
        if(g1.getCheckedRadioButtonId() == -1
        || g2.getCheckedRadioButtonId() == -1
        || g3.getCheckedRadioButtonId() == -1
        || g4.getCheckedRadioButtonId() == -1
        || g5.getCheckedRadioButtonId() == -1
        || g6.getCheckedRadioButtonId() == -1
        || g7.getCheckedRadioButtonId() == -1
        || g8.getCheckedRadioButtonId() == -1
        || g9.getCheckedRadioButtonId() == -1){
            Toast.makeText(IPSScrollingActivity.this,"請填妥再繳交喔!",Toast.LENGTH_SHORT).show();
        }else {
            //change to result page
            setContentView(R.layout.ips_result_page);
            //change the score textview
            int score = 0;
            for (int i = 1; i < 10; i++) {
                score += this.count[i];
            }
            TextView scoreTv = (TextView) findViewById(R.id.score_tv);
            String s = Integer.toString(score);
            if (scoreTv != null) scoreTv.setText(s);
            else Toast.makeText(IPSScrollingActivity.this, "...?", Toast.LENGTH_SHORT).show();
            //change the explanation textview
            TextView explanation_title = findViewById(R.id.explanation_title);
            TextView explanation = findViewById(R.id.explanation);
            if (score <= 19) {
                explanation_title.setText(R.string.ips_explanation_title19);
                explanation.setText(R.string.ips_explanation19);
            } else if (score <= 23) {
                explanation_title.setText(R.string.ips_explanation_title23);
                explanation.setText(R.string.ips_explanation23);
            } else if (score <= 31) {
                explanation_title.setText(R.string.ips_explanation_title31);
                explanation.setText(R.string.ips_explanation31);
            } else if (score <= 36) {
                explanation_title.setText(R.string.ips_explanation_title36);
                explanation.setText(R.string.ips_explanation36);
            } else {
                explanation_title.setText(R.string.ips_explanation_title36up);
                explanation.setText(R.string.ips_explanation36up);
            }
            result_page();
        }
    }
    private void result_page(){
        //get button
        Button ok = this.findViewById(R.id.result_check_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to menu page
                Intent intent = new Intent(IPSScrollingActivity.this, MyMainActivity.class);
                startActivity(intent);
            }
        });
    }
}

