package  com.egod.requestqueue.controller;

import com.egod.requestqueue.amqp.Publisher;
import com.egod.requestqueue.request.domain.Request;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AppController {

//TODO sprawdzić czy działa z postawionym rabbitem
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String receive(@RequestBody Request request){
        Publisher.publish(request);
        return request.getRequestBody();
    }
}
