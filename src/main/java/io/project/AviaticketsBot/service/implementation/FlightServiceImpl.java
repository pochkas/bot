package io.project.AviaticketsBot.service.implementation;

import io.project.AviaticketsBot.model.Data;
import io.project.AviaticketsBot.model.FlightResponse;
import io.project.AviaticketsBot.service.FlightService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component

public class FlightServiceImpl implements FlightService {


    @Override
    public FlightResponse getFlights(String url) {


        RestTemplate restTemplate = new RestTemplate();
        log.info(url);
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-RapidAPI-Key", "5ca7009191mshfa48d28547bda93p1a28aejsn41c50e9f3632");
        headers.set("X-RapidAPI-Host", "flight-info-api.p.rapidapi.com");


        HttpEntity request = new HttpEntity(headers);


        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                FlightResponse.class
        ).getBody();


    }
}
