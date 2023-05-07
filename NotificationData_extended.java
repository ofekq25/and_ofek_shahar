package com.example.firetry1;

public class NotificationData_extended {
    private String  date,hour;

    public NotificationData_extended(){}
    public NotificationData_extended( String date, String hour) {

        this.date = date;
        this.hour = hour;
    }



    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

