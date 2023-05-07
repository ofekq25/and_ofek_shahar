package com.example.firetry1;

public class Event {
    private String title;
    private String date;
    private String time;
    private String place;
    private String description;
    private String type;
    private String managersEmail;


    public Event(){}
    public Event(String title, String date, String time, String place, String description, String type,String managersEmail) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.place = place;
        this.description = description;
        this.type = type;
        this.managersEmail=managersEmail;
    }

    public Event(String type) {
        this.setType(type);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManagersEmail() {
        return managersEmail;
    }

    public void setManagersEmail(String managersEmail) {
        this.managersEmail = managersEmail;
    }
}
