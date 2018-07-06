package com.egod.requestqueue.consumers;

import org.springframework.stereotype.Component;

@Component
public class Rejector implements RequestConsumer {

    @Override
    public void handleEvent(String message) {
        System.out.println("[[Message rejected]]");
    }
}
