package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
@Setter
@Getter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
        question.setQuiz(this);
    }
}
