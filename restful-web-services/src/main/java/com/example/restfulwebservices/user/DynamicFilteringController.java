package com.example.restfulwebservices.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class DynamicFilteringController {

//value1, value2
    @GetMapping("/dynamic/filtering")
    public MappingJacksonValue retreiveSomeBean(){
        DynamicBean dynamicBean = new DynamicBean("value1", "value2", "value3");
        return getMappingJacksonValue(dynamicBean, "field1","field2");
    }

    //value2, value3
    @GetMapping("/dynamic/filtering-list")
    public MappingJacksonValue retreiveSomeBeans(){
        List<DynamicBean> dynamicBeans = Arrays.asList(new DynamicBean("value1", "value2", "value3"), new DynamicBean("value11", "value22", "value33"));

        return getMappingJacksonValue(dynamicBeans, "field2","field3");
    }


    private MappingJacksonValue getMappingJacksonValue(Object dynamicBean, String... fields) {
        MappingJacksonValue mapping = new MappingJacksonValue(dynamicBean);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        mapping.setFilters(filters);
        return mapping;
    }


}
