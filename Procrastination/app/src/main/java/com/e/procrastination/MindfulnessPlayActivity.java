package com.e.procrastination;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MindfulnessPlayActivity extends AppCompatActivity {
    Button playButton, settingButton, quitButton, quitButton2;
    TextView timerText, timerTextPlaying, typeText, typeTextPlaying;
    MediaPlayer mediaPlayer,alarm;
    Timer timer;
    TimerTask timerTask;
    Calendar calendar;
    private double time = 0.0, _time = 0.0; //(_time : final)
    private boolean clicked=false,start,end;
    private String _typeText;
    @Override
    public void onBackPressed(){
        if(!mediaPlayer.isPlaying()){
            super.onBackPressed();
        }
    }
    private void quit(){
        mediaPlayer.stop();
        switchResult();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mindfulness_play);

        //get values from main
        int hour,minute,type;
        Intent intent = getIntent();
        start = intent.getBooleanExtra("start",false);
        end = intent.getBooleanExtra("end",false);
        hour = intent.getIntExtra("hour",0);
        minute = intent.getIntExtra("minute",10);
        type = intent.getIntExtra("soundType",0);

        //initialize
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.mindfulness_playing, null);
        playButton = (Button) findViewById(R.id.mindfulness_play);
        quitButton = (Button) findViewById(R.id.minfulness_quit_play);
        settingButton = (Button) findViewById(R.id.mindfulness_setting);
        quitButton2 = (Button) view.findViewById(R.id.mindfulness_quit_button);
        timerText = (TextView) findViewById(R.id.mindfulness_time_goes);
        typeText = (TextView) findViewById(R.id.mindfulness_doing_type);
        timer = new Timer();
        alarm = MediaPlayer.create(getApplicationContext(),R.raw.alarm);
        //setting time
        time = minute*60.0+hour*60*60.0;
        _time = time;
//        if(timerTextPlaying!=null)
//        Toast.makeText(MindfulnessPlayActivity.this,getTimerText(),Toast.LENGTH_SHORT).show();
        timerText.setText(getTimerText(time));

        //setting sound and text
        if(type==1) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.body_scan_no_alarm);
            _typeText = "身體掃描";
        }
        else if(type==2) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.breath_no_alarm);
            _typeText = "正念呼吸";
        }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rain);
            _typeText="正念冥想";
        }
        typeText.setText(_typeText);
        mediaPlayer.setLooping(true);

        //start!
        playButton.setOnClickListener(v->{
            if(!clicked){
                calendar=Calendar.getInstance();
                startTimer();
                clicked=true;
                setContentView(view);
                timerTextPlaying = (TextView)view.findViewById(R.id.mindfulness_time_goes);
                timerTextPlaying.setText(getTimerText(time));
                typeTextPlaying = (TextView)view.findViewById(R.id.mindfulness_doing_type);
                typeTextPlaying.setText(_typeText);
                if(start){
                    alarm.start();
                }else{
                    mediaPlayer.start();
                }
            }
        });

        //quit
        quitButton.setOnClickListener(v->{quitOrNot();});
        quitButton2.setOnClickListener(v->quit());
        //setting
        settingButton.setOnClickListener(v->{
            onBackPressed();
        });
    }
    private void quitOrNot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MindfulnessPlayActivity.this);
        builder.setTitle(R.string.mindfulness_play_quit);
        builder.setPositiveButton(R.string.mindfulness_play_quit_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quit();
            }
        });
        builder.setNegativeButton(R.string.mindfulness_play_quit_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(600,350);
    }
    private void switchResult(){
        if(!end)mediaPlayer.stop();
        Intent intent = new Intent(MindfulnessPlayActivity.this,MindfulnessResultActivity.class);
        intent.putExtra("usedTime",getTimerText(_time-time));
        intent.putExtra("calendar",calendarFormat(calendar));
        startActivity(intent);
    }
    private void startTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        time--;
                        timerTextPlaying.setText(getTimerText(time));
                        if(start && time==_time-3)mediaPlayer.start();
                        if(end && time==3){
                            mediaPlayer.stop();
                            alarm.start();
                        }
                        if(time<=0.0){
                            switchResult();
                            timer.cancel();
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }
    public String getTimerText(double time){
        int rounded = (int)Math.round(time);
        int seconds = ((rounded%86400)%3600)%60;
        int minutes = ((rounded%86400)%3600)/60;
        int hours = ((rounded%86400)/3600);
        // format time
        if(hours==0){
            return String.format(Locale.CHINESE,"%02d",minutes)
                    + " : " + String.format(Locale.CHINESE,"%02d",seconds);
        }else{
            return String.format(Locale.CHINESE,"%02d",hours)
                    + " : "+ String.format(Locale.CHINESE,"%02d",minutes);
        }
    }
    public String calendarFormat(Calendar _calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm",Locale.TAIWAN);
        return dateFormat.format(_calendar.getTime());
    }

}
