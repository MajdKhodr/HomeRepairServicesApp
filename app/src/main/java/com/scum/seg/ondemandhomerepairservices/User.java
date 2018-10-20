package com.scum.seg.ondemandhomerepairservices;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phonenumber;
    private String address;
    private String type;



    public User(String firstName, String lastName, String userName, String password, String email,
                String phonenumber, String address, String type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.type = type;
    }

    public User(){
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return "First name: " + firstName + ":" + "Last name:" + lastName + ":" + "User Name:"
                + userName + ":" + "Password: " + password + ":"+ "Email: " + email;
    }
}
