package com.egod.requestqueue.consumers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class RequestConsumerFactory {

    private final ToConsoleLogger toConsoleLogger;
    private final Rejector rejector;
    private final ToFileLogger toFileLogger;
    private final ToDatabaseSaver toDatabaseSaver;


    public RequestConsumer createConsumer(String message) {
        if (message.startsWith("Type1")){
            return rejector;
        }
        if(message.startsWith("Type2")){
            return toConsoleLogger;
        }

        if(message.startsWith("Type3")){
            return toDatabaseSaver;
        }
        if(message.startsWith("Type4")){
            return toFileLogger;
        }
        else
            return null; //TODO should throw exception
    }


}
