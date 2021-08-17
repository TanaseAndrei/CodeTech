package com.ucv.codetech.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Column(name = "description")
    private String description;

    @OneToOne
    private Category category;

    @OneToOne(mappedBy = "course", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Quiz quiz;

    @Column(name = "enrolled_students")
    private int nrOfEnrolledStudents;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "available")
    private boolean available;

    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "cover_image_name")
    private String coverImageName;

    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "number_of_lectures")
    private int numberOfLectures;

    @Column(name = "number_of_comments")
    private int numberOfComments;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EnrolledCourse> enrolledCourses = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Student> enrolledStudents = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @PrePersist
    private void initEntity() {
        this.nrOfEnrolledStudents = 0;
        this.numberOfLectures = 0;
        this.numberOfComments = 0;
        this.creationDate = LocalDateTime.now().format(dateTimeFormatter);
        this.available = false;
    }

    @PreUpdate
    private void update() {
        this.numberOfLectures = lectures.size();
        this.numberOfComments = comments.size();
        this.nrOfEnrolledStudents = enrolledStudents.size();
    }

    public void enrollStudent(Student student, EnrolledCourse enrolledCourse) {
        student.addEnrolledCourse(enrolledCourse);
        this.enrolledStudents.add(student);
        this.enrolledCourses.add(enrolledCourse);
    }

    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
        this.numberOfLectures++;
        lecture.setCourse(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.numberOfComments++;
        comment.setCourse(this);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        quiz.setCourse(this);
    }

    public boolean containsStudent(Student student) {
        return enrolledStudents.contains(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;

        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return 1702818130;
    }
}
