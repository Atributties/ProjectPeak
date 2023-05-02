package com.example.projectpeak1.entities;

public class TestUser {
    private String email;
    private String pass;

    public TestUser(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public TestUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
