package com.egod.requestqueue.consumers;

import org.springframework.stereotype.Component;

@Component
public class ToConsoleLogger implements RequestConsumer {

    @Override
    public void handleEvent(String message){
        System.out.println(message);
    }
}
