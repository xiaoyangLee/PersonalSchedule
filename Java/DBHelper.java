package com.android.xiaoyang.personalschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by xiaoyang on 2017/6/21.
 */

public class DBHelper {
    private static final String DATABASE_NAME = "datastorage";  //数据库名称
    private static final int DATABASE_VERSION = 1;  //数据库版本号
    private static final String TABLE_NAME = "schedule"; //保存表名称
    private static final String[] COLUMNS = {"id", "date", "schedule"};
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    private static class DBOpenHelper extends SQLiteOpenHelper {

        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + COLUMNS[0] + " integer primary key autoincrement, "
                + COLUMNS[1] + " text, " + COLUMNS[2] + " text);";

        public DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_NAME); //删除旧表格
            onCreate(db);  //创建表格
        }

    }

    /**
     * DBHelper自身的构造方法
     *
     * @param context
     */
    public DBHelper(Context context) {
        helper = new DBOpenHelper(context); //创建SQLiteOpenHelper对象
        db = helper.getWritableDatabase(); //获得可写的数据库
    }

    /**
     * 向表中插入数据
     *
     * @param data
     */
    public void insert(DataBean data) {
        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], data.getDate());
        values.put(COLUMNS[2], data.getSchedule());
        db.insert(TABLE_NAME, null, values);
    }

    /**
     * 修改表中的数据
     * @param data
     * @param id
     */
    public void update(DataBean data,int id){
        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], data.getDate());
        values.put(COLUMNS[2], data.getSchedule());
        db.update(TABLE_NAME,values,"id = "+id,null);
    }

    /**
     * 此方法查询ScheduleText，返回值类型为List<String>
     *
     * @return
     */
    public List<String> querySchedule() {
        List<String> result = new ArrayList<String>();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(2) + "");
        }
        return result;
    }

    /**
     * List<>类型的查询日期。
     *
     * @return
     */
    public List<String> queryDate() {
        List<String> listdata = new ArrayList<String>();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            listdata.add(cursor.getString(1) + "");
        }
        return listdata;
    }

    /**
     * 查询所有内容,使用ArrayList<>泛型
     */
    public ArrayList<HashMap<String, Object>> queryAll() {
        ArrayList<HashMap<String, Object>> listData;
        //sa  List<String>result=new ArrayList<String>();
        listData = new ArrayList<HashMap<String, Object>>();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            // listData.add(cursor.getInt(0)+" "+cursor.getInt(1));
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", cursor.getInt(0));
            map.put("date", cursor.getString(1));
            map.put("schedule", cursor.getString(2));
            listData.add(map);
        }
        return listData;
    }

    /**
     * 查询id信息
     */
    public ArrayList<HashMap<String, Object>> queryDateById() {
        ArrayList<HashMap<String, Object>> listData;

        listData = new ArrayList<HashMap<String, Object>>();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("date", cursor.getString(1));
            listData.add(map);
        }
        return listData;
    }

    /**
     * ArrayList<>类型的查询，结果带有键值对
     *
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryText() {
        ArrayList<HashMap<String, Object>> listdata;

        listdata = new ArrayList<HashMap<String, Object>>();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("schedule", cursor.getString(2));
            listdata.add(map);
        }
        return listdata;
    }

    /**
     * 就一句话的删除语句
     * @param id
     */
    public void deleteDate(int id){
        db.delete(TABLE_NAME,"id = "+id,null);
    }
}
