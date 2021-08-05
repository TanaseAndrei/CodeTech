package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Setter
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "question")
    private String questionContent;

    @Column(name = "multiple_answers")
    private boolean multipleAnswers;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    @PreRemove
    public void deleteParent() {
        quiz.getQuestions().remove(this);
        this.quiz = null;
    }
}
