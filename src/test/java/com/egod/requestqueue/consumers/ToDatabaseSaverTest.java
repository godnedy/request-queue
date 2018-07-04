package com.egod.requestqueue.consumers;

import com.egod.requestqueue.domain.Request;
import com.egod.requestqueue.repository.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ToDatabaseSaverTest {

    private final String REQUEST_BODY = "{Body}";

    private ToDatabaseSaver saver;

    @Mock
    private HttpEntity mockEntity;

    @Mock
    private RequestRepository repository;

    @Before
    public void setUpTest() {
        initMocks(this);
        when(mockEntity.getBody()).thenReturn(REQUEST_BODY);
        doAnswer(returnsFirstArg()).when(repository).save(any(Request.class));
        saver = new ToDatabaseSaver(repository);
    }

    @Test
    public void typeTwoProperRequestReceived_requestHandled_savesToDatabase() {
        //given
        //when
        saver.handleEvent(mockEntity);
        //then
        verify(repository, Mockito.times(1)).save(any());
        verify(repository).save(argThat(aBar -> REQUEST_BODY.equals(aBar.getRequestBody())));
    }
}
