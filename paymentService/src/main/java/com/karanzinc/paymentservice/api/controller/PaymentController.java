package com.karanzinc.paymentservice.api.controller;

import com.karanzinc.paymentservice.api.entity.Payment;
import com.karanzinc.paymentservice.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping(path = "payment")
public class PaymentController {
    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService paymentService) { this.service = paymentService;}


    @PostMapping(value = "/doPayment")
    public Payment doPayment(@RequestBody Payment payment) {
        return service.doPayment(payment);
    }

    @GetMapping(value = "/{orderId}")
    public Payment findPaymentHistoryByOrderId(@PathVariable int orderId) {
        return service.findPaymentHistoryByOrderId(orderId);

    }

}
