package com.example.ecommerceapp.model;

public class Users {
    private String phoneNumber, name, password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(String phoneNumber, String name, String password) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
    }

    public Users() {
    }
}
