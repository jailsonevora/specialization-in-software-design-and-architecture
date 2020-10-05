package com.example.sharingapp;

import java.util.UUID;

/**
 * Created by jevora on 11/19/2017.
 */

public class User {
    private String username;
    private String email;
    private String id;

    public User(String username, String email, String id) {
        this.username = username;
        this.email = email;

        if (id == null){
            setId();
        } else {
            updateId(id);
        }
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public void updateId(String id){
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
