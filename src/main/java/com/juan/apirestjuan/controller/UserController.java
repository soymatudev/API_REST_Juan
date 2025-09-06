package com.juan.apirestjuan.controller;

import com.juan.apirestjuan.model.User;
import com.juan.apirestjuan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*@GetMapping
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws Exception{
        return userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                ?  ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<User> getUsersBySorting(
            @RequestParam(required = false, name = "sortedBy") String sortedBy,
            @RequestParam(required = false, name = "filter") String filter) throws Exception  {
        if(sortedBy != null) return userService.getUsersBySorting(sortedBy);
        if(filter != null) return userService.getUsersByFilter(filter);
        return userService.getUsersBySorting(sortedBy);
    }
}