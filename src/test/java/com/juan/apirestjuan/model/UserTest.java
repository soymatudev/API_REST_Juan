package com.juan.apirestjuan.model;

import com.juan.apirestjuan.util.TimeUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


class UserTest {

    @Test
    public void testUserConstructorAndGetters() {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(1L, "Juan", "juan", "AARR990101XXX", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());

        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("juan", user.getPassword());
        assertEquals("AARR990101XXX", user.getTax_id());
        assertEquals("myemailtest@test.com", user.getEmail());
        assertEquals("+5211223344", user.getPhone());
        assertEquals(1, user.getAddress().size());
        assertNotNull(user.getCreated_at());
    }

    @Test
    public void testUserSetters() {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(1L, "Juan", "juan", "AARR990101XXX", "myemailtest@test.com", "+1212555123", addresses, TimeUtil.ValidCreated_at());

        user.setId(2L);
        user.setName("Maria");
        user.setPassword("pass123");
        user.setTax_id("MARA990101ABC");
        user.setEmail("maria@test.com");
        user.setPhone("+5211223344");

        user.setCreated_at(TimeUtil.ValidCreated_at());

        assertEquals(2L, user.getId());
        assertEquals("Maria", user.getName());
        assertEquals("pass123", user.getPassword());
        assertEquals("MARA990101ABC", user.getTax_id());
        assertEquals("maria@test.com", user.getEmail());
        assertEquals("+5211223344", user.getPhone());
        assertEquals(1, user.getAddress().size());
        assertNotNull(user.getCreated_at());
    }
}
