package io.project.AviaticketsBot.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Departure {
    @JsonProperty("passengerLocalTime")
    String passengerLocalTime;
    String date;
    String terminal;


    public Departure(String passengerLocalTime, String date, String terminal) {
        this.passengerLocalTime = passengerLocalTime;
        this.date = date;
        this.terminal = terminal;
    }

    public String getPassengerLocalTime() {
        return passengerLocalTime;
    }

    public void setPassengerLocalTime(String passengerLocalTime) {
        this.passengerLocalTime = passengerLocalTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString(){
        return getPassengerLocalTime() + ", "+getDate()+", "+getTerminal();
    }

}
