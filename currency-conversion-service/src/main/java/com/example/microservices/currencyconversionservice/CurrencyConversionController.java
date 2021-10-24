package com.example.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;



//    @GetMapping("currency-conversion/from/{fromCcy}/to/{toCcy}/quantity/{quantity}")
    public CurrencyConversion doCurrencyConversion(@PathVariable String fromCcy, @PathVariable String toCcy, @PathVariable Integer quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("fromCcy", fromCcy);
        uriVariables.put("toCcy", toCcy);
        String port = environment.getProperty("local.server.port");
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{fromCcy}/to/{toCcy}"
                //http://localhost:8000/currency-exchange/from/USD/to/INR
                , CurrencyConversion.class, uriVariables);
//        CurrencyConversion currencyConversion = new CurrencyConversion(1001l, fromCcy, toCcy, BigDecimal.valueOf(65.0), quantity, BigDecimal.valueOf(650), port);
        CurrencyConversion currencyConversion = responseEntity.getBody();
        if(currencyConversion == null){
            throw new IllegalStateException("Unable to do currency conversion");
        }
        currencyConversion.setEnvironment(port);
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(new BigDecimal(quantity)));
        return responseEntity.getBody();
    }


    @GetMapping("currency-conversion/from/{fromCcy}/to/{toCcy}/quantity/{quantity}")
    public CurrencyConversion doCurrencyConversionWithFeign(@PathVariable String fromCcy, @PathVariable String toCcy, @PathVariable Integer quantity) {
//        String port = environment.getProperty("local.server.port");
        CurrencyConversion currencyConversion = currencyExchangeProxy.retreiveExchangeValue(fromCcy, toCcy);
        currencyConversion.setEnvironment(currencyConversion.getEnvironment()+"_Feign");
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(new BigDecimal(quantity)));
        return currencyConversion;
    }
}
