package com.egod.requestqueue.consumers;

import com.egod.requestqueue.RequestConsumer;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;

import java.io.File;
import java.io.PrintWriter;

public class ToFileLogger implements RequestConsumer {
    public static final String PATH_TO_FILE = "file.txt";

    @Override
    @SneakyThrows
    public void handleEvent(HttpEntity request) {

        File file = new File(PATH_TO_FILE);
        if (file.exists()) {
            PrintWriter writer = new PrintWriter(PATH_TO_FILE);
            writer.println(request.getBody());
        }
    }
}
