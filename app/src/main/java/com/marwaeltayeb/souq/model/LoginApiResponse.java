package com.marwaeltayeb.souq.model;

public class LoginApiResponse {

    private int id;
    private String name;
    private String email;
    private boolean error;
    private String message;
    private String password;
    private String address;
    private String phone_number;
    private String token;
    private String age;
    private String gender;
    private boolean isAdmin;

    public LoginApiResponse(String message) {
        this.message = message;
        this.error = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
