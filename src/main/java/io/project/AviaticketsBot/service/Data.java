package io.project.AviaticketsBot.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Data implements Serializable {


    int flightNumber;
    int sequenceNumber;
    Departure departure;
    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }


//    public Data(int flightNumber, int sequenceNumber, Departure departure) {
//        this.departure = departure;
//        this.flightNumber = flightNumber;
//        this.sequenceNumber = sequenceNumber;
//    }


    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String toString() {
        return "Data{" +
                "departure=" + getDeparture() +
                ", flightNumber=" + getFlightNumber() +
                ", sequenceNumber=" + getSequenceNumber() +
                '}';
    }
}
