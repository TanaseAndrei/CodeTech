package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enrolled_course")
@Setter
@Getter
public class EnrolledCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "enrolled_date")
    private String enrolledDate;

    @Column(name = "number_of_lectures")
    private int numberOfLectures;

    @Column(name = "number_of_completed_lectures")
    private int numberOfCompletedLectures;

    @Column(name = "is_course_completed")
    private boolean isCourseCompleted;

    @OneToMany(mappedBy = "enrolledCourse", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureWrapper> lectureWrappers = new ArrayList<>();

    @Transient
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @PrePersist
    public void init() {
        this.isCourseCompleted = false;
        this.enrolledDate = LocalDateTime.now().format(dateTimeFormatter);
    }

    @PreUpdate
    public void update() {
        if(numberOfCompletedLectures == numberOfLectures) {
            this.isCourseCompleted = true;
        }
    }

    public void addLectureWrapper(LectureWrapper lectureWrapper) {
        lectureWrapper.setEnrolledCourse(this);
        this.lectureWrappers.add(lectureWrapper);
        this.numberOfLectures++;
    }

    public void increaseNumberOfCompletedLectures() {
        this.numberOfCompletedLectures++;
    }
}
