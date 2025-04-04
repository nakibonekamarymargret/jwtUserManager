package com.AUTH.jwtUserManager.controllers;

import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return userService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return userService.verify(user);
    }
}
