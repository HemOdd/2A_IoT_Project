package com.example.team_2a_security.data_classes;

/**
 * Class to represent each user object
 */
public class Users {
    private String username;
    private String password;
    private boolean status; //active or inactive

    public Users(){

    }

    public Users(String username, String password,boolean status){
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(boolean status) { this.status = status; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getStatus() { return status; }


}
