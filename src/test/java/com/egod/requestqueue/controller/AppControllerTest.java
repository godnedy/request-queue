package com.egod.requestqueue.controller;

import com.egod.requestqueue.amqp.Publisher;
import com.egod.requestqueue.consumers.ToDatabaseSaver;
import com.egod.requestqueue.request.ExtendedRequest;
import com.egod.requestqueue.request.domain.Request;
import com.egod.requestqueue.request.persistance.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;

import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppControllerTest {

    private ToDatabaseSaver saver;
    private final ExtendedRequest extendedRequest = new ExtendedRequest("Type1", "message");
    private final ExtendedRequest noTypeGivenExtendedRequest = new ExtendedRequest(null, "message");
    private final ExtendedRequest noMessageGivenExtendedRequest = new ExtendedRequest("Type1", null);
    private final String POST_URL = "/requests";


    private final String GETALL_URL = "/requests";
    private static final Request[] REQUESTS_FAKE_DB = {new Request(1L, "Message1", LocalTime.now()),
            new Request(2L, "Message2", LocalTime.now())};

    private MockMvc mockMvc;

    @Mock
    private Publisher publisher;
    @Mock
    private RequestRepository repository;

    @Before
    public void setUpTest() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AppController(publisher, repository))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        doAnswer(returnsFirstArg()).when(repository).save(any(Request.class));
    }

    @Test
    public void postRequest_properRequestReceived_return200() throws Exception {
        //given
        doNothing().when(publisher).publish(any(ExtendedRequest.class));
        //when
        mockMvc.perform(post(POST_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(extendedRequest.toJSONString()))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @Test
    public void postRequest_noTypeInRequest_returns400() throws Exception {
        //when
        mockMvc.perform(post(POST_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(noTypeGivenExtendedRequest.toJSONString()))
                .andDo(print())
        //then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postRequest_noMessageInRequest_returns400() throws Exception {
        //when
        mockMvc.perform(post(POST_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(noMessageGivenExtendedRequest.toJSONString()))
                .andDo(print())
        //then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postRequest_rabbitmqUnavailable_return202() throws Exception {
        //when
        doThrow(new RuntimeException("Some exception")).when(publisher).publish(any(ExtendedRequest.class));
        mockMvc.perform(post(POST_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(extendedRequest.toJSONString()))
                .andDo(print())
        //then
                .andExpect(status().isAccepted());
    }

    @Test
    public void getAll_properRequest_return200() throws Exception {

        //given
        when(repository.findAll()).thenReturn(new PageImpl<>(newArrayList(REQUESTS_FAKE_DB)));
        //when
        mockMvc.perform(get(GETALL_URL))
        //then
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(repository)
                .findAll();
    }

    @Test
    public void getRequest_someRequestsAvailableInDatabase_returnRequestsFromDatabase() throws Exception {
        //given
        when(repository.findAll()).thenReturn(new PageImpl<>(newArrayList(REQUESTS_FAKE_DB)));
        //when
        mockMvc.perform(get(GETALL_URL))
        //then
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[*].message", hasItems(REQUESTS_FAKE_DB[0].getMessage())))
                .andExpect(jsonPath("$.content[*].message", hasItems(REQUESTS_FAKE_DB[1].getMessage())));
        verify(repository)
                .findAll();
    }

}
