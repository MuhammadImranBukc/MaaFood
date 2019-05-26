package com.example.maafooD;

public class Plan {
    public String name, people, date, id;
        private String mKey;

    public Plan() {
    }

    public Plan(String name, String people, String date, String id) {
        this.name = name;
        this.people = people;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

