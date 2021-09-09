package com.ucv.codetech.service;

import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Student;

public interface UserService {
    AppUser getAppUser(String username);

    Instructor saveInstructor(Instructor instructor);

    Instructor getInstructor(String username);

    Student saveStudent(Student student);

    Student getStudent(String username);

    boolean userExistsByUsername(String username);

    boolean userExistsByEmail(String email);
}
