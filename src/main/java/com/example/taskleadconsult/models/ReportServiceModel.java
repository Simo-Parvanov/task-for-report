package com.example.taskleadconsult.models;

import com.example.taskleadconsult.domain.Course;

public class ReportServiceModel {
    private Course courses;
    private String group;
    private int age;

    public ReportServiceModel() {
    }

    public Course getCourses() {
        return courses;
    }

    public void setCourses(Course courses) {
        this.courses = courses;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
