package  com.egod.requestqueue.controller;

import com.egod.requestqueue.amqp.Publisher;
import com.egod.requestqueue.request.ExtendedRequest;
import com.egod.requestqueue.request.domain.Request;
import com.egod.requestqueue.request.persistance.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppController {

    private final Publisher publisher;
    private final RequestRepository repository;

    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity<?> receive(@Valid @RequestBody ExtendedRequest extendedRequest) {
        try {
            publisher.publish(extendedRequest);
        } catch (Exception e) {
            return new ResponseEntity<>("Adding request to queue impossible", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public Iterable<Request> getAll(){
        return repository.findAll();
    }
}
