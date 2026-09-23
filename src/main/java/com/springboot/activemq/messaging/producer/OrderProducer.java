package com.springboot.activemq.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import com.springboot.activemq.messaging.dto.OrderRequest;

import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final String QUEUE_NAME = "orders";

    private final ConnectionFactory connectionFactory;

    private final ObjectMapper objectMapper;

    public OrderProducer(
            ConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        this.connectionFactory = connectionFactory;
        this.objectMapper = objectMapper;
    }

    public void send(
            OrderRequest order,
            long ttl) throws Exception {

        String json =
                objectMapper.writeValueAsString(order);

        try (JMSContext context =
                     connectionFactory.createContext()) {

            Queue queue =
                    context.createQueue(QUEUE_NAME);

            var producer =
                    context.createProducer();

            if (ttl > 0) {
                producer.setTimeToLive(ttl);
            }

            producer.send(queue, json);

            System.out.println(
                    "Message sent: " + json
            );
        }
    }
}
