package com.example.p.jumptime;

import android.support.v4.app.FragmentActivity;

public class TaskForStep {


    private String taskName;
    private String taskData;

    private int id;
    public FragmentActivity activity;

    public TaskForStep(String taskName, String taskData, int id, FragmentActivity activity) {
        this.id = id;
        this.taskName = taskName;
        this.taskData = taskData;
        this.activity = activity;

    }


    public int getID() {
        return this.id;
    }
    public String getTaskData() {
        return this.taskData;
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


    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }


}

