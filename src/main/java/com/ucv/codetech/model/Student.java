package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Setter
@Getter
public class Student extends AppUser {

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EnrolledCourse> enrolledCourses = new ArrayList<>();

    public void enrollCourse(EnrolledCourse enrolledCourse) {
        enrolledCourse.setStudent(this);
        enrolledCourses.add(enrolledCourse);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
