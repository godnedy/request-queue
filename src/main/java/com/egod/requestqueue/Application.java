package com.egod.requestqueue;

import com.egod.requestqueue.amqp.Receiver;
import com.egod.requestqueue.consumers.*;
import com.egod.requestqueue.request.persistance.RequestRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

//@ComponentScan(basePackages = {"com.egod.requestqueue"})
//@EnableJpaRepositories("com.egod.requestqueue.request.persistance")
@EnableConfigurationProperties(ApplicationProperties.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(Application.class, args);
    }

    @Bean(initMethod = "receive")
    Receiver receiver() {
        return new Receiver();
    }
}
