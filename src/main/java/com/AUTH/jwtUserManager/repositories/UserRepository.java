package com.AUTH.jwtUserManager.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.AUTH.jwtUserManager.models.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    //    Find user by username
    Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void deleteUserById(Long userId);

    @Modifying
    @Transactional//used when we are working with multiple fields
    @Query("UPDATE Users u SET u.username = COALESCE(?1, u.username), " +
            "u.password = COALESCE(?2, u.password)," +
            " u.profilePicUrl = COALESCE(?3, u.profilePicUrl) WHERE u.id = ?4")
    void updateUsers(String username, String password, String profilePictureUrl, Long id);


}

