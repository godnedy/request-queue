package  com.egod.requestqueue.controller;

import com.egod.requestqueue.amqp.Publisher;
import com.egod.requestqueue.request.domain.Request;
import com.egod.requestqueue.request.persistance.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppController {

    private final Publisher publisher;
    private final RequestRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String receive(@Valid @RequestBody ExtendedRequest extendedRequest) {
        try {
            publisher.publish(extendedRequest);
        } catch (Exception e) {
            throw new RuntimeException("Request queue unavailable");
        }
        return extendedRequest.getMessage();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Request> getAll(){
        return repository.findAll();
    }
}
