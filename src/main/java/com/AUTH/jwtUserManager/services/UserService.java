package com.AUTH.jwtUserManager.services;

import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

//    Creating a user
    public Users createUser(Users user){
        return userRepository.save(user);
    }
//    Retrieving users
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }
//    Updating a user
    public void updateUsers(Long id, Users userDetails){
        if (userDetails.getUsername() != null) {
            userRepository.updateUsers(userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getProfilePicUrl(), id);
        }

    }
}
