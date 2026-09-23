package com.springboot.activemq.messaging.consumer.dlq;

import jakarta.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterConsumer {

    @JmsListener(destination = "DLQ")
    public void consume(TextMessage message)
            throws Exception {

        System.out.println();
        System.out.println(
                "*************** DLQ ***************"
        );

        System.out.println(
                "Dead letter message:"
        );

        System.out.println(
                message.getText()
        );

        System.out.println(
                "************************************"
        );
        System.out.println();
    }
}
