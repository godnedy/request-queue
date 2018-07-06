package com.egod.requestqueue.consumers;

public interface RequestConsumer {

    void handleEvent(String message);
}
