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

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EnrolledCourse> enrolledCourses = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setStudent(this);
    }

    public void addEnrolledCourse(EnrolledCourse enrolledCourse) {
        this.enrolledCourses.add(enrolledCourse);
    }
}
