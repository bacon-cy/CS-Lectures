package com.e.procrastination;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class CourseActivity extends Activity {
    protected static ArrayList<Integer> idxList = new ArrayList<>();
    protected static int tmpIdx = R.id.idx;
    protected static boolean isEmpty;
    protected static boolean isModify;
    protected static String[] Record = new String[18];

    private static final String DB_NAME = "DBCourse";
    private static final int DB_VERSION = 1;
    private static String TABLE_NAME = "Root";
    private static SQLiteDatabase db;
    protected static CourseSqlDBHelper DBHelper;

    protected static ConstraintLayout main;
    protected static ConstraintLayout scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_main_activity);
        DBHelper = new CourseSqlDBHelper(this, DB_NAME
                , null, DB_VERSION, TABLE_NAME);//初始化資料庫
        DBHelper.checkTable();
        if(DBHelper.searchByAccount(MyMainActivity.account) == null){
            for (int i=0;i<18;i++){
                Record[i] = null;
            }
            DBHelper.addData(MyMainActivity.account,Record);
        }else{
            Record = (String[]) DBHelper.searchByAccount(MyMainActivity.account).get("course");
        }
        // set the initial page
        // safe way to cast
        View tmp = this.findViewById(R.id.main_layout);
        assert tmp instanceof ConstraintLayout;
        main = (ConstraintLayout) tmp;
        tmp = this.findViewById(R.id.scroll_layout);
        assert tmp instanceof ConstraintLayout;
        scroll = (ConstraintLayout) tmp;
        CourseUtils.Init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void AddCard(View view) {
        // remove @empty
        if(idxList.size() == 1 && isEmpty) {
            CourseUtils.NotEmpty(main.getViewById(idxList.get(0)));
            idxList.remove(0);
        }
        // create record page
        isModify = false;
        AlertDialog.Builder enterDialog = new AlertDialog.Builder(this);
        CourseUtils.ShowDialog(enterDialog,null);
    }

    protected static void setID(Object obj){
        tmpIdx++; // ensure every ID will be unique
        if(obj instanceof ConstraintLayout){
            ((ConstraintLayout) obj).setId(tmpIdx);
        }else if (obj instanceof EditText){
            ((EditText) obj).setId(tmpIdx);
        }else if (obj instanceof Button){
            ((Button) obj).setId(tmpIdx);
        }else{
            // View is basic building block for user interface components
            ((View) obj).setId(tmpIdx);
        }
    }
}