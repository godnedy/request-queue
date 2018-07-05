package com.egod.requestqueue.amqp;

import com.egod.requestqueue.consumers.RequestConsumer;
import com.egod.requestqueue.consumers.RequestConsumerFactory;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.egod.requestqueue.amqp.Publisher.QUEUE_NAME;

@Service
public class Receiver {

    @Autowired
    RequestConsumerFactory requestConsumerFactory;

    public void receive() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                RequestConsumer requestConsumer = requestConsumerFactory.createConsumer(message);
                requestConsumer.handleEvent(message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }



}
