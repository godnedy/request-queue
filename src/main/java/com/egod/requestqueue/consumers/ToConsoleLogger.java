package com.egod.requestqueue.consumers;

import org.springframework.http.HttpEntity;

public class ToConsoleLogger implements RequestConsumer {

    @Override
    public void handleEvent(String message){
        System.out.println(message);
    }
}
