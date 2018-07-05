package com.egod.requestqueue.amqp;

import com.egod.requestqueue.request.domain.Request;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@AllArgsConstructor
//@NoArgsConstructor
public class Publisher {

    public static final String QUEUE_NAME = "request queue";

    @SneakyThrows
    public static void publish(Request request) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String message = request.getRequestBody();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    };

}
