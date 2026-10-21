package com.springboot.activemq.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import static com.springboot.activemq.messaging.system.SetEnv.requireEnv;

@Configuration
@EnableJms
public class JmsConfig {

    @Bean
    public ConnectionFactory orderConsumerConnectionFactory() {
        return TlsConfig.createFactory(
                "consumer",
                requireEnv("ORDER_CONSUMER_PASSWORD")
        );
    }

    @Bean
    public ConnectionFactory orderProducerConnectionFactory() {
        return TlsConfig.createFactory(
                "producer",
                requireEnv("ORDER_PRODUCER_PASSWORD")
        );
    }

    @Bean
    public ConnectionFactory orderProcessorConnectionFactory() {
        return TlsConfig.createFactory(
                "developer",
                requireEnv("ORDER_PROCESSOR_PASSWORD")
        );
    }

    @Bean
    public ConnectionFactory orderDLQConnectionFactory() {
        return TlsConfig.createFactory(
                "admin",
                "admin"
        );
    }

    @Bean
    public ConnectionFactory orderExpiredConnectionFactory() {
        return TlsConfig.createFactory(
                "admin",
                "admin"
        );
    }

    @Bean
    public JmsTemplate orderProducerJmsTemplate(
            @Qualifier("orderProducerConnectionFactory")
            ConnectionFactory connectionFactory) {

        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public JmsTemplate orderConsumerJmsTemplate(
            @Qualifier("orderConsumerConnectionFactory")
            ConnectionFactory connectionFactory) {

        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public JmsTemplate orderProcessorJmsTemplate(
            @Qualifier("orderProcessorConnectionFactory")
            ConnectionFactory connectionFactory) {

        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public JmsTemplate orderDLQJmsTemplate(
            @Qualifier("orderDLQConnectionFactory")
            ConnectionFactory connectionFactory) {

        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public JmsTemplate orderExpiredJmsTemplate(
            @Qualifier("orderExpiredConnectionFactory")
            ConnectionFactory connectionFactory) {

        return new JmsTemplate(connectionFactory);
    }

    @Bean(name = "orderDLQListenerFactory")
    public DefaultJmsListenerContainerFactory orderDLQListenerFactory(
            @Qualifier("orderDLQConnectionFactory")
            ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);

        return factory;
    }

    @Bean(name = "orderExpiredListenerFactory")
    public DefaultJmsListenerContainerFactory orderExpiredListenerFactory(
            @Qualifier("orderExpiredConnectionFactory")
            ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);

        return factory;
    }

    @Bean(name = "jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            @Qualifier("orderConsumerConnectionFactory")
            ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);

        factory.setErrorHandler(t ->
                System.err.println("JMS listener failed: " + t.getMessage()));

        return factory;
    }


    @Bean(name = "orderProcessorListenerFactory")
    public DefaultJmsListenerContainerFactory orderProcessorListenerFactory(
            @Qualifier("orderProcessorConnectionFactory")
            ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);

        return factory;
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
