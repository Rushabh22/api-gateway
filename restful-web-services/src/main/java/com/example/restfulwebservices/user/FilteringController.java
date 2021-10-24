package com.example.restfulwebservices.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {


    @GetMapping("/filtering")
    public SomeBean retreiveSomeBean(){
        return new SomeBean("value1","value2","value3");
    }


    @GetMapping("/filtering-list")
    public List<SomeBean> retreiveSomeBeans(){
        return Arrays.asList(new SomeBean("value1","value2","value3"), new SomeBean("value11","value22","value33"));
    }


}
