package com.springboot.activemq.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.activemq.messaging.dto.OrderRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderProducer {

    private final JmsTemplate orderProducerJmsTemplate;
    private final ObjectMapper objectMapper;

    public void produceMessages(OrderRequest order, long ttl) throws JsonProcessingException {

        if(ttl > 0)
         orderProducerJmsTemplate.setTimeToLive(ttl);

        orderProducerJmsTemplate.convertAndSend(
                "orders.in",
                objectMapper.writeValueAsString(order)
        );

        log.info("Order {} sent in order.in queue", order);
    }

}
