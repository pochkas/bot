package io.project.AviaticketsBot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.AviaticketsBot.config.BotConfig;
import io.project.AviaticketsBot.exception.FlightsException;
import io.project.AviaticketsBot.model.Data;
import io.project.AviaticketsBot.model.ErrorResponse;
import io.project.AviaticketsBot.model.FlightResponse;
import io.project.AviaticketsBot.service.implementation.FlightServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    BotConfig botConfig;

    @InjectMocks
    private FlightService flightService = new FlightServiceImpl();

    @Test
    public void getFlightsTestError() {

        HttpHeaders headers = new HttpHeaders();


        headers.set("X-RapidAPI-Key", botConfig.getRapidApiKey());
        headers.set("X-RapidAPI-Host", botConfig.getRapidApiHost());


        HttpEntity request = new HttpEntity(headers);

        String errorStr = "errors:ArrivalDate:0:\"'Arrival Date' is not a valid data or a data range.\"DepartureDate:0:\"'Departure Date' is not valid a data or a data range.\"";

        Mockito.when(restTemplate.exchange(
                        "https://flight-info-api.p.rapidapi.com/schedules?version=v1&DepartureDate=departure&DepartureAirport=to&ArrivalAirport=type", HttpMethod.GET, request, String.class))

                .thenReturn(new ResponseEntity(errorStr, HttpStatus.BAD_REQUEST));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            flightService.getFlights("https://flight-info-api.p.rapidapi.com/schedules?version=v1&DepartureDate=departure&DepartureAirport=to&ArrivalAirport=type");
        });

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void getFlightsTesSuccess() {


        HttpHeaders headers = new HttpHeaders();


        headers.set("X-RapidAPI-Key", botConfig.getRapidApiKey());
        headers.set("X-RapidAPI-Host", botConfig.getRapidApiHost());


        HttpEntity request = new HttpEntity(headers);

        String successStr = "{  \"data\": [    {      \"carrierCode\": {        \"iata\": \"\",        \"icao\": \"\"      },      \"serviceSuffix\": \"\",      \"flightNumber\": 0,      \"sequenceNumber\": 0,      \"departure\": {        \"airport\": {          \"iata\": \"\",          \"icao\": \"\"        },        \"terminal\": \"\",        \"date\": \"\",        \"passengerLocalTime\": \"\"      },      \"arrival\": {        \"airport\": {          \"iata\": \"\",          \"icao\": \"\"        },        \"terminal\": \"\",        \"date\": \"\",        \"passengerLocalTime\": \"\"      },      \"aircraftType\": {        \"iata\": \"\",        \"icao\": \"\"      },      \"serviceTypeCode\": {        \"iata\": \"\"      },      \"segmentInfo\": {        \"numberOfStops\": 0,        \"intermediateAirports\": {          \"iata\": [            {              \"sequenceNumber\": 0,              \"station\": \"\"            }          ]        }      },      \"oagFingerprint\": \"\",      \"codeshare\": {        \"operatingAirlineDisclosure\": {          \"code\": \"\",          \"name\": \"\",          \"number\": \"\"        },        \"aircraftOwner\": {          \"code\": \"\",          \"name\": \"\"        },        \"cockpitCrewEmployer\": {          \"code\": \"\",          \"name\": \"\",          \"number\": \"\"        },        \"jointOperationAirlineDesignators\": [          {            \"code\": \"\",            \"name\": \"\",            \"number\": \"\"          }        ],        \"comments010\": [          {            \"code\": \"\",            \"serviceNumber\": \"\",            \"suffix\": \"\"          }        ],        \"comment050\": {          \"code\": \"\",          \"serviceNumber\": \"\",          \"suffix\": \"\"        }      },      \"statusDetails\": [        {          \"state\": \"\",          \"updatedAt\": \"\",          \"equipment\": {            \"aircraftRegistrationNumber\": \"\",            \"actualAircraftType\": {              \"iata\": \"\",              \"icao\": \"\"            }          },          \"departure\": {            \"estimatedTime\": {              \"outGateTimeliness\": \"\",              \"outGateVariation\": \"\",              \"outGate\": {                \"local\": \"\",                \"utc\": \"\"              },              \"offGround\": {                \"local\": \"\",                \"utc\": \"\"              }            },            \"actualTime\": {              \"outGateTimeliness\": \"\",              \"outGateVariation\": \"\",              \"outGate\": {                \"local\": \"\",                \"utc\": \"\"              },              \"offGround\": {                \"local\": \"\",                \"utc\": \"\"              }            },            \"airport\": {              \"iata\": \"\",              \"icao\": \"\"            },            \"actualTerminal\": \"\",            \"gate\": \"\",            \"checkInCounter\": \"\"          },          \"arrival\": {            \"estimatedTime\": {              \"inGateTimeliness\": \"\",              \"inGateVariation\": \"\",              \"onGround\": {                \"local\": \"\",                \"utc\": \"\"              },              \"inGate\": {                \"local\": \"\",                \"utc\": \"\"              }            },            \"actualTime\": {              \"inGateTimeliness\": \"\",              \"inGateVariation\": \"\",              \"onGround\": {                \"local\": \"\",                \"utc\": \"\"              },              \"inGate\": {                \"local\": \"\",                \"utc\": \"\"              }            },            \"airport\": {              \"iata\": \"\",              \"icao\": \"\"            },            \"actualTerminal\": \"\",            \"gate\": \"\",            \"baggage\": \"\"          },          \"diversionAirport\": {            \"iata\": \"\",            \"icao\": \"\"          }        }      ]    }  ],  \"paging\": {    \"totalCount\": 0,    \"totalPages\": 0,    \"next\": \"\"  }}";

        Mockito.when(restTemplate.exchange(
                        "https://flight-info-api.p.rapidapi.com/status?version=v1&DepartureDate=2023-01-27&DepartureAirport=IST&ArrivalAirport=LGW", HttpMethod.GET, request, String.class))

                .thenReturn(new ResponseEntity(successStr, HttpStatus.OK));

        FlightResponse response = new FlightResponse();
        ObjectMapper mapper=new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            response =mapper.readValue(successStr, FlightResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        FlightResponse fl = flightService.getFlights("https://flight-info-api.p.rapidapi.com/status?version=v1&DepartureDate=2023-01-27&DepartureAirport=IST&ArrivalAirport=LGW");

        Assertions.assertEquals(response, fl);
    }
}