package com.mnnyang.tallybook.model;

/**
 * Created by mnnyang on 17-5-19.
 */

public class User {
    private String name;

    private String mail;
    private String password;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
