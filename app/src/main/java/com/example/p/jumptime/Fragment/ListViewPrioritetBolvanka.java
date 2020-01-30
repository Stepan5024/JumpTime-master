package com.example.p.jumptime.Fragment;

public class ListViewPrioritetBolvanka {
    private String name;
    private String description;
    private String time;
    private int idColorBack;
    private int idColorFront;
    public ListViewPrioritetBolvanka(String name, String description, String time, int idColorBack, int idColorFront){
        this.name = name;
        this.description = description;
        this.time = time;
        this.idColorBack = idColorBack;
        this.idColorFront = idColorFront;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public int getIdColorBack() {
        return idColorBack;
    }
    public void setIdColorBack(int idColorBack) {
        this.idColorBack = idColorBack;
    }

    public int getIdColorFront() {
        return idColorFront;
    }
    public void setIdColorFront(int idColorFront) {
        this.idColorFront = idColorFront;
    }

}
