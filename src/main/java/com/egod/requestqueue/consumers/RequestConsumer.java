package com.egod.requestqueue.consumers;

import org.springframework.http.HttpEntity;

import java.io.IOException;

public interface RequestConsumer { // TODO ewentualnie zamienic na interfejs funkcyjny

    void handleEvent(String message) throws IOException;
}
