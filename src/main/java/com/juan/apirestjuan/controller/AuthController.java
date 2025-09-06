package com.juan.apirestjuan.controller;

import com.juan.apirestjuan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String name = credentials.get("name");
        String password = credentials.get("password");

        return userService.login(name, password)
                .map(user -> ResponseEntity.ok(Map.of(
                    "message", "Login exitoso",
                        "user", user
                )))
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Credentiales Invalidas")));
    }
}