package com.ucv.codetech.service.user;

import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.model.Student;

public interface UserService {
    Instructor saveInstructor(Instructor instructor);
    Instructor getInstructor(String name);
    Student saveStudent(Student student);
    Student getStudent(String name);
    Role saveRole(Role role);
    void addRoleToUser(String username, String role);
}
