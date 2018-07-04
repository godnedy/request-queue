package com.egod.requestqueue.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Request {

    public Request(HttpEntity request){
        this.requestBody = request.getBody().toString();
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
