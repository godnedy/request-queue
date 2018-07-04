package com.egod.requestqueue;

import org.springframework.http.HttpEntity;

import java.io.IOException;

public interface RequestConsumer { // TODO ewentualnie zamienic na interfejs funkcyjny

    void handleEvent(HttpEntity request) throws IOException;
}
