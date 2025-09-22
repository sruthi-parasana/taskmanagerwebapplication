
package com.example.model;

public class User {
    private int id;
    private String username;
    private String password;

    // constructor
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // getters & setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
