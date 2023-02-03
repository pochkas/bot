package io.project.AviaticketsBot.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.AviaticketsBot.config.BotConfig;
import io.project.AviaticketsBot.model.ErrorResponse;
import io.project.AviaticketsBot.model.FlightResponse;
import io.project.AviaticketsBot.service.FlightService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    BotConfig botConfig;
    @Autowired
    RestTemplate restTemplate;
//    public FlightServiceImpl(BotConfig botConfig, RestTemplate restTemplate) {
//
//        this.botConfig = botConfig;
//        this.restTemplate=restTemplate;
//    }




    @Override
    public FlightResponse getFlights(String url) {



        log.info(url);
        HttpHeaders headers = new HttpHeaders();


        headers.set("X-RapidAPI-Key", botConfig.getRapidApiKey());
        headers.set("X-RapidAPI-Host", botConfig.getRapidApiHost());


        HttpEntity request = new HttpEntity(headers);


        String jsonStr = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        ).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FlightResponse response = null;
        try {
            response = objectMapper.readValue(jsonStr, FlightResponse.class);
        } catch (JsonProcessingException e) {
            try {
                ErrorResponse errorResponse = objectMapper.readValue(jsonStr, ErrorResponse.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException("Error",ex);
            }
            throw new RuntimeException(e);
        }
        return response;

    }
}
