package com.egod.requestqueue.consumers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;

import java.io.*;

public class ToConsoleLoggerTest {

    HttpEntity mockEntity;
    public final String REQUEST_BODY = "{Body}";

    @Before
    public void setUpTest(){
        mockEntity = Mockito.mock(HttpEntity.class);
        Mockito.when(mockEntity.getBody()).thenReturn(REQUEST_BODY);
    }

    @Test
    public void typeTwoProperRequestReceived_requestHandled_logsBodyToConsole() throws IOException {
        ToConsoleLogger logger = new ToConsoleLogger();
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        logger.handleEvent(mockEntity);
        Assert.assertTrue(REQUEST_BODY.contentEquals(out.toString().trim()));
    }
}
