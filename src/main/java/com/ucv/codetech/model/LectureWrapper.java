package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lecture_wrapper")
@Setter
@Getter
public class LectureWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "enrolled_course_id")
    private EnrolledCourse enrolledCourse;

    @Column(name = "completed_lecture")
    public boolean completedLecture = false;

    public void completeLecture() {
        this.completedLecture = true;
        this.enrolledCourse.increaseNumberOfCompletedLectures();
    }
}
