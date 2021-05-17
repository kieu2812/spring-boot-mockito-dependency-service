package com.springmockitomvc.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Setter
@Getter
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String dept;
}
