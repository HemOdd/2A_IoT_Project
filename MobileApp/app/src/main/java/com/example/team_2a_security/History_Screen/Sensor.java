package com.example.team_2a_security.History_Screen;

public class Sensor {
    private String sensorType, dateTime, location;
    private int id ;
    private  double value;


    public Sensor(int id, String sensorType, String datetime, String location, Double value)
    {
        this.id = id;
        this.dateTime = datetime;
        this.location = location;
        this.value = value;
        this.sensorType = sensorType;

    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getSensorType() {
        return sensorType;
    }

    public double getValue() {
        return value;
    }

    public int getId() {
        return id;
    }


}
