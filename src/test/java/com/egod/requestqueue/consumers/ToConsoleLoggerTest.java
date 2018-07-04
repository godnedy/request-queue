package com.egod.requestqueue.consumers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ToConsoleLoggerTest {

    private final String REQUEST_BODY = "{Body}";

    @Mock
    private HttpEntity mockEntity;

    @Before
    public void setUpTest() {
        initMocks(this);
        when(mockEntity.getBody()).thenReturn(REQUEST_BODY);
    }

    @Test
    public void typeTwoProperRequestReceived_requestHandled_logsBodyToConsole() throws IOException {
        ToConsoleLogger logger = new ToConsoleLogger();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        logger.handleEvent(mockEntity);
        assertTrue(REQUEST_BODY.concat("\r\n").contentEquals(out.toString()));
    }
}
