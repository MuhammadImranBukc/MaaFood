package com.example.maafooD;


import com.google.firebase.database.Exclude;

public class Order {
    public String name, people, date, id;
    private String mKey;


    public Order(String id, String name, String peopl, String dat) {
       this.id=id;
        this.name = name;
        this.people = peopl;
        this.date = dat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPeople() {
        return people;
    }

    public String getDate() {
        return date;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }}