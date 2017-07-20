package com.android.xiaoyang.personalschedule;


import java.util.Calendar;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private EditText dateEditText;
    private EditText scheduleEditText;
    private Button alarmButton;
    private Button saveButton;
    private Button resetButton;
    private int year, month, day;
    private int flag;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dateEditText = (EditText) findViewById(R.id.dateEditText);
        scheduleEditText = (EditText) findViewById(R.id.scheduleEditText);
        alarmButton = (Button) findViewById(R.id.alarmButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        dateEditText.setOnClickListener(new MyClick());
        alarmButton.setOnClickListener(new MyClick());
        saveButton.setOnClickListener(new MyClick());
        resetButton.setOnClickListener(new MyClick());

        //获取到来自请求修改的数据
        Intent intent = getIntent();
        //获取设置的标志位
        String isAddorUpt = intent.getStringExtra("class");
        //判断是新建标签还是修改原有的
        if (isAddorUpt.equals("add")) {
            flag = 0;
          //  Toast.makeText(this, "我是从新建Activity来的", Toast.LENGTH_SHORT).show();
            return;
        } else if (isAddorUpt.equals("update")) {
           // Toast.makeText(this, "我是从修改Activity来的", Toast.LENGTH_SHORT).show();
            flag = 1;
            String date = intent.getStringExtra("date");
            String schedule = intent.getStringExtra("schedule");
            //获取到item的id值
            id = intent.getIntExtra("id", 0);

            dateEditText.setText(date);
            scheduleEditText.setText(schedule);
        } else {
            Toast.makeText(this, "应该不会有出错的这天吧？!", Toast.LENGTH_SHORT).show();
        }

    }

    class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dateEditText:

                    date();
                    datePickerDialog();
                    break;

                case R.id.alarmButton:

                    alarmClick();
                    break;

                case R.id.saveButton:

                    saveInfo();
                    break;

                case R.id.resetButton:
                    reset();
                    break;
                default:
                    break;
            }
        }
    }

    private void reset() {
        dateEditText.setText("");
        scheduleEditText.setText("");
    }

    /**
     * 将信息保存到数据库中
     */
    private void saveInfo() {
        DataBean data = new DataBean();
        DBHelper helper = new DBHelper(EditActivity.this);

        String schedulText = scheduleEditText.getText().toString();
        String dateText = dateEditText.getText().toString();


        if (schedulText.length() < 5) {
            scheduleEditText.setError("不得少于5个字！");
            return;
        }
        data.setDate(dateText);
        data.setSchedule(schedulText);
        if (flag == 1) {
            helper.update(data,id);
            Toast.makeText(EditActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
            return;
        } else if (flag == 0) {
            helper.insert(data);
            Toast.makeText(EditActivity.this, "保存成功啦！", Toast.LENGTH_SHORT).show();
            return;
        }
        //回到MainActivity
//        finish();
//        Intent intent = new Intent(EditActivity.this,MainActivity.class);
//        startActivity(intent);
    }

    //获取当前系统时间
    private void date() {
        Calendar c = Calendar.getInstance();
        //年
        year = c.get(Calendar.YEAR);
        //月
        month = c.get(Calendar.MONTH);
        //日
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * datePickerDialog()用于设置用户的出生日期
     * 以一个日历框的形式显示。
     */
    private void datePickerDialog() {
        new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String date = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                dateEditText.setText(date);
            }
        }, year, month, day).show();
    }

    /**
     * 启动闹钟
     */
    private void alarmClick() {
        Intent alarms = new Intent(AlarmClock.ACTION_SET_ALARM);
        startActivity(alarms);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
