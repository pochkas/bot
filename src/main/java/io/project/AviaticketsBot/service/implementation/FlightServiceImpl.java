package io.project.AviaticketsBot.service.implementation;

import io.project.AviaticketsBot.config.BotConfig;
import io.project.AviaticketsBot.model.FlightResponse;
import io.project.AviaticketsBot.service.FlightService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@Configuration
public class FlightServiceImpl implements FlightService {

    BotConfig botConfig;

    public FlightServiceImpl(BotConfig botConfig) {
        this.botConfig = botConfig;
    }


    @Override
    public FlightResponse getFlights(String url) {


        RestTemplate restTemplate = new RestTemplate();
        log.info(url);
        HttpHeaders headers = new HttpHeaders();



        headers.set("X-RapidAPI-Key", botConfig.getRapidApiKey());
        headers.set("X-RapidAPI-Host", botConfig.getRapidApiHost());


        HttpEntity request = new HttpEntity(headers);


        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                FlightResponse.class
        ).getBody();


    }
}
