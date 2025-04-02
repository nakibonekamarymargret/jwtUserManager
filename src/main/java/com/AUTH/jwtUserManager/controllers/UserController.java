package com.AUTH.jwtUserManager.controllers;

import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.models.Roles;
import com.AUTH.jwtUserManager.models.RolesEnum;
import com.AUTH.jwtUserManager.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // Get all users (only accessible to ADMIN)
    @GetMapping("/all")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }
    // Get only users with role "USER" (only ADMIN can see this)
    @GetMapping("/only-users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> getOnlyUsers() {
        List<Users> allUsers = userService.getAllUsers();
        List<Users> usersList = new ArrayList<>();

        for (Users user : allUsers) {
            for (Roles role : user.getRoles()) {
                if (role.getName() == RolesEnum.ROLE_USER) {
                    usersList.add(user);
                    break;  // Stop checking roles once ROLE_USER is found
                }
            }
        }
        return usersList;
    }

    // Get only users with role "ADMIN" (only ADMIN can access)
    @GetMapping("/only-admins")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> getOnlyAdmins() {
        List<Users> allUsers = userService.getAllUsers();
        List<Users> adminsList = new ArrayList<>();

        for (Users user : allUsers) {
            for (Roles role : user.getRoles()) {
                if (role.getName() == RolesEnum.ROLE_ADMIN) {
                    adminsList.add(user);
                    break;  // Stop checking roles once ROLE_ADMIN is found
                }
            }
        }
        return adminsList;
    }
    // Regular user access (only USER or ADMIN can access)
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "Welcome User!";
    }
    // Admin access only
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Hello Admin!";
    }
}
