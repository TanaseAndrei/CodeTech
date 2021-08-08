package com.ucv.codetech.service.user;

import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Student;

public interface UserService {
    AppUser getAppUser(String name);

    Instructor saveInstructor(Instructor instructor);

    Instructor getInstructor(String name);

    Student saveStudent(Student student);

    Student getStudent(String name);

    boolean userExistsByName(String name);

    boolean userExistsByEmail(String email);
}
