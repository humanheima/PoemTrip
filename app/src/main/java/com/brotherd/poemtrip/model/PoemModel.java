package com.brotherd.poemtrip.model;

/**
 * Created by dumingwei on 2017/5/1.
 */
public class PoemModel {

    private long id;
    private String cover;
    private String title;
    private String author;

    public PoemModel(long id, String cover, String title, String author) {
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
