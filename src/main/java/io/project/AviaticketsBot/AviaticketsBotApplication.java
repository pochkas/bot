package io.project.AviaticketsBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AviaticketsBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AviaticketsBotApplication.class, args);
    }
}
