package com.springboot.activemq.messaging.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long id;

    private String customer;

    private double amount;

    private boolean fail;

}
