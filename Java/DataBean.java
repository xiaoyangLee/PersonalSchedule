package com.android.xiaoyang.personalschedule;

/**
 * Created by xiaoyang on 2017/6/21.
 * 数据库的model类，定义数据的类型
 */

public class DataBean {
    private int id;
    private String date;
    private String schedule;

    public DataBean(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
