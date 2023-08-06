package com.example.taskleadconsult.services;

import com.example.taskleadconsult.domain.Student;
import com.example.taskleadconsult.models.CourseServiceModel;
import com.example.taskleadconsult.models.StudentServiceModel;

public interface StudentService {
    StudentServiceModel createStudent(StudentServiceModel studentServiceModel);
    StudentServiceModel updateStudent(StudentServiceModel studentServiceModel);
    void deleteStudentByID(String id);
    String getAllStudents();
    String getAllStudentByCourse(CourseServiceModel courseServiceModel);
}
