package com.juan.apirestjuan.service;

import com.juan.apirestjuan.model.Address;
import com.juan.apirestjuan.model.User;
import com.juan.apirestjuan.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        userService = new UserService();
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUserById() {
        User firstUser = userService.getAllUsers().get(0);
        Optional<User> userOpt = userService.getUserById(firstUser.getId());
        assertTrue(userOpt.isPresent());
        assertEquals(firstUser.getName(), userOpt.get().getName());
    }

    @Test
    public void testCreateUser() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(1L, "Juan", "juan", "AARR990101XXX", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser.getId());
        assertEquals("Juan", createdUser.getName());
        assertEquals(4, userService.getAllUsers().size());
    }

    @Test
    public void testUpdateUser() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(1L, "Juan", "juan", "GAFA980602F82", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());
        User userUpdated = new User(2L, "Ana", "ana", "AARR990101XXX", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());

        Optional<User> updated = userService.updateUser(user.getId(), userUpdated);
        assertTrue(updated.isPresent());
        assertEquals("Ana", updated.get().getName());
        assertEquals("myemailtest@test.com", updated.get().getEmail());
    }

    @Test
    public void testDeleteUser() {
        User existingUser = userService.getAllUsers().get(0);
        boolean deleted = userService.deleteUser(existingUser.getId());
        assertTrue(deleted);
        assertFalse(userService.getUserById(existingUser.getId()).isPresent());
    }

    @Test
    public void testGetUsersBySorting() {
        List<User> sortedByName = userService.getUsersBySorting("name");
        assertNotNull(sortedByName);
        assertEquals(userService.getAllUsers().size(), sortedByName.size());
    }

    @Test
    public void testGetUsersByFilter() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(1L, "Juan", "juan", "AARR990101XXX", "myemailtest@test.com", "+1212555123", addresses, TimeUtil.ValidCreated_at());

        userService.createUser(user);
        List<User> filtered = userService.getUsersByFilter("name co Juan");
        assertFalse(filtered.isEmpty());
        assertEquals("Juan", filtered.get(0).getName());
    }
}
