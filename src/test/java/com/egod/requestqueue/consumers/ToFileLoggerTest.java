package com.egod.requestqueue.consumers;

import com.egod.requestqueue.ApplicationProperties;
import com.egod.requestqueue.amqp.RabbitMQProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class ToFileLoggerTest {

    private final String MESSAGE = "Message";
    private final String MESSAGE2 = "Additional";

    @Mock
    private ApplicationProperties properties;

    private File file;

    @Before
    public void setUp() {
        initMocks(this);
        when(properties.getFileName()).thenReturn("testfile.txt");
        file = new File(properties.getFileName());
    }

    @Test
    public void messageReceived_fileExists_logsToFile() throws IOException {
        file.createNewFile();
        ToFileLogger logger = new ToFileLogger(properties);
        logger.handleEvent(MESSAGE);
        assertTrue(MESSAGE.equals(readLines().trim()));
    }

    private String readLines() throws IOException {
        try(FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining());
        }
    }

    @Test
    public void messageReceived_fileNotExists_createsFileAndLogsToFile() throws IOException {
        ToFileLogger logger = new ToFileLogger(properties);
        logger.handleEvent(MESSAGE);
        assertTrue(file.exists());
        assertTrue(MESSAGE.equals(readLines().trim()));
    }

    @Test
    public void messageReceived_dataAlreadyInFile_appendsNewDataToFile() throws IOException {
        file.createNewFile();
        ToFileLogger logger = new ToFileLogger(properties);
        logger.handleEvent(MESSAGE);
        logger.handleEvent(MESSAGE2);
        assertTrue(MESSAGE.concat(MESSAGE2).equals(readLines().trim()));
    }

    @After
    public void cleanUp() {
        file.delete();
    }
}