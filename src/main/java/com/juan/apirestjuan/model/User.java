package com.juan.apirestjuan.model;

import java.util.*;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String tax_id;
    private String phone;
    private List<Address> address;
    private String created_at;

    public User() {}

    public User(Long id, String name, String password, String tax_id, String email, String phone, List<Address> address, String created_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tax_id = tax_id;
        this.phone  = phone;
        this.address = address;
        this.created_at = created_at;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTax_id() { return tax_id; }
    public void setTax_id(String tax_id) { this.tax_id = tax_id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<Address> getAddress() { return address; }
    public void setAddress(List<Address> address) { this.address = address; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }

}