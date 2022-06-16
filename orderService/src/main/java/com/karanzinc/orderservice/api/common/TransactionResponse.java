package com.karanzinc.orderservice.api.common;

import com.karanzinc.orderservice.api.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Order order;
    private String transationId;
    private double amount;
    private String message;
}
