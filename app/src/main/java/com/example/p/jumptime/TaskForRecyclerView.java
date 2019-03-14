package com.example.p.jumptime;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentActivity;

public class TaskForRecyclerView {


    private String taskName;
    private String taskData;
    private String taskTime;
    private int image;
    public FragmentActivity activity;

    public TaskForRecyclerView(String taskName, String taskData,String taskTime,int image, FragmentActivity activity) {

        this.taskName = taskName;
        this.taskData = taskData;
        this.taskTime = taskTime;
        this.image = image;
        this.activity = activity;

    }



    public String getTaskData() {
        return this.taskData;
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

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setImage(int image) {
        this.image = image;
    }

}

