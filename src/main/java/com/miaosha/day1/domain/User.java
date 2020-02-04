package com.miaosha.day1.domain;

public class User {
    private int id;
    private String name;
    public User(){

    }
    public User(int i, String s) {
        this.id=i;
        this.name=s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
