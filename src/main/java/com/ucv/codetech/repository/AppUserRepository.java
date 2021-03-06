package com.ucv.codetech.repository;

import com.ucv.codetech.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String name);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
