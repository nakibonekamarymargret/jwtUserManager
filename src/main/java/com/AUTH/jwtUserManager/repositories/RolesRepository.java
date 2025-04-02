package com.AUTH.jwtUserManager.repositories;


import com.AUTH.jwtUserManager.models.Roles;
import com.AUTH.jwtUserManager.models.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RolesEnum name);
}

