package com.example.taskleadconsult.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;

    @Column(name = "teacher_group")
    private String teacherGroup;

    @ManyToMany
    @JoinTable(
            name = "teacher_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

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
