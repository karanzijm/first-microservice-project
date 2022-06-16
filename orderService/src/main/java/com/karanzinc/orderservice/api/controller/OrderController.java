package com.karanzinc.orderservice.api.controller;

import com.karanzinc.orderservice.api.common.Payment;
import com.karanzinc.orderservice.api.common.TransactionRequest;
import com.karanzinc.orderservice.api.common.TransactionResponse;
import com.karanzinc.orderservice.api.entity.Order;
import com.karanzinc.orderservice.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "order")
public class OrderController {
    private final OrderService service;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory = null;

    @Autowired
    public OrderController(OrderService orderService){ this.service = orderService; }

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping(value = "/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest) {
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("")
        return service.saveOrder(transactionRequest);
    }
}
