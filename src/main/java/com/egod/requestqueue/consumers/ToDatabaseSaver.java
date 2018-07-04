package com.egod.requestqueue.consumers;

import com.egod.requestqueue.RequestConsumer;
import org.springframework.http.HttpEntity;

public class ToDatabaseSaver implements RequestConsumer{

    @Override
    public void handleEvent(HttpEntity request) {

    }
}
