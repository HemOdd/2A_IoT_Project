package com.example.team_2a_security.History_Screen;

public class Sensor {
    private String sensorName, dateTime, location;

    public Sensor(String name, String datetime, String location)
    {
        this.sensorName = name;
        this.dateTime = datetime;
        this.location = location;

    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
