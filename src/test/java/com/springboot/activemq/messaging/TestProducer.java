package com.springboot.activemq.messaging;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class TestProducer {

    public static void main(String[] args) {

        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        ); JMSContext context =
                     factory.createContext("admin", "admin")) {

            JMSProducer producer = context.createProducer();

            producer.setTimeToLive(5000);

            System.out.println(
                    "Sending message at: "
                            + System.currentTimeMillis()
            );

            producer.send(
                    context.createQueue("orders"),
                    "TEST TTL MESSAGE"
            );

            System.out.println("Message sent");

        }
    }
}