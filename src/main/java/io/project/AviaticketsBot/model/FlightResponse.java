package io.project.AviaticketsBot.model;

import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@Getter

public class FlightResponse implements Serializable {

    public Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }
}
