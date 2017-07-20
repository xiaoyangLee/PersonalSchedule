package com.android.xiaoyang.personalschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //采用webview加载本地写好的html文件，放在assets目录下。
        WebView webView =(WebView)findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/index.html");
    }
}
