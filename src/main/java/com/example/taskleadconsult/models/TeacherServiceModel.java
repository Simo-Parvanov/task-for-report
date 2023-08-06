package com.example.taskleadconsult.models;

import com.example.taskleadconsult.domain.Course;

import java.util.List;

public class TeacherServiceModel {
    private String id;
    private String name;
    private int age;
    private String teacherGroup;
    private List<Course> courses;

    public TeacherServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(String teacherGroup) {
        this.teacherGroup = teacherGroup;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
