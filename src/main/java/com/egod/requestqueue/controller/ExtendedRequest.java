package com.egod.requestqueue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ExtendedRequest {

    @NotNull
    private String type;

    @NotNull
    private String message;

    public ExtendedRequest(String type, String message){
        this.type = type;
        this.message = message;
    }

    @SneakyThrows
    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
