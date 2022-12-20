package com.e.procrastination;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MindfulnessMainActivity extends AppCompatActivity {

    private int theSoundType=0; //0:no_talk 1:body_scan 2:breath
    private int hour=0,minute=10;
    private boolean start=false,end=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mindfulness_main_activity);
        //initialize button text
        changeTimeButtonText(hour,minute);
        changeSoundButtonText(start,end);
        //Buttons binding
        final Button timeLength = (Button) findViewById(R.id.mindfulness_time_button);
        final Button soundOnOff = (Button) findViewById(R.id.mindfulness_alarm_button);
        final Button nextPage = (Button) findViewById(R.id.mindfulness_check_button);
        //sound type setting button
        final RadioGroup soundType = (RadioGroup)findViewById(R.id.mindfulness_radio_group);
        final RadioButton type1 = (RadioButton)findViewById(R.id.mindfulness_no_talk);
        final RadioButton type2 = (RadioButton)findViewById(R.id.mindfulness_body_scan);
        final RadioButton type3 = (RadioButton)findViewById(R.id.mindfulness_breath);
        //initialize radio button
        soundType.check(R.id.mindfulness_no_talk);
        type1.setBackgroundColor(getResources().getColor(R.color.aquamarine_500_0_5));
        /*font setting(skipped)
         * */
        //sound type setting radio group
        soundType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case (int) R.id.mindfulness_no_talk:
                    type1.setBackgroundColor(getResources().getColor(R.color.aquamarine_500_0_5));
                    type2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    type3.setBackgroundColor(getResources().getColor(R.color.transparent));
                    theSoundType = 0;
                    break;
                case (int) R.id.mindfulness_body_scan:
                    type2.setBackgroundColor(getResources().getColor(R.color.aquamarine_500_0_5));
                    type1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    type3.setBackgroundColor(getResources().getColor(R.color.transparent));
                    theSoundType = 1;
                    break;
                case (int) R.id.mindfulness_breath:
                    type3.setBackgroundColor(getResources().getColor(R.color.aquamarine_500_0_5));
                    type1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    type2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    theSoundType = 2;
                    break;
                default:
                    break;
            }
        });
        //time length setting button
        timeLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        //sound on off setting button
        soundOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSoundOnOff();
            }
        });
        //next page button
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MindfulnessMainActivity.this,MindfulnessPlayActivity.class);
                intent.putExtra("hour",hour);
                intent.putExtra("minute",minute);
                intent.putExtra("soundType",theSoundType);
                intent.putExtra("start",start);
                intent.putExtra("end",end);
                startActivity(intent);
            }
        });
    }
    private void showTimePicker(){
        // Build alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MindfulnessMainActivity.this);
        builder.setTitle("選擇時間長度").setCancelable(false);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.mindfulness_time_setting,null);
        builder.setView(dialogView);
        AlertDialog timePicker = builder.create();
        // Views
        final Button sure = (Button)dialogView.findViewById(R.id.mindfulness_time_sure);
        final NumberPicker hours = (NumberPicker) dialogView.findViewById(R.id.mindfulness_hours);
        final NumberPicker minutes = (NumberPicker) dialogView.findViewById(R.id.mindfullness_minutes);
        // setting for button
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hour==0 && minute==0){
                    Toast.makeText(MindfulnessMainActivity.this, getString(R.string.mindfulness_time_zero),Toast.LENGTH_SHORT).show();
                }else{
                    changeTimeButtonText(hour,minute);
                    timePicker.dismiss();
                }
            }
        });
        // setting for hours
        hours.setMaxValue(5);
        hours.setMinValue(0);
        hours.setWrapSelectorWheel(false);
        hours.setValue(hour);
        hours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Save the value in the number picker
                hour = newVal;
            }
        });
        /* setting for minutes */
        minutes.setMaxValue(59);
        minutes.setMinValue(0);
        minutes.setWrapSelectorWheel(true);
        minutes.setValue(minute);
        minutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Save the value in the number picker
                minute = newVal;
            }
        });
        timePicker.show();
    }
    private void changeTimeButtonText(int _hour, int _minute){
        Button timeLength = (Button)findViewById(R.id.mindfulness_time_button);
        if(_hour!=0)
            timeLength.setText(getString(R.string.mindfulness_time_setting,_hour,_minute));
        else
            timeLength.setText(getString(R.string.mindfulness_time_setting2,_minute));
    }
    private void setSoundOnOff(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MindfulnessMainActivity.this);
        builder.setTitle("設定提示音");
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.mindfulness_sound_setting, null);
        builder.setView(dialogView);
        AlertDialog soundOnOff = builder.create();
        /* View */
        final SwitchCompat startSound = (SwitchCompat)dialogView.findViewById(R.id.mindfulness_start_sound);
        final SwitchCompat endSound = (SwitchCompat)dialogView.findViewById(R.id.mindfulness_end_sound);
        final Button sure = (Button)dialogView.findViewById(R.id.mindfulness_time_sure);
        /* Setting Switch Start Sound */
        startSound.setChecked(start);
        startSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                start= isChecked;
            }
        });
        /* Setting Switch End Sound */
        endSound.setChecked(end);
        endSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                end= isChecked;
            }
        });
        /* Setting Button */
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSoundButtonText(start,end);
                soundOnOff.dismiss();
            }
        });
        soundOnOff.show();

    }
    private void changeSoundButtonText(boolean _start, boolean _end){
        String open="開啟",close="關閉";
        Button soundOnOff = (Button)findViewById(R.id.mindfulness_alarm_button);
        if(_start && _end){
            soundOnOff.setText(getString(R.string.mindfulness_alarm_setting,open,open));
        }else if(_start && !_end){
            soundOnOff.setText(getString(R.string.mindfulness_alarm_setting,open,close));
        }else if(!_start && _end){
            soundOnOff.setText(getString(R.string.mindfulness_alarm_setting,close,open));
        }else{
            soundOnOff.setText(getString(R.string.mindfulness_alarm_setting,close,close));
        }
    }
}
