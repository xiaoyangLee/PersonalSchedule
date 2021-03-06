package com.android.xiaoyang.personalschedule;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

/**
*MainActivity为主Activity界面，其功能有
*1.Listview形式加载SQLite中获取的内容，显示模板采用了Item.xml文件。
*2.点击"+"号新建日程，长按修改或者删除。
*3.点击左上角呼出导航栏。
*4.右上角查看关于我们的信息。
*/
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //显示已有的数据
        final ListView listView = (ListView) findViewById(R.id.listview);
        //实例化DBHelper类
        final DBHelper helper = new DBHelper(MainActivity.this);
        
        //将ListView与SQLite绑定，设置Adapter适配器
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, helper.queryAll(), R.layout.item,
                new String[]{"id", "date", "schedule"},
                new int[]{R.id.schedule_id, R.id.schedule_date, R.id.schedule_text});
        listView.setAdapter(adapter);
        
        //设置长按事件
        listView.setOnCreateContextMenuListener(listviewLongPress);
        // 设置点击进入详情页
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击Item事件，会到详细信息显示的Activity
                Intent intent = new Intent(MainActivity.this, TextActivity.class);
                //将日期和日程详细信息取出，转为List<String>泛型格式。
                List<String> listdata;
                List<String> listtext;
                listdata = helper.queryDate();
                listtext = helper.querySchedule();
                //listdata与listtext为所有的日期、日程信息组成的列表，这里按点击的位置取出被点击Item的那条即可。
                String date = listdata.get(position) + "";
                String schedule = listtext.get(position) + "";
                //将数据传入intent中带入下个Activity
                intent.putExtra("date", date);
                intent.putExtra("schedule", schedule);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击"+"号按钮执行新建操作
                addNewSchedule();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //左上角显示导航列表
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    
    //点击返回键方法。
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //右上角选项设置，这里只有一个关于我们。
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
        //加载关于我们的Activity
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
            //Toast.makeText(MainActivity.this, "你点击了设置", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //导航栏菜单点击与执行相关操作。
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            takePhoto();

        } else if (id == R.id.nav_gallery) {
            //打开相册
            openGallery();
        } else if (id == R.id.nav_slideshow) {
            openSlideshow();
        } else if (id == R.id.nav_manage) {
            openSetUp();

        } else if (id == R.id.nav_share) {
            //执行app的分享操作
            shareApp();

        } else if (id == R.id.nav_send) {
            //联系我们
            sendURL();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 打开影像功能
     */
    protected void openSlideshow() {
        Toast.makeText(this, "对不起，此功能暂时未开发!", Toast.LENGTH_SHORT).show();
    }

    /**
     * 调用系统设置
     */
    protected void openSetUp() {
        Toast.makeText(this, "对不起，此功能暂时未开发!", Toast.LENGTH_SHORT).show();
        /*
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.ApplicationSettings");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        startActivityForResult(intent, 0);*/

    }

    /**
     * 调用系统相册。
     */
    protected void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 11);
    }

    /**
     * 相机功能的开发
     */
    protected void takePhoto() {
        Toast.makeText(this, "相机功能还在开发过程中，见谅。", Toast.LENGTH_SHORT).show();
        return;

    }

    /**
     * 调用浏览器打开默认页面
     */
    protected void sendURL() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://www.lixiaoyang.tech");
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 分享功能实现
     */
    protected void shareApp() {
        //分享我的apk连接
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "我发现了一个很好的日程app，快来使用吧！https://github.com/xiaoyangLee/PersonalSchedule");
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 添加新的日程安排
     */
    protected void addNewSchedule() {
        finish();
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("class", "add");
        startActivity(intent);
    }
    //执行长按操作
    View.OnCreateContextMenuListener listviewLongPress = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(final ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //长按弹出消息提示
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("请选择操作")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("点击对话框外可取消")
                    .setPositiveButton("修改",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Toast.makeText(MainActivity.this, "进入修改界面", Toast.LENGTH_SHORT).show();
                                    //建立数据库操作对象
                                    DBHelper helper = new DBHelper(MainActivity.this);
                                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                                    ListView listView = (ListView) findViewById(R.id.listview);
                                    //很关键的一步，求出所选中Item的id值。
                                    Map<String, Object> list = (Map<String, Object>) listView.getItemAtPosition(info.position);
                                    int id = (int) list.get("id");

                                    List<String> listdata;
                                    List<String> listtext;
                                    listdata = helper.queryDate();
                                    listtext = helper.querySchedule();
                                    String date = listdata.get(info.position) + "";
                                    String schedule = listtext.get(info.position) + "";

                                    intent.putExtra("class", "update");
                                    intent.putExtra("id", id);
                                    intent.putExtra("date", date);
                                    intent.putExtra("schedule", schedule);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //新建数据库操作对象
                            DBHelper helper = new DBHelper(MainActivity.this);
                            //获取ListView列表
                            ListView listView = (ListView) findViewById(R.id.listview);
                            //很关键的一步，求出所选中Item的id值。
                            Map<String, Object> list = (Map<String, Object>) listView.getItemAtPosition(info.position);
                            int id = (int) list.get("id");
                            //删除选中的数据
                            helper.deleteDate(id);
                            Toast.makeText(MainActivity.this, "id=" + list.get("id") + "的数据已被删除", Toast.LENGTH_SHORT).show();
                            //刷新Listview显示，用了重启Activity的方法。这里比较低效，不建议使用，是一个快速见效的办法。
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();

        }
    };
}
