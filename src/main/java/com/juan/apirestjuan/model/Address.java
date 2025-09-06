package com.juan.apirestjuan.model;

import java.util.*;

public class Address {
    private Long id;
    private String name;
    private String street;
    private String country_code;

    public Address() {}

    public  Address(Long id, String name, String street, String country_code) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.country_code = country_code;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCountry() { return country_code; }
    public void setCountry(String country) { this.country_code = country; }

}