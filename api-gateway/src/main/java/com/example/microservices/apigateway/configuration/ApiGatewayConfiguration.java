package com.example.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    /**
     * This is to build custom Routes like
     * http://localhost:8765/currency-exchange/from/USD/to/INR
     *
     * http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        Function<PredicateSpec, Buildable<Route>> routeFunction
                = p -> p.path("/get")
                .filters(gatewayFilterSpec -> gatewayFilterSpec
                        .addRequestHeader("MyHeader", "MyURI") // This can be authentication headers
                        .addRequestParameter("MyParam", "MyParamValue")
                )

                .uri("http://httpbin.org:80"); //This can be url to microservice

        Function<PredicateSpec, Buildable<Route>> currencyExchangeRoute
                = p -> p.path("/currency-exchange/**")
                .uri("lb://currency-exchange"); //This is name registered on the eureka server

        Function<PredicateSpec, Buildable<Route>> currencyConversionRoute
                = p -> p.path("/currency-conversion/**")
                .uri("lb://currency-conversion"); //This is name registered on the eureka server


        return builder
                .routes()
                .route(routeFunction)
                .route(currencyExchangeRoute)
                .route(currencyConversionRoute)

                .build();
    }
}
