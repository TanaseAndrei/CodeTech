package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Setter
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "comment")
    private String description;

    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    @Column(name = "last_time_updated")
    private LocalDateTime lastTimeUpdated;

    @Column(name = "up_votes")
    private Integer upVotes;

    @Column(name = "down_votes")
    private Integer downVotes;

    @PrePersist
    public void setTimeStamp() {
        this.commentDate = LocalDateTime.now();
        this.upVotes = 0;
        this.downVotes = 0;
    }

    @PreUpdate
    public void updateStatus() {
        this.lastTimeUpdated = LocalDateTime.now();
    }

    public void upVote() {
        upVotes++;
    }

    public void downVote() {
        downVotes--;
    }

    public void addComment(Student student, Course course) {
        this.course = course;
        this.student = student;
        student.addComment(this);
        course.addComment(this);
    }
}
