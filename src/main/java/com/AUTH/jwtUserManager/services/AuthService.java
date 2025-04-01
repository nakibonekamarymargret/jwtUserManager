package com.AUTH.jwtUserManager.services;


import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.repositories.UserRepository;
import com.AUTH.jwtUserManager.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String username, String password, String profilePic) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);
        Users user = new Users(username, hashedPassword, profilePic);
        userRepository.save(user);

        return JwtUtil.generateToken(username);
    }

    public String login(String username, String password) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return JwtUtil.generateToken(username);
    }
}
