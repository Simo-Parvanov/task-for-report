package com.example.taskleadconsult.controllers;

import com.example.taskleadconsult.models.CourseServiceModel;
import com.example.taskleadconsult.services.CourseService;
import com.example.taskleadconsult.services.StudentService;
import com.example.taskleadconsult.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    public ReportController(StudentService studentService, TeacherService teacherService, CourseService courseService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/number-of-students")
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/number-of-teacher")
    public ResponseEntity<?> getAllTeacher(){
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/number-of-courses-by-type/{type}")
    public ResponseEntity<?> getAllCoursesByType(@PathVariable String type){
        return ResponseEntity.ok(courseService.getAllCoursesByType(type));
    }

    @GetMapping("/students-by-course")
    public ResponseEntity<?> getAllStudentsByCourse(@RequestBody CourseServiceModel courseServiceModel){
        return ResponseEntity.ok(studentService.getAllStudentByCourse(courseServiceModel));
    }
}
