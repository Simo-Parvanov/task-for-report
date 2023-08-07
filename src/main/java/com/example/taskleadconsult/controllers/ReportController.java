package com.example.taskleadconsult.controllers;

import com.example.taskleadconsult.models.CourseServiceModel;
import com.example.taskleadconsult.models.ReportServiceModel;
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
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/number-of-teacher")
    public ResponseEntity<?> getAllTeacher() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/number-of-courses-by-type/{type}")
    public ResponseEntity<?> getAllCoursesByType(@PathVariable String type) {
        return ResponseEntity.ok(courseService.getAllCoursesByType(type));
    }

    @GetMapping("/students-by-course")
    public ResponseEntity<?> getAllStudentsByCourse(@RequestBody CourseServiceModel courseServiceModel) {
        return ResponseEntity.ok(studentService.getAllStudentsByCourse(courseServiceModel));
    }

    @GetMapping("/students-by-group/{group}")
    public ResponseEntity<?> getAllStudentsByStudentGroup(@PathVariable String group) {
        return ResponseEntity.ok(studentService.getAllStudentsByGroup(group));
    }

    @GetMapping("/students-and-teachers-by-course-and-group")
    public ResponseEntity<?> getAllStudentsAndTeacherByCourseAndGroup(@RequestBody ReportServiceModel reportServiceModel) {
        StringBuilder stringBuilder = new StringBuilder();
        String studentsList = studentService.getStudentsByCoursesAndGroup(reportServiceModel);
        String teacherList = teacherService.getTeachersByCoursesAndGroup(reportServiceModel);
        stringBuilder.append(studentsList).append("\n").append(teacherList);
        return ResponseEntity.ok(stringBuilder.toString());
    }

    @GetMapping("/students-by-age-and-course")
    public ResponseEntity<?> getAllStudentsByStudentGroup(@RequestBody ReportServiceModel reportServiceModel) {
        return ResponseEntity.ok(studentService.gerStudentsByUnderCertainAgeAndCourse(reportServiceModel));
    }
}
