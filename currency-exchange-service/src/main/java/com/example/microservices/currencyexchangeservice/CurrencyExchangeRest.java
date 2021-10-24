package com.example.microservices.currencyexchangeservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
public class CurrencyExchangeRest {

    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    private Environment environment;

    @GetMapping("currency-exchange/from/{fromCcy}/to/{toCcy}")
    public CurrencyExchange retreiveExchangeValue(@PathVariable String fromCcy, @PathVariable String toCcy){
        log.info("retreiveExchangeValue called with {} to {}", fromCcy, toCcy);
        String port = environment.getProperty("local.server.port");
//        CurrencyExchange currencyExchange = new CurrencyExchange(1001l, fromCcy, toCcy, new BigDecimal(65.5), port);
        CurrencyExchange currencyExchange = repository.findByFromAndTo(fromCcy, toCcy);
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }

}
