package com.ucv.codetech.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "course")
@NoArgsConstructor
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "instructor_name")
    private String instructorName;

    @Column(name = "description")
    private String description;

    @OneToOne
    private Category category;

    @Column(name = "enrolled_students")
    private int enrolledStudents;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "folder")
    private String folder;

    @Column(name = "available")
    private boolean available;

    @Column(name = "cover_image_path")
    private String coverImagePath;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CourseLecture> courseLectures;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<Comment> comments;

    @Transient
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PrePersist
    private void initEntity() {
        this.enrolledStudents = 0;
        this.creationDate = LocalDateTime.now().format(dateTimeFormatter);
        this.available = false;
    }
}
