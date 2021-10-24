package com.example.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class CircuitBreakerController {

    @GetMapping("/sample-api")
//    @Retry(name="sample-api", fallbackMethod = "hardCodedResponse") //If there is any failure in method then it retries thrice. you can configure retries in app.props
    @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    /*
    It is used to switch to fallBackMethod in case if the API throws an exception
     Use following command in unix to hit multiple requests
     watch -n 0.5 curl http://localhost:8000/sample-api

     MAC:
     while :; do clear; curl http://localhost:8000/sample-api; sleep 0.5; done
     */

    public String sampleApi() {
        log.info("Sample API call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return forEntity.getBody();
    }


    @RateLimiter(name = "default")
    /*
    10s -> 1000 calls to the sample api

    while :; do clear; curl http://localhost:8000/rate-limit; sleep 1; done
     */

    @Bulkhead(name = "rate-limit")
    @GetMapping("/rate-limit")
    public String sampleApi2() {
        log.info("Rate Limit call received");
        return "Rate limit called";
    }

    //This is the fallbackMethod from the above API
    public String hardCodedResponse(Exception ex) {
        return "Fallback-response -> " + ex.getMessage();
    }
}
