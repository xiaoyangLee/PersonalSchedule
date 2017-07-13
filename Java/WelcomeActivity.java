package com.android.xiaoyang.personalschedule;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
*WelcomeActivity用来做一个欢迎界面，2秒的延迟之后，会跳转到MainActivity
*通过实现Runnable接口，和实例化Handler来实现
*/
public class WelcomeActivity extends AppCompatActivity implements Runnable{
    //实例化Handler类
    final Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //使用Handler对象发送一个延迟2秒的操作
        mHandler.postDelayed(this,2000);
    }
    @Override
    public void run() {
        //重新run()方法
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //此处不需要调用finish()了，因为已经设置了noHistory属性，系统会接管操作。
    }
}
