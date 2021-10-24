package com.example.restfulwebservices.hello.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping(path = "/hello-bean")
    public HellowWorldBean helloWorldBean(){
        return new HellowWorldBean("Hello world");
    }

    @GetMapping(path = "/hello-bean-i18n")
    public String helloWorldBeanI18n(@RequestHeader(name = "Accept-language", required = false) Locale locale){
        return messageSource.getMessage("good.morning.message",null, "Default message", locale);
    }
}
