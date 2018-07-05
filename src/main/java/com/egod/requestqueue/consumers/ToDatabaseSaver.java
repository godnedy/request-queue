package com.egod.requestqueue.consumers;

import com.egod.requestqueue.request.domain.Request;
import com.egod.requestqueue.request.persistance.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ToDatabaseSaver implements RequestConsumer{

    @Autowired
    private final RequestRepository repository;

    @Override
    public void handleEvent(String message) {
        repository.save(new Request(message));
    }
}
