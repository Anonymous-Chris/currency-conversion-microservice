package com.chris.springboot.microservices.currencyconversion.controller;

import com.chris.springboot.microservices.currencyconversion.model.CurrencyConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionControllerUsingFeignProxy {
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
    private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    public CurrencyConversionControllerUsingFeignProxy(CurrencyExchangeServiceProxy currencyExchangeServiceProxy) {
        this.currencyExchangeServiceProxy = currencyExchangeServiceProxy;
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion responseEntity(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity){
        CurrencyConversion response = currencyExchangeServiceProxy.retrieveExchangeValue(from,to);
        logger.info("{}", response);
        return new CurrencyConversion(response.getId(),  from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
    }
}
