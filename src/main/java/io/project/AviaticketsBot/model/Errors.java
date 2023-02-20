package io.project.AviaticketsBot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Errors {

    String[] departureDate;

    String[] arrivalDate;

    public String[] getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String[] departureDate) {
        this.departureDate = departureDate;
    }

    public String[] getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String[] arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        return "Errors{" +
                "departureDate='" + getDepartureDate()[0] + '\'' +
                ", arrivalDate='" + getArrivalDate()[0] + '\'' +
                '}';
    }
}
