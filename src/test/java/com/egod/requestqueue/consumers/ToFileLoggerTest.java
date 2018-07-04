package com.egod.requestqueue.consumers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;

import java.io.*;

public class ToFileLoggerTest {

    private final String FILE_NAME = "file.txt";
    private final String REQUEST_BODY = "{Body}";
    private File file;
    private HttpEntity mockEntity;

    @Before
    public void setUp() {
        file = new File(FILE_NAME);
        mockEntity = Mockito.mock(HttpEntity.class);
        Mockito.when(mockEntity.getBody()).thenReturn(REQUEST_BODY);
    }

    @Test
    public void typeTwoProperRequestReceived_fileExists_logsToFile() throws IOException {
        file.createNewFile();
        ToFileLogger logger = new ToFileLogger(FILE_NAME);
        logger.handleEvent(mockEntity);
        Assert.assertTrue(REQUEST_BODY.equals(readLineFromFile().trim()));
    }

    @Test
    public void typeTwoProperRequestReceived_fileNotExists_createsFileAndLogsToFile() throws IOException {
        ToFileLogger logger = new ToFileLogger(FILE_NAME);
        logger.handleEvent(mockEntity);
        Assert.assertTrue(file.exists());
        Assert.assertTrue(REQUEST_BODY.equals(readLineFromFile().trim()));
    }

    private String readLineFromFile() throws IOException {
        try(FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.readLine();
        }
    }

    @After
    public void cleanUp() {
        file.delete();
    }
}