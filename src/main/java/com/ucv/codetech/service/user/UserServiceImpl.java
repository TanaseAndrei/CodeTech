package com.ucv.codetech.service.user;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.model.Student;
import com.ucv.codetech.repository.AppUserRepository;
import com.ucv.codetech.repository.InstructorRepository;
import com.ucv.codetech.repository.StudentRepository;
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

    private final AppUserRepository appUserRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("The user with name " + username + " does not exist", HttpStatus.NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), getAppUserAuthority(appUser.getRole()));
    }

    @Override
    public AppUser getAppUser(String name) {
        return appUserRepository.findByUsername(name)
                .orElseThrow(() -> new AppException("The user with the name " + name + " does not exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor getInstructor(String name) {
        return instructorRepository.findByUsername(name).orElseThrow(() -> new AppException("The instructor with the name " + name + " has not been found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(String name) {
        return studentRepository.findByUsername(name).orElseThrow(() -> new AppException("The student with the name " + name + " has not been found", HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean userExistsByName(String name) {
        return appUserRepository.existsByUsername(name);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }

    private Collection<? extends GrantedAuthority> getAppUserAuthority(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
