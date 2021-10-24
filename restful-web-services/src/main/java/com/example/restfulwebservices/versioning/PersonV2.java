package com.example.restfulwebservices.versioning;

public class PersonV2 {
    private String name;

    public PersonV2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
