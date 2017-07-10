package com.android.xiaoyang.personalschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Intent intent=getIntent();
        String date = intent.getStringExtra("date");
        String schedule = intent.getStringExtra("schedule");

        TextView datetextview = (TextView)findViewById(R.id.time);
        TextView scheduletextview = (TextView)findViewById(R.id.details);
        datetextview.setText(date);
        scheduletextview.setText(schedule);
    }
}
