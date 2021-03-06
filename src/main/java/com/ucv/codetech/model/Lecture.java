package com.ucv.codetech.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lectures")
@NoArgsConstructor
@Setter
@Getter
public class Lecture {

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
    private List<String> lectureFileNames = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<LectureWrapper> lectureWrappers = new ArrayList<>();

    @Column(name = "lecture_video_name")
    private String lectureVideoName;

    public boolean containsLectureFile(String fileName) {
        return lectureFileNames.contains(fileName);
    }

    public boolean deleteLectureFile(String fileName) {
        return lectureFileNames.remove(fileName);
    }
}
