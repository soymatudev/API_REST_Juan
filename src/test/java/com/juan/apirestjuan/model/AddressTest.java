package com.juan.apirestjuan.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    public void testAddressConstructorAndGetters() {
        Address address = new Address(1L, "Home", "Street Siempre Viva 123", "UK");

        assertEquals(1L, address.getId());
        assertEquals("Home", address.getName());
        assertEquals("Street Siempre Viva 123", address.getStreet());
        assertEquals("UK", address.getCountry());
    }

    @Test
    public void testAddressSetters() {
        Address address = new Address();

        address.setId(2L);
        address.setName("Work");
        address.setStreet("Office Street 456");
        address.setCountry("MX");

        assertEquals(2L, address.getId());
        assertEquals("Work", address.getName());
        assertEquals("Office Street 456", address.getStreet());
        assertEquals("MX", address.getCountry());
    }
}
