package com.egod.requestqueue.consumers;

import com.egod.requestqueue.request.domain.Request;
import com.egod.requestqueue.request.persistance.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ToDatabaseSaverTest {

    private final String MESSAGE = "Message";

    private ToDatabaseSaver saver;

    @Mock
    private RequestRepository repository;

    @Before
    public void setUpTest() {
        initMocks(this);
        doAnswer(returnsFirstArg()).when(repository).save(any(Request.class));
        saver = new ToDatabaseSaver(repository);
    }

    @Test
    public void messageReceived_messageHandled_savesToDatabase() {
        //given
        //when
        saver.handleEvent(MESSAGE);
        //then
        verify(repository, Mockito.times(1)).save(any());
        verify(repository).save(argThat(aBar -> MESSAGE.equals(aBar.getMessage())));
    }
}
