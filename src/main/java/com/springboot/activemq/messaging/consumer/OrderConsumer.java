package com.springboot.activemq.messaging.consumer;


import com.springboot.activemq.messaging.config.TlsConfig;

import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import static com.springboot.activemq.messaging.system.SetEnv.requireEnv;

public class OrderConsumer {

    public static void main(String[] args) {
        String username = "consumer";
        String password = requireEnv("ORDER_CONSUMER_PASSWORD");

        try (ActiveMQConnectionFactory factory =
                     TlsConfig.createFactory(username, password);
             JMSContext context = factory.createContext()) {

            Queue queue = context.createQueue("orders.out");
            JMSConsumer consumer = context.createConsumer(queue);

            consumer.setMessageListener(message -> {
                try {
                    String body = message.getBody(String.class);
                    System.out.println("Order received: " + body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            new java.util.concurrent.CountDownLatch(1).await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
