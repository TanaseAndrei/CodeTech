package com.ucv.codetech.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> enrolledStudents = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @Column(name = "number_of_lectures")
    private int numberOfLectures;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "nuber_of_comments")
    private int numberOfComments;

    @Transient
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
