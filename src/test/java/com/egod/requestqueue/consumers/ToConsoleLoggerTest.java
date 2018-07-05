package com.egod.requestqueue.consumers;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ToConsoleLoggerTest {

    private final String MESSAGE = "Message";

    @Test
    public void messageReceived_messageHandled_logsMessageToConsole() throws IOException {
        ToConsoleLogger logger = new ToConsoleLogger();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        logger.handleEvent(MESSAGE);
        assertTrue(MESSAGE.equals(out.toString().trim()));
    }
}
