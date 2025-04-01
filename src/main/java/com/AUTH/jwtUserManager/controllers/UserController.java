package com.AUTH.jwtUserManager.controllers;

import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public List<Users> getAllUsers(){
        return userService.getAllUsers();
    }
}
