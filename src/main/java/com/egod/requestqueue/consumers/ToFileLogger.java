package com.egod.requestqueue.consumers;

import com.egod.requestqueue.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class ToFileLogger implements RequestConsumer {

    private final ApplicationProperties properties;

    @Override
    @SneakyThrows
    public void handleEvent(String message) {
        File file = new File(properties.getFileName());
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fileWriter = new FileWriter(properties.getFileName(), true);){
            fileWriter.append(message+ "\n");
        }
    }
}
