package com.ucv.codetech.repository;

import com.ucv.codetech.model.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AppUserRepositoryGateway {

    private final AppUserRepository appUserRepository;

    public Optional<AppUser> findByUsername(String name) {
        return appUserRepository.findByUsername(name);
    }

    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }
}
