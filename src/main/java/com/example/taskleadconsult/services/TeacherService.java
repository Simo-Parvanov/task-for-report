package com.example.taskleadconsult.services;

import com.example.taskleadconsult.models.TeacherServiceModel;

public interface TeacherService {
    TeacherServiceModel createTeacher(TeacherServiceModel teacherServiceModel);
    TeacherServiceModel updateTeacher(TeacherServiceModel teacherServiceModel);
    void deleteTeacherByID(String id);
    String getAllTeachers();
}
