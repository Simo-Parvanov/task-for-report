package com.example.taskleadconsult.services;

import com.example.taskleadconsult.models.CourseServiceModel;
import com.example.taskleadconsult.models.ReportServiceModel;
import com.example.taskleadconsult.models.StudentServiceModel;

public interface StudentService {
    StudentServiceModel createStudent(StudentServiceModel studentServiceModel);
    StudentServiceModel updateStudent(StudentServiceModel studentServiceModel);
    void deleteStudentByID(String id);
    String getAllStudents();
    String getAllStudentsByCourse(CourseServiceModel courseServiceModel);
    String getAllStudentsByGroup(String group);
    String getStudentsByCoursesAndGroup(ReportServiceModel reportServiceModel);

    String gerStudentsByUnderCertainAgeAndCourse(ReportServiceModel reportServiceModel);
}
