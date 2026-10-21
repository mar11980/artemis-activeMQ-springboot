package com.springboot.activemq.messaging.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.activemq.messaging.dto.OrderRequest;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderConsumer {

    private final ObjectMapper objectMapper;

    @JmsListener(
            destination = "orders.out",
            containerFactory = "jmsListenerContainerFactory"
    )
    public void consumeMessages(TextMessage message) throws JsonProcessingException, JMSException {

        OrderRequest order =
                objectMapper.readValue(
                        message.getText(),
                        OrderRequest.class
                );

        /*
         * Simulate processing failure.
         */

        System.out.println("Message ID: " + message.getJMSMessageID());

        System.out.println(
                "Delivery count: " +
                        message.getIntProperty("JMSXDeliveryCount")
        );

        System.out.println(
                "Redelivered: " +
                        message.getJMSRedelivered()
        );

        System.out.println(
                "Destination: " +
                        message.getJMSDestination()
        );

        if (order.isFail()) {

            System.out.println(
                    "Processing FAILED! ❌"
            );

            throw new RuntimeException(
                    "Simulated processing error"
            );
        }

        System.out.println(
                "Order received from orders.out: " + message
        );

    }

}
