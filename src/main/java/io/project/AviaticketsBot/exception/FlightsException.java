package io.project.AviaticketsBot.exception;

import io.project.AviaticketsBot.model.FlightResponse;

public class FlightsException extends RuntimeException {


    public FlightsException(String message) {
        super(message);
    }
}
