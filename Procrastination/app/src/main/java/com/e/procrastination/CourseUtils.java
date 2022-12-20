package com.e.procrastination;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import java.util.HashMap;


public class CourseUtils extends Activity {

    private static HashMap<String, Object> getNowHashMap = new HashMap<>();
    private static ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CourseActivity.main.getContext(), R.array.weekSpinnerValue, android.R.layout.simple_spinner_item);

    protected static void Init(){
        // set adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // create and initialize the text
        TextView text = new TextView(CourseActivity.main.getContext());
        CourseActivity.setID(text);
        CourseActivity.idxList.add(CourseActivity.tmpIdx);
        text.setText("尚無紀錄");
        text.setTextSize(24);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // Add text to main
        CourseActivity.main.addView(text);
        // set layout of text
        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(text.getId(),ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(text.getId(),ConstraintSet.WRAP_CONTENT);
        set.connect(text.getId(),ConstraintSet.TOP,CourseActivity.main.getId(),ConstraintSet.TOP);
        set.connect(text.getId(),ConstraintSet.BOTTOM,CourseActivity.main.getId(),ConstraintSet.BOTTOM);
        set.connect(text.getId(),ConstraintSet.START,CourseActivity.main.getId(),ConstraintSet.START);
        set.connect(text.getId(),ConstraintSet.END,CourseActivity.main.getId(),ConstraintSet.END);
        // apply layout to main
        set.applyTo(CourseActivity.main);

        CourseActivity.isEmpty = CheckEmpty();
        if(!CourseActivity.isEmpty){
            NotEmpty(text);
            InitExistCard();
        }
    }
    private static boolean CheckEmpty(){
        getNowHashMap = CourseActivity.DBHelper.searchByAccount(MyMainActivity.account);
        for (String record: (String[]) getNowHashMap.get("course")) {
            if(record != null)
                return false;
        }
        return true;
    }
    protected static void NotEmpty(View view){
        assert view instanceof TextView;
        TextView text = (TextView)view;
        CourseActivity.main.removeView(text);
        CourseActivity.isEmpty = !CourseActivity.isEmpty;
    }
    private static void InitExistCard(){
        getNowHashMap = CourseActivity.DBHelper.searchByAccount(MyMainActivity.account);
        String[] record = (String[]) getNowHashMap.get("course");
        for (int record_idx = 0;record_idx < 18 ;record_idx++) {
            if(record[record_idx] == null)
                continue;
            TextView weeks = new TextView(CourseActivity.main.getContext());
            String meg = (String) adapter.getItem(record_idx);
            weeks.setText(meg);
            CourseActivity.setID(weeks);

            TextView content = new TextView(CourseActivity.main.getContext());
            content.setText(record[record_idx]);
            CourseActivity.setID(content);

            ShowCard(weeks, content);
        }

    }
    /*
     * set every necessary elements in card
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected static CardView CardRender(View txt1, View txt2){
        // create and initialize the necessary elements
        CardView card = new CardView(CourseActivity.main.getContext());
        CourseActivity.setID(card);
        CourseActivity.idxList.add(CourseActivity.tmpIdx);
        ConstraintLayout cl = new ConstraintLayout(CourseActivity.main.getContext());
        CourseActivity.setID(cl);
        assert txt1 instanceof TextView;
        TextView weeks = (TextView)txt1;
        assert txt2 instanceof TextView;
        TextView content = (TextView)txt2;

        // set card attributes
        card.setCardBackgroundColor(Color.YELLOW);
        card.setElevation(5);
        card.setRadius(50F);
        card.setMinimumWidth(700);
        card.setMinimumHeight(345);
        card.setLongClickable(true);
        card.setOnLongClickListener(v -> { //modify the data
            CourseActivity.isModify = true;
            // create record page
            AlertDialog.Builder enterDialog = new AlertDialog.Builder(CourseActivity.main.getContext());
            CourseUtils.ShowDialog(enterDialog,card);
            return false;
        });
        // set layout of card
        ViewGroup.LayoutParams Params = new ViewGroup.LayoutParams(
                700,
                345
        );
        card.addView(cl,Params);
        // set weeks attributes
        weeks.setTextSize(16);
        weeks.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cl.addView(weeks);
        // set layout of weeks
        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(weeks.getId(),ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(weeks.getId(),ConstraintSet.WRAP_CONTENT);
        set.connect(weeks.getId(),ConstraintSet.TOP,cl.getId(),ConstraintSet.TOP);
        set.connect(weeks.getId(),ConstraintSet.START,cl.getId(),ConstraintSet.START);
        set.setMargin(weeks.getId(),ConstraintSet.TOP,20);
        set.setMargin(weeks.getId(),ConstraintSet.START,20);
        set.applyTo(cl);
        // set content attributes
        content.setTextSize(14);
        content.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        content.setMinLines(1);
        content.setMaxLines(5);
        content.setEllipsize(TextUtils.TruncateAt.END);
        cl.addView(content);
        // set layout of content
        set = new ConstraintSet();
        set.constrainHeight(content.getId(),ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(content.getId(),ConstraintSet.WRAP_CONTENT);
        set.connect(content.getId(),ConstraintSet.TOP,weeks.getId(),ConstraintSet.BOTTOM);
        set.connect(content.getId(),ConstraintSet.START,cl.getId(),ConstraintSet.START);
        set.connect(content.getId(),ConstraintSet.END,cl.getId(),ConstraintSet.END);
        set.applyTo(cl);

        return  card;
    }
    protected  static void AddNewCard(View view,View prev){
        // safe way to cast
        assert view instanceof CardView;
        CardView card = (CardView)view;
        // set layout of card
        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        if (prev == null){
            newParams.topToTop = CourseActivity.scroll.getId();
        }else{
            newParams.topToBottom = prev.getId();
        }
        newParams.topMargin = 10;
        newParams.startToStart = CourseActivity.scroll.getId();
        newParams.endToEnd = CourseActivity.scroll.getId();
        CourseActivity.scroll.addView(card,newParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected static void ShowCard(TextView weeks, TextView content){
        CardView newCard = CardRender(weeks, content);
        if (CourseActivity.idxList.size() == 1) { // using while first card creating
            AddNewCard( newCard, null);
        } else { // new card's top will attach to previous card's bottom
            AddNewCard( newCard, CourseActivity.scroll.getViewById(CourseActivity.idxList.get(CourseActivity.idxList.size() - 2)));
        }
    }

    /*
     * @modifyCard: be target card in modification mode,or null in adding new card.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void ShowDialog(AlertDialog.Builder enterDialog, CardView modifyCard) {
        // create and initialize the necessary elements
        ConstraintLayout el = new ConstraintLayout(CourseActivity.main.getContext());
        CourseActivity.setID(el);
        Spinner weekUI = new Spinner(CourseActivity.main.getContext());
        CourseActivity.setID(weekUI);
        EditText ec = new EditText(CourseActivity.main.getContext());
        CourseActivity.setID(ec);
        Button savebtn = new Button(CourseActivity.main.getContext());
        CourseActivity.setID(savebtn);
        Button retbtn = new Button(CourseActivity.main.getContext());
        CourseActivity.setID(retbtn);
        // record ec is empty or not
        boolean[] TextEmpty = {true};
        // set layout of el
        ConstraintLayout.LayoutParams Params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        el.setBackgroundColor(Color.YELLOW);
        el.setLayoutParams(Params);
        // set week attributes
        weekUI.setAdapter(adapter);

        if(CourseActivity.isModify){
            String selected = ((TextView) ((ConstraintLayout)modifyCard.getChildAt(0)).getChildAt(0)).getText().toString();
            weekUI.setSelection(adapter.getPosition(selected));
            weekUI.setEnabled(false);
        }

        el.addView(weekUI);
        // set layout of weekUI
        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(weekUI.getId(),ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(weekUI.getId(),ConstraintSet.WRAP_CONTENT);
        set.connect(weekUI.getId(),ConstraintSet.TOP,el.getId(),ConstraintSet.TOP);
        set.connect(weekUI.getId(),ConstraintSet.START,el.getId(),ConstraintSet.START);
        set.setMargin(weekUI.getId(),ConstraintSet.TOP,50);
        set.applyTo(el);
        // set subtitle attributes
        TextView subtitle = new TextView(CourseActivity.scroll.getContext());
        CourseActivity.setID(subtitle);
        subtitle.setText("課程紀錄");
        subtitle.setTextColor(Color.BLACK);
        subtitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        el.addView(subtitle);
        // set layout of subtitle
        set = new ConstraintSet();
        set.constrainHeight(subtitle.getId(),ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(subtitle.getId(),ConstraintSet.WRAP_CONTENT);
        set.connect(subtitle.getId(),ConstraintSet.TOP,weekUI.getId(),ConstraintSet.BOTTOM);
        set.connect(subtitle.getId(),ConstraintSet.START,el.getId(),ConstraintSet.START);
        set.setMargin(subtitle.getId(),ConstraintSet.TOP,35);
        set.setMargin(subtitle.getId(),ConstraintSet.START,10);
        set.applyTo(el);
        // set ec attributes
        ec.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        ec.setHint("請輸入您本周的課程紀錄");
        ec.setGravity(Gravity.START|Gravity.TOP);
        ec.setLines(10);
        ec.setMinLines(1);
        ec.setMaxLines(10);
        ec.setSingleLine(false);
        ec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // before
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    savebtn.setEnabled(false);
                    savebtn.getForeground().setAlpha(100);
                    return;
                }
                if(!TextEmpty[0]){
                    savebtn.setEnabled(true);
                    savebtn.getForeground().setAlpha(255);
                    return;
                }
                TextEmpty[0] = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
                // after
            }
        });
        el.addView(ec);
        // set layout of ec
        set = new ConstraintSet();
        set.constrainMaxHeight(ec.getId(),800);
        set.constrainWidth(ec.getId(),ConstraintSet.WRAP_CONTENT);
        set.connect(ec.getId(),ConstraintSet.TOP,subtitle.getId(),ConstraintSet.BOTTOM);
        set.connect(ec.getId(),ConstraintSet.START,el.getId(),ConstraintSet.START);
        set.connect(ec.getId(),ConstraintSet.END,el.getId(),ConstraintSet.END);
        set.applyTo(el);
        // set savebtn attributes
        savebtn.setForeground(ContextCompat.getDrawable(CourseActivity.main.getContext(),R.drawable.ic_check2));
        savebtn.setBackgroundColor(Color.TRANSPARENT);
        savebtn.getForeground().setAlpha(100);
        if (ec.getText().toString().trim().length() != 0){
            TextEmpty[0] = false;
            savebtn.setEnabled(true);
            savebtn.getForeground().setAlpha(255);
        }else{
            savebtn.setEnabled(false);
            savebtn.getForeground().setAlpha(100);
        }
        savebtn.setClickable(true);
        el.addView(savebtn);
        // set layout of savebtn
        set = new ConstraintSet();
        set.constrainHeight(savebtn.getId(),100);
        set.constrainWidth(savebtn.getId(),100);
        set.connect(savebtn.getId(),ConstraintSet.BOTTOM,el.getId(),ConstraintSet.BOTTOM);
        set.connect(savebtn.getId(),ConstraintSet.START,el.getId(),ConstraintSet.START);
        set.connect(savebtn.getId(),ConstraintSet.END,el.getId(),ConstraintSet.END);
        set.setHorizontalBias(savebtn.getId(),0.25f);
        set.applyTo(el);
        // set retbtn attributes
        retbtn.setForeground(ContextCompat.getDrawable(CourseActivity.main.getContext(),R.drawable.course_ic_return));
        retbtn.setBackgroundColor(Color.TRANSPARENT);
        retbtn.getForeground().setAlpha(180);
        savebtn.setClickable(true);
        el.addView(retbtn);
        // set layout of retbtn
        set = new ConstraintSet();
        set.constrainHeight(retbtn.getId(),90);
        set.constrainWidth(retbtn.getId(),90);
        set.connect(retbtn.getId(),ConstraintSet.BOTTOM,el.getId(),ConstraintSet.BOTTOM);
        set.connect(retbtn.getId(),ConstraintSet.START,el.getId(),ConstraintSet.START);
        set.connect(retbtn.getId(),ConstraintSet.END,el.getId(),ConstraintSet.END);
        set.setHorizontalBias(retbtn.getId(),0.75f);
        set.applyTo(el);
        // create dialog
        AlertDialog enter = enterDialog.create();
        enter.show();
        enter.getWindow().setLayout(1000, 1200);
        enter.setCanceledOnTouchOutside(false);
        enter.setContentView(el);
        // set weekSpinner event
        weekUI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getNowHashMap = CourseActivity.DBHelper.searchByAccount(MyMainActivity.account);//取得被選中的項目資料

                String record = ((String[]) getNowHashMap.get("course"))[weekUI.getSelectedItemPosition()];
                if (record == null){
                    ec.setText("");
                    CourseActivity.isModify = false;
                }else{
                    ec.setText(record);
                    CourseActivity.isModify = true;
                }
                if (ec.getText().toString().trim().length() != 0){
                    TextEmpty[0] = false;
                    savebtn.setEnabled(true);
                    savebtn.getForeground().setAlpha(255);
                }else{
                    savebtn.setEnabled(false);
                    savebtn.getForeground().setAlpha(100);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // set buttons' click event
        // savebtn
        getNowHashMap = CourseActivity.DBHelper.searchByAccount(MyMainActivity.account);//取得被選中的項目資料
        String id = (String) getNowHashMap.get("id");

        /*if(!CourseActivity.isModify) { // Add new card
            savebtn.setOnClickListener(v-> {
                TextView weeks = new TextView(CourseActivity.main.getContext());
                String meg = weekUI.getSelectedItem().toString();
                weeks.setText(meg);
                CourseActivity.setID(weeks);
                TextView content = new TextView(CourseActivity.main.getContext());
                content.setText(ec.getText());
                CourseActivity.setID(content);

                int week_idx = weekUI.getSelectedItemPosition();
                CourseActivity.Record[week_idx] = String.valueOf(ec.getText());
                CourseActivity.DBHelper.modify(id,MyMainActivity.account,CourseActivity.Record);

                enter.dismiss();
                ShowCard(weeks, content);
            });
        }else{ // Modify exist card
            savebtn.setOnClickListener(v-> {
                // bad code! need change while changing CardRender
                ConstraintLayout tmp;
                if (modifyCard != null){
                    tmp = (ConstraintLayout) modifyCard.getChildAt(0);
                }else{
                    tmp = (ConstraintLayout) findCardByWeek(weekUI.getSelectedItem().toString()).getChildAt(0);
                }
                TextView weeks = (TextView) tmp.getChildAt(0);
                TextView content = (TextView) tmp.getChildAt(1);
                String meg = weekUI.getSelectedItem().toString();
                weeks.setText(meg);
                content.setText(ec.getText());

                int week_idx = weekUI.getSelectedItemPosition();
                CourseActivity.Record[week_idx] = String.valueOf(ec.getText());
                CourseActivity.DBHelper.modify(id,MyMainActivity.account,CourseActivity.Record);

                enter.dismiss();
            });
        }*/
        savebtn.setOnClickListener(v-> {
            if(!CourseActivity.isModify) { // Add new card
                TextView weeks = new TextView(CourseActivity.main.getContext());
                String meg = weekUI.getSelectedItem().toString();
                weeks.setText(meg);
                CourseActivity.setID(weeks);
                TextView content = new TextView(CourseActivity.main.getContext());
                content.setText(ec.getText());
                CourseActivity.setID(content);

                ShowCard(weeks, content);
            }else{ // Modify exist card
                // bad code! need change while changing CardRender
                ConstraintLayout tmp;
                if (modifyCard != null){
                    tmp = (ConstraintLayout) modifyCard.getChildAt(0);
                }else{
                    tmp = (ConstraintLayout) findCardByWeek(weekUI.getSelectedItem().toString()).getChildAt(0);
                }
                TextView weeks = (TextView) tmp.getChildAt(0);
                TextView content = (TextView) tmp.getChildAt(1);
                String meg = weekUI.getSelectedItem().toString();
                weeks.setText(meg);
                content.setText(ec.getText());
            }
            int week_idx = weekUI.getSelectedItemPosition();
            CourseActivity.Record[week_idx] = String.valueOf(ec.getText());
            CourseActivity.DBHelper.modify(id,MyMainActivity.account,CourseActivity.Record);
            enter.dismiss();
        });
        // retbtn
        retbtn.setOnClickListener(v-> {
            enter.dismiss();
            if(CourseActivity.idxList.size() == 0) {
                CourseActivity.isEmpty = true;
                CourseUtils.Init();
            }
        });
    }
    private static CardView findCardByWeek(String week){
        int range = CourseActivity.scroll.getChildCount();
        CardView ret;
        String target;
        for(int idx = 0;idx < range; idx++){
            ret = (CardView)CourseActivity.scroll.getChildAt(0);
            target = ((TextView) ((ConstraintLayout)ret.getChildAt(0)).getChildAt(0)).getText().toString();
            if(target.equals(week))
                return ret;
        }
        return null;
    }
}
