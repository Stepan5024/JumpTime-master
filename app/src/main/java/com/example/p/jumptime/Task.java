package com.example.p.jumptime;

public class Task {
    private String id;
    private String name;
    private String data;
    private String k;
    private String i;
    private String l;
    private String o;

    public Task(){

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getK() {
        return k;
    }

    public String getI() {
        return i;
    }

    public String getL() {
        return l;
    }

    public String getO() {
        return o;
    }

    public Task(String id, String name, String data, String k, String i, String l, String o) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.k = k;
        this.i = i;
        this.l = l;
        this.o = o;
    }
}
