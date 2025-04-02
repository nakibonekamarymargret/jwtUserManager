package com.AUTH.jwtUserManager.services;

import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.repositories.UserRepository;
import com.AUTH.jwtUserManager.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> register(String username, String password, String profilePic,String email) {
        Optional<Users> existingUser = userRepository.findByUsername(username);
        Map<String, Object> response = new HashMap<>();

        if (existingUser.isPresent()) {
            response.put("success", false);
            response.put("message", "Username already exists");
            response.put("data", null);
            return response;
        }

        String hashedPassword = passwordEncoder.encode(password);
        Users user = new Users(username, hashedPassword, profilePic,email);
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "User registered successfully");

        // Put user details in "data" instead of the token
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", user.getUsername());
        userData.put("password", password);
        userData.put("profilePic", user.getProfilePicUrl());

        response.put("data", userData);
        return response;
    }

    public Map<String, Object> login(String username, String password) {
        Users user = userRepository.findByUsername(username).orElse(null);
        Map<String, Object> response = new HashMap<>();

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            response.put("data", null);
            return response;
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("success", false);
            response.put("message", "Incorrect password");
            response.put("data", null);
            return response;
        }

        response.put("success", true);
        response.put("message", "Login successful");

        // Put user details in "data"
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", user.getUsername());
        userData.put("profilePic", user.getProfilePicUrl());

        response.put("data", userData);
        return response;
    }
}
