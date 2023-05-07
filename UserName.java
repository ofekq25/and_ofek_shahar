package com.example.firetry1;

import java.util.ArrayList;

public class UserName {
    private String name,email,password;
    private boolean isManager;


    public UserName(){

    }
    public UserName(String name,String email,String password,Boolean isManager){
        this.name = name;
        this.email = email;
        this.password = password;
        this.isManager=isManager;
    }
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public boolean getIsManager() {
        return isManager;
    }
    public void setManager(boolean manager) {
        this.isManager = isManager;
    }

    public String toString() {return "name" + name + " email" + email + " password" + password;}



}
