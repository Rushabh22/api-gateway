package com.example.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "localhost:8000") //Name of the service and host, Port details are required
@FeignClient(name = "currency-exchange")  //With Eureka
public interface CurrencyExchangeProxy { //Proxy should be interface

    @GetMapping("currency-exchange/from/{fromCcy}/to/{toCcy}")  //Just Copy paste method definition from the currency-exchange service
    public CurrencyConversion retreiveExchangeValue(@PathVariable String fromCcy, @PathVariable String toCcy);

}
