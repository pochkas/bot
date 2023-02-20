package io.project.AviaticketsBot.service;
import io.project.AviaticketsBot.model.FlightResponse;



public interface FlightService {
    public FlightResponse getFlights(String url);
}
