package com.example.retrofitexample;

import com.google.gson.annotations.SerializedName;

public class Post {

    private String title;

    @SerializedName("body")
    private String text;

    private int userId;

    private int id;

    public Post(int userId, String title, String text) {
        this.title = title;
        this.text = text;
        this.userId = userId;
    }


    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }
}
