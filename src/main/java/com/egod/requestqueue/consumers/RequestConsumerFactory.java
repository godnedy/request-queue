package com.egod.requestqueue.consumers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class RequestConsumerFactory {

    private final ToConsoleLogger toConsoleLogger;
    private final Rejector rejector;
    private final ToFileLogger toFileLogger;
    private final ToDatabaseSaver toDatabaseSaver;

    public RequestConsumer createConsumer(String message) {
        if(message.startsWith("Type1")){
            return toDatabaseSaver;
        }
        else if (message.startsWith("Type2")){
            return rejector;
        }
        else if(message.startsWith("Type3")) {
            return toFileLogger;
        }
        else if(message.startsWith("Type2")){
            return toConsoleLogger;
        }
        else {
            return null; //TODO should throw exception
        }
    }
}
