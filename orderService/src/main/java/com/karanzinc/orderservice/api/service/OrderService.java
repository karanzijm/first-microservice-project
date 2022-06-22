package com.karanzinc.orderservice.api.service;

import com.karanzinc.orderservice.api.common.Payment;
import com.karanzinc.orderservice.api.common.TransactionRequest;
import com.karanzinc.orderservice.api.common.TransactionResponse;
import com.karanzinc.orderservice.api.entity.Order;
import com.karanzinc.orderservice.api.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.function.Supplier;


@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private RestTemplate template;

//    public OrderService(Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
//        this.circuitBreakerFactory = circuitBreakerFactory;
//    }

    int  count = 1;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest) {
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("payment");
        System.out.println("Retry method called "+ count++  +" times at "+  new Date());

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
      /*  Payment paymentResponse = template.postForObject(
                "http://PAYMENT-SERVICE/payment/doPayment",
                payment,
                Payment.class);*/
        Payment paymentResponse = circuitBreaker.run(paymentSupplier, throwable -> handleErrorCase());
        responseMessage = paymentResponse.getPaymentStatus().equals("success") ?
                "Payment processing successful and order placed." :
                "There's a failure in the payment api, order added to cart";
        repository.save(order);
        return new TransactionResponse(order, paymentResponse.getTransactionId(), paymentResponse.getAmount(), responseMessage );
    }

    private Payment handleErrorCase() {
        return new Payment(99,"Failed","99",99,200000);
    }

}
