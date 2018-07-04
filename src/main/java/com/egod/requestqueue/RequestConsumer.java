package com.egod.requestqueue;

import org.springframework.http.HttpEntity;

public interface RequestConsumer { // TODO ewentualnie zamienic na interfejs funkcyjny

    void handleEvent(HttpEntity request);
}
