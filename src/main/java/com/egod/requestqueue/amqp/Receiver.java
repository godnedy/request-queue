package com.egod.requestqueue.amqp;

import com.egod.requestqueue.consumers.RequestConsumer;
import com.egod.requestqueue.consumers.RequestConsumerFactory;
import com.egod.requestqueue.request.ExtendedRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Receiver {

    private final RabbitMQProperties properties;
    private final RequestConsumerFactory requestConsumerFactory;

    public void receive() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getHost());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(properties.getQueueName(), false, false, false, null);
        System.out.println(" [*] Waiting for messages");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                ExtendedRequest extendedRequest = createExtendedRequest(body);
                System.out.println(" [x] Received '" + extendedRequest.getMessage() + "'");
                RequestConsumer requestConsumer = null;
                try {
                    requestConsumerFactory.createConsumer(extendedRequest.getType()).handleEvent(extendedRequest.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(properties.getQueueName(), true, consumer);
    }

    private ExtendedRequest createExtendedRequest(byte[] body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(body), ExtendedRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ExtendedRequest("", "");
    }
}
