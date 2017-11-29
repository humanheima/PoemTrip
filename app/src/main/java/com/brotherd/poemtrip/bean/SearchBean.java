package com.brotherd.poemtrip.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchBean extends DataSupport {

    //数据库中的id
    private int id;
    //诗的id
    private long poemId = -1;
    //诗人id
    private long poetId = -1;
    //诗的标题
    private String title;
    private String poet;

    private long timeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPoemId() {
        return poemId;
    }

    public void setPoemId(long poemId) {
        this.poemId = poemId;
    }

    public long getPoetId() {
        return poetId;
    }

    public void setPoetId(long poetId) {
        this.poetId = poetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoet() {
        return poet;
    }

    public void setPoet(String poet) {
        this.poet = poet;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
