package com.example.firetry1;

import android.annotation.SuppressLint;

public class EventType extends Event{
    //todo this class extends Event class and classify between its type

    private String typeSelected;
    @SuppressLint("SuspiciousIndentation")
    public EventType(String type) {
        super(type);
        if(type == "Agriculture" )
        this.typeSelected="Agriculture";
        if(type == "Planting" )
        this.typeSelected="Planting";
    }
}
