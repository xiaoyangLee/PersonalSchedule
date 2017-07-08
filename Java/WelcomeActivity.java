package com.android.xiaoyang.personalschedule;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class WelcomeActivity extends AppCompatActivity implements Runnable{
    final Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mHandler.postDelayed(this,2000);
    }
    @Override
    public void run() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //此处不需要调用finish()了，因为已经设置了noHistory属性，系统会接管操作。
    }
}
