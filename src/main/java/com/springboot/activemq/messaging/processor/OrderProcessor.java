package com.springboot.activemq.messaging.processor;


import com.springboot.activemq.messaging.config.TlsConfig;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import static com.springboot.activemq.messaging.system.SetEnv.requireEnv;

public class OrderProcessor {

    public static void main(String[] args) {
        String username = "developer";
        String password = requireEnv("ORDER_PROCESSOR_PASSWORD");

        try (ActiveMQConnectionFactory factory =
                     TlsConfig.createFactory(username, password);
             JMSContext context = factory.createContext()) {

            Queue input = context.createQueue("orders.in");
            Queue output = context.createQueue("orders.out");

            JMSConsumer consumer = context.createConsumer(input);
            JMSProducer producer = context.createProducer();

            consumer.setMessageListener(message -> {
                try {
                    String body = message.getBody(String.class);

                    // Add business validation/transformation here.
                    producer.send(output, body);

                    System.out.println("Forwarded order to orders.out");

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
