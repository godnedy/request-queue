package com.egod.requestqueue.consumers;

import com.egod.requestqueue.RequestConsumer;
import com.egod.requestqueue.domain.Request;
import com.egod.requestqueue.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

@RequiredArgsConstructor
public class ToDatabaseSaver implements RequestConsumer{

    @Autowired
    private final RequestRepository repository;

    @Override
    public void handleEvent(HttpEntity request) {
        repository.save(new Request(request));
    }
}
