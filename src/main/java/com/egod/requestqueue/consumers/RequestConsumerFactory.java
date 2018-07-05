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

    public RequestConsumer createConsumer(String message) throws Exception {
        if(message.equals("Type1")){
            return toDatabaseSaver;
        }
        else if (message.equals("Type2")){
            return rejector;
        }
        else if(message.equals("Type3")) {
            return toFileLogger;
        }
        else if(message.equals("Type4")){
            return toConsoleLogger;
        }
        else {
            throw new Exception(message + " request type not supported");
        }
    }
}
