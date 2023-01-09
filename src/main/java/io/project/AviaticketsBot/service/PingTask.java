package io.project.AviaticketsBot.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@Getter
@Setter
public class PingTask {
    @Value("https://www.google.com")
    private  String utl;

    @Scheduled(fixedRateString = "${pingtask.period}")
    public void  pingMe(){
        try{
            URL url=new URL(getUtl());
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.connect();
            log.info("Ping OK");
            connection.disconnect();
        } catch (IOException e){
            log.error("Ping FAILED");
            e.printStackTrace();
        }
    }
}
