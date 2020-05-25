package com.example.team_2a_security.data_classes;

/**
 * Class to represent on message object
 */
public class Message {
    private String date;
    private String message;
    private String username;

    public Message(){

    }


    public Message(String message, String date, String username){
        this.message = message;
        this.date=date;
        this.username = username;
    }

    public String getMessage(){
        return this.message;
    }

    public String getDate(){
        return this.date;
    }

    public String getUsername(){
        return this.username;
    }

    public void setMessage(String message){
         this.message=message;
    }

    public void setDate(String date){
         this.date=date;
    }

    public void setUsername(String username){
         this.username=username;
    }
}
