package com.e.procrastination;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MyMainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    FloatingActionButton fab;
    ImageButton moodButton,procrastinateButton,courseButton,mindfulnessButton;
    protected static String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //initialize
        account = LoginActivity.share.getString("account", null);
        drawerLayout = findViewById(R.id.drawer_layout);
        fab = findViewById(R.id.main_fab);
        moodButton = findViewById(R.id.main_mood_button);
        procrastinateButton = findViewById(R.id.main_procrastinate_button);
        courseButton = findViewById(R.id.main_course_button);
        mindfulnessButton = findViewById(R.id.main_mindfulness_button);
        //fab setOnListener
        fab.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        //four image buttons setOnListeners
        /*  1~8
            1:profile
            2:courseRecord
            3:procrastinateDiary
            4:IPS
            5:mindfulnessMain
            6:mindfulnessRecord
            7:moodRecord
            8:logout
        */
        moodButton.setOnClickListener(v -> {
            //navigateClicked(7);
            Toast.makeText(this, "功能尚未開啟", Toast.LENGTH_SHORT).show();
        });
        procrastinateButton.setOnClickListener(v -> {
            //navigateClicked(3);
            Toast.makeText(this, "功能尚未開啟", Toast.LENGTH_SHORT).show();
        });

        courseButton.setOnClickListener(v -> navigateClicked(2));
        mindfulnessButton.setOnClickListener(v -> navigateClicked(5));
        //appBar navigation setting
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
                /*  1~8
                    1:profile
                    2:courseRecord
                    3:procrastinateDiary
                    4:IPS
                    5:mindfulnessMain
                    6:mindfulnessRecord
                    7:moodRecord
                    8:logout
                */
                if(id==R.id.sidebar_profile)navigateClicked(1);
                else if(id==R.id.sidebar_course_record)navigateClicked(2);
                else if(id==R.id.sidebar_diary)navigateClicked(3);
                else if(id==R.id.sidebar_ips)navigateClicked(4);
                else if(id==R.id.sidebar_mindfulness)navigateClicked(5);
                else if(id==R.id.sidebar_mindfulness_record)navigateClicked(6);
                else if(id==R.id.sidebar_mood_record)navigateClicked(7);
                else if(id==R.id.sidebar_logout)navigateClicked(8);
                return false;
            }
        });
    }
    public void navigateClicked(int button){
        /*  1~8
            1:profile
            2:courseRecord
            3:procrastinateDiary
            4:IPS
            5:mindfulnessMain
            6:mindfulnessRecord
            7:moodRecord
            8:logout
        */
        Intent intent=new Intent(MyMainActivity.this, MyMainActivity.class);
        switch (button){
            case 1:
                intent = new Intent(MyMainActivity.this,ProfileMainActivity.class);
                break;
            case 2:
                intent = new Intent(MyMainActivity.this,CourseActivity.class);
                break;

            case 4:
                intent = new Intent(MyMainActivity.this,IPSScrollingActivity.class);
                break;
            case 5:
                intent = new Intent(MyMainActivity.this,MindfulnessMainActivity.class);
                break;
            case 3:
                //break;
            case 6:
                //break;
            case 7:
                Toast.makeText(this, "功能尚未開啟", Toast.LENGTH_SHORT).show();
                return;
                //break;
            case 8:
                intent = new Intent(MyMainActivity.this,LoginActivity.class);
                LoginActivity.editor.putBoolean("AUTO_CHECK",false).apply();
                LoginActivity.editor.apply();
                break;
            default:
                Toast.makeText(MyMainActivity.this,"Something is Wrong",Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.sidebar_course_record).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                navigateClicked(2);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu);
        return true;
    }

}
