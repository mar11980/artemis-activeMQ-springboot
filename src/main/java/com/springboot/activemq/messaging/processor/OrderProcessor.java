package com.springboot.activemq.messaging.processor;


import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderProcessor {

    private final JmsTemplate orderProcessorJmsTemplate;

    @JmsListener(
            destination = "orders.in",
            containerFactory = "orderProcessorListenerFactory"
    )
    public void processMessages(String message) {

        orderProcessorJmsTemplate.convertAndSend(
                "orders.out",
                message
        );

    }
}

