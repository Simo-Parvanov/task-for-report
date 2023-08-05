package com.example.taskleadconsult.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course  extends BaseEntity{

    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CourseType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }
}
