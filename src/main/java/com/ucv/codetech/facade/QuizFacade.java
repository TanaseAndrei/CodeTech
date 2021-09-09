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

import java.util.List;
import java.util.Optional;

@Facade
@AllArgsConstructor
@Slf4j
public class QuizFacade {

    private final QuizService quizService;
    private final QuizConverter quizConverter;
    private final UserService userService;

    @Transactional
    public Long addQuestion(Long id, QuestionDto questionDto) {
        log.info("Adding question to quiz {}", id);
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
        Optional<EnrolledCourse> enrolledCourse = courseExistsInStudentCourses(student.getEnrolledCourses(), course.getId());
        validateGettingQuiz(username, student, course, enrolledCourse);
        return quizConverter.entityToDisplayQuizDto(quiz);
    }

    private void validateGettingQuiz(String username, Student student, Course course, Optional<EnrolledCourse> enrolledCourse) {
        if(!enrolledCourse.isPresent()) {
            log.warn("The student {} cannot take the quiz because he is not enrolled din the course {}", username, course.getName());
            throw new AppException(String.format("The student %s cannot take the quiz for course %s because he is not" +
                    " enrolled in the course", username, course.getName()), HttpStatus.BAD_REQUEST);
        }

        if(!enrolledCourse.get().isCourseCompleted()) {
           log.warn("The student {} cannot take the quiz because he did not finish the course {}", username, course.getName());
            throw new AppException(String.format("The student %s cannot take the quiz for course %s because" +
                    " he did not finish it", username, course.getName()), HttpStatus.BAD_REQUEST);
        }

        if (student.containsCertificationForCourse(course)) {
            log.warn("The student {} can't take the quiz of course {} because he already finished it", username, course.getName());
            throw new AppException(String.format("The student %s cannot take the quiz for course %s because" +
                    " he already finished it", username, course.getName()), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deleteQuiz(Long id) {
        log.info("Deleting quiz with id {}", id);
        quizService.deleteQuiz(id);
        log.info("Deleted quiz with id {}", id);
    }

    @Transactional
    public Long completeQuiz(Long courseId, String username) {
        log.info("Completing quiz of course {}", courseId);
        Student student = userService.getStudent(username);
        Course course = quizService.findById(courseId).getCourse();
        validateCompletingQuiz(courseId, username, student, course);
        Certification certification = new Certification();
        certification.setCourse(course);
        student.addCertification(certification);
        userService.saveStudent(student);
        log.info("Added certification to student {}", username);
        return certification.getId();
    }

    private void validateCompletingQuiz(Long courseId, String username, Student student, Course course) {
        if (!course.containsStudent(student)) {
            log.warn("Cannot complete quiz because the student {} is not enrolled in course {}", username, courseId);
            throw new AppException(String.format("The student %s cannot obtain certification for course %s because he" +
                    " is not enrolled in this course", username, course.getName()), HttpStatus.BAD_REQUEST);
        }
        if (student.containsCertificationForCourse(course)) {
            log.warn("Student {} already has a certification for course {}", username, courseId);
            throw new AppException(String.format("The student %s cannot obtain certification for course %s because he" +
                    " already has a certification for it", username, course.getName()), HttpStatus.BAD_REQUEST);
        }
    }

    private Optional<EnrolledCourse> courseExistsInStudentCourses(List<EnrolledCourse> enrolledCourses, Long id) {
        return enrolledCourses
                .stream()
                .filter(enrolledCourse -> id.equals(enrolledCourse.getCourse().getId()))
                .findFirst();
    }
}
