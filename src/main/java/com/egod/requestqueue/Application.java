package com.egod.requestqueue;

import com.egod.requestqueue.amqp.RabbitMQProperties;
import com.egod.requestqueue.amqp.Receiver;
import com.egod.requestqueue.consumers.RequestConsumerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@EnableJpaRepositories("com.egod.requestqueue.request.persistance")
@EnableConfigurationProperties({ApplicationProperties.class, RabbitMQProperties.class})
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(Application.class, args);
    }

    @Bean(initMethod = "receive")
    Receiver receiver(RabbitMQProperties properties, RequestConsumerFactory factory) {
        return new Receiver(properties, factory);
    }
}
