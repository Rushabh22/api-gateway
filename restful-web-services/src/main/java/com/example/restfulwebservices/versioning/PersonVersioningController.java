package com.example.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    //WITH URLS, CAN EXECUTE IN BROWSERS, EASY TO CACHE BUT, POLLUTES THE URI SPACE
    @GetMapping("/v1/person")
    public PersonV1 personV1(){
        return new PersonV1(new Name("Bob"," Charlie"));
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2(){
        return new PersonV2("Person v2");
    }


    //WITH PARAMS

    @GetMapping(value="/person/param",params="version=1")
    public PersonV1 personV1Param(){
        return new PersonV1(new Name("Bob"," Charlie"));
    }

    @GetMapping(value = "/person/param",params = "version=2")
    public PersonV2 personV2Param(){
        return new PersonV2("Person v2");
    }

    //HARD TO GENERATE THE API DOCUMENTATION FROM CODE
    //URI IS ALWAYS CONSTANT, HARD TO RUN FROM BROWSERS, CACHING DIFFICULT(AFTER URI YOU WILL ALSO NEED TO CHECK THE HEADERS)
    //WITH HEADERS, MIME TYPE VERSIONING OR ACCEPT HEADER VERSIONING OR CONTENT-TYPE VERSIONING

    @GetMapping(value="/person/header",headers="X-API-VERSION=1")
    public PersonV1 personV1Header(){
        return new PersonV1(new Name("Bob"," Charlie"));
    }

    @GetMapping(value = "/person/header",headers = "X-API-VERSION=2")
    public PersonV2 personV2Header(){
        return new PersonV2("Person v2");
    }


    //WITH PRODUCERS

    @GetMapping(value="/person/produces",produces="application/mycompany-v1+json")
    public PersonV1 personV1Produces(){
        return new PersonV1(new Name("Bob"," Charlie"));
    }

    @GetMapping(value = "/person/produces",produces="application/mycompany-v2+json")
    public PersonV2 personV2Produces(){
        return new PersonV2("Person v2");
    }
}


