package io.project.AviaticketsBot.model;

import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@Getter

public class Departure implements Serializable {

    String passengerLocalTime;
    String date;
    String terminal;


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
        return getPassengerLocalTime() + ", "+"terminal="+getTerminal();
    }

}
