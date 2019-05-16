package com.example.p.jumptime;

import android.support.v4.app.FragmentActivity;

public class NewsForRecycler {

    private String news;
    private int image;
    public FragmentActivity activity;

    public NewsForRecycler(String news, int image, FragmentActivity activity){

        this.news=news;
        this.image = image;
        this.activity = activity;
    }
    public FragmentActivity getActivity(){return activity;}

    public String getName() {
        return this.news;
    }

    public void setName(String name) {
        this.news = name;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
