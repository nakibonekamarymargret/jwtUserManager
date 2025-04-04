package com.AUTH.jwtUserManager.controllers;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController

public class HelloController {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "Hi "+request.getSession().getId();
    }
//    @GetMapping("/")
//    public String greet(Principal principal) {
//        return "Hi " + principal.getName();  // returns the logged-in username
//    }
}
