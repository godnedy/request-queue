package  com.egod.requestqueue.controller;

import com.egod.requestqueue.amqp.Publisher;
import com.egod.requestqueue.request.domain.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppController {

    private final Publisher publisher;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String receive(@RequestBody Request request) throws IOException, TimeoutException {
        publisher.publish(request);
        return request.getRequestBody();
    }
}
