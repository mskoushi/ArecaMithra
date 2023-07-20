package com.example.arecamithra;

public class UserHelperClass {
    String name,email,password,phoneNumber,username,image_url;

    public UserHelperClass() {

    }

    public UserHelperClass(String name,String username, String email, String password, String phoneNumber) {
        this.name = name;
        this.username=username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.image_url=" ";
    }
    public String getImageUrl() {
        return image_url;
    }
    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
