package com.springboot.activemq.messaging.controller;



import com.springboot.activemq.messaging.dto.OrderRequest;
import com.springboot.activemq.messaging.producer.OrderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@SuppressWarnings("NullableProblems")
public class OrderController {

    private final OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> send(
            @RequestBody OrderRequest order)
            throws Exception {

        producer.send(order, 0);

        return ResponseEntity.ok(
                "Order sent to Artemis MQ"
        );
    }

    @PostMapping("/expire")
    public ResponseEntity<String> sendWithExpiration(
            @RequestBody OrderRequest order)
            throws Exception {

        /*
         * Message expires after 5 seconds.
         */

        producer.send(order, 5000);

        return ResponseEntity.ok(
                "Order sent with TTL = 5 seconds"
        );
    }
}
