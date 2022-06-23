package com.karanzinc.orderservice;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;

import java.time.Duration;

@SpringBootApplication
@EnableEurekaClient
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

  /*  @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalConfiguration() {
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().
                timeoutDuration(Duration.ofSeconds(4)).
                build();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.
                custom().
                failureRateThreshold(80).
                waitDurationInOpenState(Duration.ofMillis(1000)).
                slidingWindowSize(2).
                build();

        return factory ->factory.configureDefault(id -> new Resilience4JConfigBuilder(id).
                timeLimiterConfig(timeLimiterConfig).circuitBreakerConfig(circuitBreakerConfig).build());

    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> specificCustomConfiguration1() {

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .build();

        return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig).build(), "payment");
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> specificCustomConfiguration2() {

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .build();

        return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
                        .timeLimiterConfig(timeLimiterConfig).build(),
                "circuitBreaker1", "circuitBreaker2", "circuitBreaker3", "payment");
    }
*/



}
