package com.example.taskleadconsult.controllers;

import com.example.taskleadconsult.models.StudentServiceModel;
import com.example.taskleadconsult.services.StudentService;
import com.example.taskleadconsult.validation.ValidationGroups;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody @Validated(ValidationGroups.CreateValidation.class)
                                           StudentServiceModel studentServiceModel) {
        try {
            StudentServiceModel createdStudent = studentService.createStudent(studentServiceModel);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdStudent.getId())
                            .toUri())
                    .body(createdStudent);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<StudentServiceModel> updateStudent(@RequestBody @Validated(ValidationGroups.UpdateValidation.class)
                                                             StudentServiceModel studentServiceModel) {
        return ResponseEntity.ok(studentService.updateStudent(studentServiceModel));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteStudent(@PathVariable String id) {
        studentService.deleteStudentByID(id);
        return ResponseEntity.noContent().build();
    }
}
