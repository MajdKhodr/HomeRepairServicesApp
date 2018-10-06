package com.scum.seg.ondemandhomerepairservices;

public class User {
    public String firstName, lastName, userName, passsord, email;


    public User(String firstName, String lastName, String userName, String passsord, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passsord = passsord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasssord() {
        return passsord;
    }

    public void setPasssord(String passsord) {
        this.passsord = passsord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
