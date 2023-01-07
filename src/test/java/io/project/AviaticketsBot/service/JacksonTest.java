package io.project.AviaticketsBot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

public class JacksonTest {


        ObjectMapper objectMapper = new ObjectMapper();

        @Test
        void pojoToJsonString() throws JsonProcessingException {
            Data data = new Data(1923, 29, new Departure("ist","s","fgjhj"));

            String json = objectMapper.writeValueAsString(data);

            System.out.println(json);
        }

    @Test
    void jsonStringToPojo() throws JsonProcessingException {
        String dataJson = "{\n" +
                " \"data\" : \"Jalil\",\n" +
                " \"passengerLocalTime\" : \"21\",\n" +
               "}";

        Data data = objectMapper.readValue(dataJson, Data.class);

      //  assertThat(data.getDeparture().isEqualTo("IST"));
    }
}
