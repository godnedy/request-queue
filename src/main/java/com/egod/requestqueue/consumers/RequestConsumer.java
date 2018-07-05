package com.egod.requestqueue.consumers;

public interface RequestConsumer { // TODO ewentualnie zamienic na interfejs funkcyjny

    void handleEvent(String message);
}
