package com.ucv.codetech.service.user;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.model.Student;
import com.ucv.codetech.repository.AppUserRepository;
import com.ucv.codetech.repository.InstructorRepository;
import com.ucv.codetech.repository.RoleRepository;
import com.ucv.codetech.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final RoleRepository roleRepository;

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
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("The user with name " + username + " has not been found", HttpStatus.NOT_FOUND));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException("The role with name " + roleName + " has not been found", HttpStatus.NOT_FOUND));
        appUser.addRole(role);
    }
}
