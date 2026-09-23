package com.springboot.activemq.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.springboot.activemq.messaging.dto.OrderRequest;

@Component
public class OrderConsumer {

    private final ObjectMapper objectMapper;

    public OrderConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "orders")
    public void consume(TextMessage message)
            throws Exception {

        String body =
                message.getText();

        System.out.println(
                "================================"
        );

        System.out.println(
                "Received order: " + body
        );

        OrderRequest order =
                objectMapper.readValue(
                        body,
                        OrderRequest.class
                );

        /*
         * Simulate processing failure.
         */

        if (order.isFail()) {

            System.out.println(
                    "Processing FAILED!"
            );

            throw new RuntimeException(
                    "Simulated processing error"
            );
        }

        System.out.println(
                "Order processed successfully: "
                        + order.getId()
        );

        System.out.println(
                "================================"
        );
    }
}

