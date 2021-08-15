package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructor")
@Setter
@Getter
public class Instructor extends AppUser {

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Quiz> quizzes = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        quiz.setInstructor(this);
    }
}
