package com.ucv.codetech.facade;

import com.ucv.codetech.CodeTechApplication.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.QuestionDto;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.*;
import com.ucv.codetech.service.QuizService;
import com.ucv.codetech.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Facade
@AllArgsConstructor
@Slf4j
public class QuizFacade {

    private final QuizService quizService;
    private final QuizConverter quizConverter;
    private final UserService userService;

    @Transactional
    public Long addQuestion(Long id, QuestionDto questionDto) {
        Quiz quiz = quizService.findById(id);
        Question question = quizConverter.questionDtoToEntity(questionDto);
        quiz.addQuestion(question);
        quizService.saveOrUpdate(quiz);
        log.info("Added question to quiz {}", id);
        return question.getId();
    }

    public DisplayQuizDto getQuiz(Long id, String username) {
        log.info("Retrieving quiz {} for student {}", id, username);
        Quiz quiz = quizService.findById(id);
        Student student = userService.getStudent(username);
        Course course = quiz.getCourse();
        if (student.containsCertificationForCourse(course)) {
            log.warn("The student {} did not complete the course {} in order to take the quiz", username, course.getName());
            throw new AppException("The student " + username + " cannot take the quiz for course " + course.getName()
                    + " because he finished it", HttpStatus.BAD_REQUEST);
        }
        return quizConverter.entityToDisplayQuizDto(quiz);
    }

    @Transactional
    public void deleteQuiz(Long id) {
        quizService.deleteQuiz(id);
        log.info("Deleted quiz with id {}", id);
    }

    @Transactional
    public Long completeQuiz(Long courseId, String username) {
        log.info("Completing quiz of course {}", courseId);
        Student student = userService.getStudent(username);
        Course course = quizService.findById(courseId).getCourse();
        if (!course.containsStudent(student)) {
            log.warn("Cannot complete quiz because the student {} is not enrolled in course {}", username, courseId);
            throw new AppException("The student " + username + " cannot obtain certification for course " + course.getName()
                    + " because he is not enrolled in this course", HttpStatus.BAD_REQUEST);
        }
        if (student.containsCertificationForCourse(course)) {
            log.warn("Student {} already has a certification for course {}", username, courseId);
            throw new AppException("The student " + username + " cannot obtain certification for course " + course.getName()
                    + " because he already has a certification for it", HttpStatus.BAD_REQUEST);
        }
        Certification certification = new Certification();
        certification.setCourse(course);
        student.addCertification(certification);
        userService.saveStudent(student);
        log.info("Added certification to student {}", username);
        return certification.getId();
    }
}
