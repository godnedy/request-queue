package com.egod.requestqueue.amqp;

import com.egod.requestqueue.request.domain.Request;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class Publisher {

    private final RabbitMQProperties properties;

    public void publish(Request request) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(properties.getHost());
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String message = request.getMessage();
        channel.queueDeclare(properties.getQueueName(), false, false, false, null);
        channel.basicPublish("", properties.getQueueName(), null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    };
}
