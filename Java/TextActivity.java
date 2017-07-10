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
        //获取intent传入的数据      
        Intent intent=getIntent();
        String date = intent.getStringExtra("date");
        String schedule = intent.getStringExtra("schedule");
        //获取日志的日期和内容详细信息
        TextView datetextview = (TextView)findViewById(R.id.time);
        TextView scheduletextview = (TextView)findViewById(R.id.details);
        datetextview.setText(date);
        scheduletextview.setText(schedule);
    }
}
