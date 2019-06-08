package com.example.p.jumptime.Model;

import android.support.v4.app.FragmentActivity;

public class TaskForStep {


    private String taskName, taskData, k, i, l;
    private int HpMax, hp, counter;
    public FragmentActivity activity;

    public TaskForStep(String taskName, String taskData, String k, String i, String l, int HpMax, int hp, int counter, FragmentActivity activity) {
        this.HpMax = HpMax;
        this.hp = hp;
        this.taskName = taskName;
        this.taskData = taskData;
        this.activity = activity;
        this.counter = counter;
        this.k = k;
        this.l = l;
        this.i = i;
    }


    public int getHpMax() {
        return this.HpMax;
    }

    public String getK() {
        return this.k = k;
    }

    public String getI() {
        return this.i = i;
    }

    public String getL() {
        return this.l = l;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int count) {
        this.counter = count;
    }

    public String getTaskData() {
        return this.taskData;
    }

    public void setHpMax(int hp) {
        this.HpMax = hp;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

