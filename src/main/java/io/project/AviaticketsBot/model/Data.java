package io.project.AviaticketsBot.model;

import com.vdurmont.emoji.EmojiParser;
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
        return EmojiParser.parseToUnicode(" :alarm_clock: ") + getDeparture() +
                ", flight number=" + getFlightNumber() +
                ";";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Data)) {
            return false;
        }
        Data obj = (Data) o;

        if (obj.departure == null && departure == null) {
            return true;
        }

        if (obj.departure == null || departure == null) {
            return false;
        }


        return (obj.flightNumber == flightNumber && obj.sequenceNumber == sequenceNumber && obj.departure.equals(departure));
    }
}
