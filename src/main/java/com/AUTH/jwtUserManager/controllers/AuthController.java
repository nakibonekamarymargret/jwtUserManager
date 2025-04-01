package com.AUTH.jwtUserManager.controllers;

import com.AUTH.jwtUserManager.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String profilePic) {
        return authService.register(username, password, profilePic);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }
}
