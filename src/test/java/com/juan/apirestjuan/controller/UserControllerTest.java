package com.juan.apirestjuan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juan.apirestjuan.model.Address;
import com.juan.apirestjuan.model.User;
import com.juan.apirestjuan.service.UserService;
import com.juan.apirestjuan.util.TimeUtil;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private Long idCounter = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // simulamos la capa de servicio

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsers() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(idCounter++, "Juan", "juan", "GAFA980602F82", "myemailtest@test.com", "+1212555123", addresses, TimeUtil.ValidCreated_at());

        Mockito.when(userService.getUsersBySorting(Mockito.anyString()))
                        .thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/users?sortedBy=name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[0].email").value("myemailtest@test.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(idCounter++, "Juan", "juan", "GAFA980602F82", "myemailtest@test.com", "+1212555123", addresses, TimeUtil.ValidCreated_at());

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.email").value("myemailtest@test.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(idCounter++, "Juan", "juan", "GAFA980602F82", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());
        User userUpdated = new User(idCounter++, "Ana", "ana", "GAFA980602F82", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userService.updateUser(Mockito.anyLong(), Mockito.any(User.class)))
                .thenReturn(Optional.of(userUpdated));

        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"))
                .andExpect(jsonPath("$.email").value("myemailtest@test.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(idCounter++, "Juan", "juan", "GAFA980602F82", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());
        User userUpdated = new User(idCounter++, "Ana", "ana", "GAFA980602F82", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(userUpdated);
        Mockito.when(userService.deleteUser(Mockito.anyLong()))
                .thenReturn(true);

        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is(204));
    }

    @Test
    public void testFilterUsersByEmail() throws Exception {
        List<Address> addresses = new ArrayList<>();;
        addresses.add(new Address(1L, "House", "Street Siempre viva 123", "UK"));
        User user = new User(idCounter++, "Juan", "juan", "GAFA980602F82", "myemailtest@test.com", "+5211223344", addresses, TimeUtil.ValidCreated_at());

        Mockito.when(userService.getUsersByFilter(Mockito.anyString()))
                .thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/users?filter=email+ew+test.com")
                        .param("keyValue", "email+eq+luis@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("myemailtest@test.com"));
    }
}
