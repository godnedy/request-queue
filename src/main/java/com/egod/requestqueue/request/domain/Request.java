package com.egod.requestqueue.request.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class Request {

    public Request(String message){
        this.requestBody = message;
        this.timestamp = LocalTime.now();
    }


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String requestBody;

    @Column
    private LocalTime timestamp;
}
