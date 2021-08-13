package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "student")
@Setter
@Getter
public class Student extends AppUser {

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EnrolledCourse> enrolledCourses = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Certification> certifications = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setStudent(this);
    }

    public void addEnrolledCourse(EnrolledCourse enrolledCourse) {
        this.enrolledCourses.add(enrolledCourse);
    }

    public void addCertification(Certification certification) {
        this.certifications.add(certification);
        certification.setStudent(this);
    }

    public boolean containsCertificationForCourse(Course course) {
        Optional<Certification> optionalCertification = certifications
                .stream()
                .filter(certification -> certification.getCourse().equals(course))
                .findFirst();
        return optionalCertification.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;

        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return 1128121276;
    }
}
