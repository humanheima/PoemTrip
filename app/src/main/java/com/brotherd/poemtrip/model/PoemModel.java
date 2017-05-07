package com.brotherd.poemtrip.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dumingwei on 2017/5/1.
 */
public class PoemModel implements Parcelable{

    //诗的id
    private long poemId;
    //诗人id
    private long poetId;
    private String imageUrl;
    private String title;
    private String content;
    //朝代
    private String age;
    //诗的主题思想
    private String theme;
    //诗人的名字
    private String poet;
    //翻译
    private String translation;
    private String description;

    protected PoemModel(Parcel in) {
        poemId = in.readLong();
        poetId = in.readLong();
        imageUrl = in.readString();
        title = in.readString();
        content = in.readString();
        age = in.readString();
        theme = in.readString();
        poet = in.readString();
        translation = in.readString();
    }

    public static final Creator<PoemModel> CREATOR = new Creator<PoemModel>() {
        @Override
        public PoemModel createFromParcel(Parcel in) {
            return new PoemModel(in);
        }

        @Override
        public PoemModel[] newArray(int size) {
            return new PoemModel[size];
        }
    };

    public long getPoemId() {
        return poemId;
    }

    public void setPoemId(long poemId) {
        this.poemId = poemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public long getPoetId() {
        return poetId;
    }

    public void setPoetId(long poetId) {
        this.poetId = poetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(poemId);
        dest.writeLong(poetId);
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(age);
        dest.writeString(theme);
        dest.writeString(poet);
        dest.writeString(translation);
    }
}
