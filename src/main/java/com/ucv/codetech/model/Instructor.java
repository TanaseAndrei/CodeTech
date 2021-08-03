package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "instructor")
@Setter
@Getter
public class Instructor extends AppUser {
    //TODO
}
