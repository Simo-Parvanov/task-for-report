package com.example.taskleadconsult.models;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.validation.ValidationGroups;
import jakarta.validation.constraints.*;

import java.util.List;

import static com.example.taskleadconsult.constants.ValidationMessages.*;

public class TeacherServiceModel {
    @NotNull(message = ID_REQUIRED, groups = ValidationGroups.UpdateValidation.class)
    @NotBlank(message = ID_REQUIRED, groups = ValidationGroups.UpdateValidation.class)
    private String id;
    @NotNull(groups = ValidationGroups.CreateValidation.class)
    @Size(min = 2, max = 30, message = NAME_LENGTH, groups = {ValidationGroups.CreateValidation.class, ValidationGroups.UpdateValidation.class})
    private String name;
    @NotNull(groups = ValidationGroups.CreateValidation.class)
    @Min(value = 18, message = AGE_MIN, groups = {ValidationGroups.CreateValidation.class, ValidationGroups.UpdateValidation.class})
    @Max(value = 90, message = AGE_MAX, groups = {ValidationGroups.CreateValidation.class, ValidationGroups.UpdateValidation.class})
    private int age;
    @NotNull(groups = ValidationGroups.CreateValidation.class)
    @Size(min = 1, max = 2, message = GROUP_LENGTH, groups = {ValidationGroups.CreateValidation.class, ValidationGroups.UpdateValidation.class})
    private String teacherGroup;
    @NotEmpty(message = TEACHER_COURSES_REQUIRED, groups = ValidationGroups.CreateValidation.class)
    @NotNull(message = COURSES_OBJECT_REQUIRED, groups = {ValidationGroups.CreateValidation.class, ValidationGroups.UpdateValidation.class})
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
