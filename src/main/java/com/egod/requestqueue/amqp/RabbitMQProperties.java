package com.egod.requestqueue.amqp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("rabbitmq")
public class RabbitMQProperties {
    private String queueName;
    private String host;
}
