package com.ucv.codetech.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course_lecture")
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

    @ElementCollection(targetClass = String.class)
    private List<String> lectureFilePaths;

    @Column(name = "lectureVideoPath")
    private String lectureVideoPath;
}
