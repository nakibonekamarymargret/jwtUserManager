package com.AUTH.jwtUserManager.services;

import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AuthenticationManager authManager;
    private BCryptPasswordEncoder encoder =new  BCryptPasswordEncoder(12);
private final JwtService jwtService;
    private final UserRepository userRepo;

    public UserService(AuthenticationManager authManager, JwtService jwtService, UserRepository userRepo) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String verify(Users user) {
       Authentication authentication= authManager.authenticate(
               new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
       if(authentication.isAuthenticated()){
           return jwtService.generateToken(user.getUsername());
       }
       return "User not authenticated";
    }

}
