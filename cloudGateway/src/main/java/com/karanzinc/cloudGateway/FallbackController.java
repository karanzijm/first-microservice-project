package com.karanzinc.cloudGateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/orderFallBack")
    public String orderServiceFallBack() {
        return "Order service is taking long to respond or is down. Please try again later";
        }

        @RequestMapping("paymentFallBack")
    public String paymentServiceFallBack() {
        return "Payment service is taking long to respond or is down. Please try again later";
        }
}
