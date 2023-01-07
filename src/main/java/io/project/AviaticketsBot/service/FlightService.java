package io.project.AviaticketsBot.service;

import io.project.AviaticketsBot.model.Data;
import io.project.AviaticketsBot.model.FlightResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;


public interface FlightService {
    public FlightResponse getFlights(String url);
}
