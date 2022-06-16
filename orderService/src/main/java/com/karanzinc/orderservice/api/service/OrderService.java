package com.karanzinc.orderservice.api.service;

import com.karanzinc.orderservice.api.common.Payment;
import com.karanzinc.orderservice.api.common.TransactionRequest;
import com.karanzinc.orderservice.api.common.TransactionResponse;
import com.karanzinc.orderservice.api.entity.Order;
import com.karanzinc.orderservice.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;


@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private RestTemplate template;

    public OrderService(Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public TransactionResponse saveOrder(TransactionRequest transactionRequest) {
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("payment");
        String responseMessage = "";
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        //rest api call
       Supplier<Payment> paymentSupplier = () -> template.postForObject(
                "http://PAYMENT-SERVICE/payment/doPayment",
                payment,
                Payment.class);
        /*
        Payment paymentResponse = template.postForObject(
                "http://PAYMENT-SERVICE/payment/doPayment",
                payment,
                Payment.class);
         */
        Payment paymentResponse = circuitBreaker.run(paymentSupplier, throwable -> handleErrorCase());
//        responseMessage = paymentResponse.getPaymentStatus().equals("success") ?
//                "Payment processing successful and order placed." :
//                "There's a failure in the payment api, order added to cart";
        repository.save(order);
        return new TransactionResponse(order, paymentResponse.getTransactionId(), paymentResponse.getAmount(), responseMessage );
    }

    private Payment handleErrorCase() {
        return null;
    }
}
