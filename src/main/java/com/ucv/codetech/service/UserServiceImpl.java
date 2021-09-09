package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.model.Student;
import com.ucv.codetech.repository.AppUserRepositoryGateway;
import com.ucv.codetech.repository.InstructorRepositoryGateway;
import com.ucv.codetech.repository.StudentRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepositoryGateway appUserRepositoryGateway;
    private final StudentRepositoryGateway studentRepositoryGateway;
    private final InstructorRepositoryGateway instructorRepositoryGateway;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepositoryGateway.findByUsername(username)
                .orElseThrow(() -> new AppException(String.format("The user with the name %s does not exist", username), HttpStatus.NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), getAppUserAuthority(appUser.getRole()));
    }

    @Override
    public AppUser getAppUser(String username) {
        return appUserRepositoryGateway.findByUsername(username)
                .orElseThrow(() -> new AppException(String.format("The user with the name %s does not exist", username), HttpStatus.NOT_FOUND));
    }

    @Override
    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepositoryGateway.save(instructor);
    }

    @Override
    public Instructor getInstructor(String username) {
        return instructorRepositoryGateway.findByUsername(username).orElseThrow(() ->
                new AppException(String.format("The instructor with the username %s does not exit", username), HttpStatus.NOT_FOUND));
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepositoryGateway.save(student);
    }

    @Override
    public Student getStudent(String username) {
        return studentRepositoryGateway.findByUsername(username).orElseThrow(() ->
                new AppException(String.format("The student with the name %s does not exist", username), HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean userExistsByUsername(String username) {
        return appUserRepositoryGateway.existsByUsername(username);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return appUserRepositoryGateway.existsByEmail(email);
    }

    private Collection<? extends GrantedAuthority> getAppUserAuthority(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
