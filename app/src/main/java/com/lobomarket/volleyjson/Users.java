package com.lobomarket.volleyjson;

public class Users {
    private int id;
    private String fname;
    private String lname;
    private int age;
    private String userImg;

    public Users() {
    }

    public Users(int id, String fname, String lname, int age, String userImg) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.userImg = userImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
