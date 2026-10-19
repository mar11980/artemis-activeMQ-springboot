package com.springboot.activemq.messaging.consumer.expired;

import jakarta.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ExpiryConsumer {

    @JmsListener(destination = "EXPIRY")
    public void consume(TextMessage message)
            throws Exception {

        System.out.println();

        System.out.println(
                "Expired message:"
        );

        System.out.println(
                "************************************"
        );
        System.out.println();
    }
}
