package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.QuestionDto;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.*;
import com.ucv.codetech.service.QuizService;
import com.ucv.codetech.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Facade
@AllArgsConstructor
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
        return question.getId();
    }

    public DisplayQuizDto getQuiz(Long id, String username) {
        Quiz quiz = quizService.findById(id);
        Student student = userService.getStudent(username);
        Course course = quiz.getCourse();
        if(student.containsCertificationForCourse(course)) {
            throw new AppException("The student " + username + " cannot obtain certification for course " + course.getName()
                    + " because he already has a certification for it", HttpStatus.BAD_REQUEST);
        }
        return quizConverter.entityToDisplayQuizDto(quiz);
    }

    @Transactional
    public void deleteQuiz(Long id) {
        quizService.deleteQuiz(id);
    }

    @Transactional
    public Long completeQuiz(Long id, String name) {
        Student student = userService.getStudent(name);
        Course course = quizService.findById(id).getCourse();
        if (!course.containsStudent(student)) { //to obtain a certification, a student must enroll in a course first
            throw new AppException("The student " + name + " cannot obtain certification for course " + course.getName()
                    + " because he is not enrolled in this course", HttpStatus.BAD_REQUEST);
        }
        if (student.containsCertificationForCourse(course)) {
            throw new AppException("The student " + name + " cannot obtain certification for course " + course.getName()
                    + " because he already has a certification for it", HttpStatus.BAD_REQUEST);
        }
        Certification certification = new Certification();
        certification.setCourse(course);
        student.addCertification(certification);
        userService.saveStudent(student);
        return certification.getId();
    }
}
