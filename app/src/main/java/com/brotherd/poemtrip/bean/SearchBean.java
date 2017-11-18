package com.brotherd.poemtrip.bean;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchBean {

    //诗的id
    private long poemId;
    //诗人id
    private long poetId;
    //诗的标题
    private String title;

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
}
