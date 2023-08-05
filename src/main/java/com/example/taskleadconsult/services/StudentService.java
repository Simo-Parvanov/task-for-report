package com.example.taskleadconsult.services;

import com.example.taskleadconsult.domain.Student;
import com.example.taskleadconsult.models.StudentServiceModel;

public interface StudentService {
    Student createStudent(StudentServiceModel studentServiceModel);
    Student updateStudent(StudentServiceModel studentServiceModel);
}
