package com.example.projectpeak1.entities;

public class TestUser {
    private String email;
    private String password;


    public TestUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public TestUser() {
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
}
