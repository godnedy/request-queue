package com.egod.requestqueue.amqp;

import com.egod.requestqueue.consumers.Rejector;
import com.egod.requestqueue.consumers.RequestConsumer;
import com.egod.requestqueue.consumers.ToFileLogger;
import com.egod.requestqueue.request.domain.Request;
import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;

import static com.egod.requestqueue.amqp.Publisher.QUEUE_NAME;

public class Receiver {

    RequestConsumer requestConsumer = new ToFileLogger("file.txt");

    @SneakyThrows
    public void receive(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel);
        String message = channel.basicConsume(QUEUE_NAME, true, consumer);

        requestConsumer.handleEvent(message);
    }

}
