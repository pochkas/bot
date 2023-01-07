package io.project.AviaticketsBot.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter

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
        return "Flights: " +
                "departure=" + getDeparture() +
                ", flightNumber=" + getFlightNumber() +
                ";";
    }
}
