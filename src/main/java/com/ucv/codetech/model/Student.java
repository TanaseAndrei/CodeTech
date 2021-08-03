package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@Setter
@Getter
public class Student extends AppUser {
    //TODO
}
