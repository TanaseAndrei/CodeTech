package com.ucv.codetech.facade;

import com.ucv.codetech.CodeTechApplication.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.AppUserDto;
import com.ucv.codetech.controller.model.input.RoleDto;
import com.ucv.codetech.facade.converter.AppUserConverter;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.model.Student;
import com.ucv.codetech.service.EmailRestClientService;
import com.ucv.codetech.service.UserService;
import com.ucv.codetech.service.model.RegisterEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Facade
@AllArgsConstructor
@Slf4j
public class UserFacade {

    private final UserService userService;
    private final EmailRestClientService emailRestClientService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserConverter appUserConverter;

    @Transactional
    public Long registerUser(AppUserDto appUserDto) {
        log.info("Registering new {} with name {}", appUserDto.getRoleDto(), appUserDto.getUsername());
        validate(appUserDto.getUsername(), appUserDto.getEmail());
        Long userId = saveUser(appUserDto);
        emailRestClientService.sendEmail(getRegisterEmail(appUserDto));
        log.info("Registered the new {} with name {}", appUserDto.getRoleDto(), appUserDto.getUsername());
        return userId;
    }

    public AppUser getAppUser(String username) {
        return userService.getAppUser(username);
    }

    private RegisterEmail getRegisterEmail(AppUserDto appUserDto) {
        return new RegisterEmail(appUserDto.getUsername(), appUserDto.getEmail(),
                Role.getByName(appUserDto.getRoleDto().name()));
    }

    private Long saveUser(AppUserDto appUserDto) {
        RoleDto roleDto = appUserDto.getRoleDto();
        switch(roleDto) {
            case INSTRUCTOR:
                return saveInstructor(appUserDto);
            case STUDENT:
                return saveStudent(appUserDto);
            default:
                throw new AppException("Unexpected value: " + roleDto, HttpStatus.BAD_REQUEST);
        }
    }

    private Long saveStudent(AppUserDto appUserDto) {
        Student student = appUserConverter.dtoToStudent(appUserDto);
        student.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        return userService.saveStudent(student).getId();
    }

    private Long saveInstructor(AppUserDto appUserDto) {
        Instructor instructor = appUserConverter.dtoToInstructor(appUserDto);
        instructor.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        return userService.saveInstructor(instructor).getId();
    }

    private void validate(String username, String email) {
        validateUserName(username);
        validateEmail(email);
    }

    private void validateUserName(String username) {
        if (userService.userExistsByUsername(username)) {
            log.warn("Application user with name {} already exists", username);
            throw new AppException("The user with the name " + username + " already exists", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateEmail(String email) {
        if (userService.userExistsByEmail(email)) {
            log.warn("Application user with email {} already exists", email);
            throw new AppException("The user with the email " + email + " already exists", HttpStatus.BAD_REQUEST);
        }
    }
}
