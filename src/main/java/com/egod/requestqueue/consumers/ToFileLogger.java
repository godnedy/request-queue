package com.egod.requestqueue.consumers;

import com.egod.requestqueue.RequestConsumer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class ToFileLogger implements RequestConsumer {

    private final String pathToFile;

    @Override
    @SneakyThrows
    public void handleEvent(HttpEntity request) throws IOException {
        File file = new File(pathToFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (PrintWriter out = new PrintWriter(pathToFile)) {
            out.println(request.getBody());
        }
    }
}
