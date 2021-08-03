package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructor")
@Setter
@Getter
public class Instructor extends AppUser {

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }
}
