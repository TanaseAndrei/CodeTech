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

    @OneToOne
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "comment")
    private String description;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @Column(name = "last_time_updated")
    private LocalDateTime lastTimeUpdated;

    @Column(name = "up_votes")
    private Integer upVotes;

    @Column(name = "down_votes")
    private Integer downVotes;

    @PrePersist
    public void setTimeStamp() {
        this.timeStamp = LocalDateTime.now();
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
}
