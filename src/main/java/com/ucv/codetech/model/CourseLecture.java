package com.ucv.codetech.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lectures")
@NoArgsConstructor
@Setter
@Getter
public class CourseLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ElementCollection(targetClass = String.class)
    private List<String> lectureFilePaths;

    @Column(name = "lectureVideoPath")
    private String lectureVideoPath;
}
