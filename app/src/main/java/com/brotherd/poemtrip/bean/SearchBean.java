package com.brotherd.poemtrip.bean;

import android.support.annotation.NonNull;

import org.litepal.crud.DataSupport;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchBean extends DataSupport implements Comparable<SearchBean> {

    //诗的id
    private long poemId = -1;
    //诗人id
    private long poetId = -1;
    //诗的标题
    private String title;

    private long timeStamp;

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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public int compareTo(@NonNull SearchBean o) {
        if (this.timeStamp > o.timeStamp) {
            return 1;
        } else if (this.timeStamp == o.timeStamp) {
            return 0;
        } else {
            return -1;
        }
    }
}
