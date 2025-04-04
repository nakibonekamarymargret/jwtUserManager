package com.AUTH.jwtUserManager.repositories;

import com.AUTH.jwtUserManager.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username); // Changed method name
    Boolean existsByUsername(String username);
}
