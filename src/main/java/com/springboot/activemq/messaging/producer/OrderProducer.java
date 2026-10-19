package com.springboot.activemq.messaging.producer;

import com.springboot.activemq.messaging.config.TlsConfig;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import static com.springboot.activemq.messaging.system.SetEnv.requireEnv;

public class OrderProducer {

    public static void main(String[] args) {
        String username = "producer";
        String password = requireEnv("ORDER_PRODUCER_PASSWORD");

        try (ActiveMQConnectionFactory factory =
                     TlsConfig.createFactory(username, password);
             JMSContext context = factory.createContext()) {

            Queue queue = context.createQueue("orders.in");

            context.createProducer().send(
                    queue,
                    "{\"orderId\":1003,\"product\":\"Ananas\"}");

            System.out.println("Order sent to orders.in");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
