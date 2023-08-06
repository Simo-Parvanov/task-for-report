package com.example.taskleadconsult.models;

import com.example.taskleadconsult.domain.CourseType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class CourseServiceModel {

    private String name;
    private CourseType type;

    public CourseServiceModel() {
    }

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
