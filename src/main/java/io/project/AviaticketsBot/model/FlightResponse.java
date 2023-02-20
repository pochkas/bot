package io.project.AviaticketsBot.model;

import lombok.*;

import java.io.Serializable;
import java.util.Arrays;


@NoArgsConstructor
@Getter

public class FlightResponse implements Serializable {

    public Data[] data;

    public Data[] getData() {
        return data;
    }

    public FlightResponse(Data[] data){
       this.data=data;
    }

    public void setData(Data[] data) {

        this.data = data;
    }


    @Override
    public boolean equals(Object o){

        if(o==this){
            return true;
        }

        if(!(o instanceof FlightResponse)){
            return false;
        }

        FlightResponse obj=(FlightResponse) o;

        return Arrays.equals(obj.getData(), this.getData());


    }





}
