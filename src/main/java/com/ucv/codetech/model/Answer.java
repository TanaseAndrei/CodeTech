package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Setter
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "is_correct_answer")
    private boolean isCorrectAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private Question question;
}
