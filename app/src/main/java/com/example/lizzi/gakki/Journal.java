package com.example.lizzi.gakki;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by LiZzi on 2018/6/27.
 */

public class Journal extends DataSupport implements Serializable {
    private int id;
    private String title;
    private String content;
    private String date;
   // private String jname;
    private int is_collect;   //0代表为收藏 1为收藏
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }*/
}
