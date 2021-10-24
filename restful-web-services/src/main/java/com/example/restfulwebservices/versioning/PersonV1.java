package com.example.restfulwebservices.versioning;

public class PersonV1 {
    private Name name;

    public PersonV1(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}


