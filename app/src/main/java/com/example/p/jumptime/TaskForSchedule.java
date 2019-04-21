package com.example.p.jumptime;

import android.support.v4.app.FragmentActivity;

public class TaskForSchedule {


    private String taskName;
    private String taskLong;
    private String taskTime;
    private int image;
    public FragmentActivity activity;

    public TaskForSchedule(String taskName, String taskLong, String taskTime, int image, FragmentActivity activity) {

        this.taskName = taskName;
        this.taskLong = taskLong;
        this.taskTime = taskTime;
        this.image = image;
        this.activity = activity;

    }



    public String gettaskLong() {
        return this.taskLong;
    }

    public String getTaskTime() {
        return this.taskTime;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setName(String name) {
        this.taskName = name;
    }

    public int getImage() {
        return this.image;
    }

    public void settaskLong(String taskLong) {
        this.taskLong = taskLong;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setImage(int image) {
        this.image = image;
    }

}

