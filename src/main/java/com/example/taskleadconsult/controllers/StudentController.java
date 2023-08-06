package com.example.taskleadconsult.controllers;

import com.example.taskleadconsult.models.StudentServiceModel;
import com.example.taskleadconsult.repositories.StudentRepository;
import com.example.taskleadconsult.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/student")
public class StudentController {
private final StudentService studentService;
private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody StudentServiceModel studentServiceModel,
                                                           UriComponentsBuilder builder){
        if (studentRepository.findByNameAndAge(studentServiceModel.getName(), studentServiceModel.getAge()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(String.format("A student with the same name: %s and age: %d already exists.",
                            studentServiceModel.getName(), studentServiceModel.getAge()));
        }
        return ResponseEntity.created(builder.path("/student/create")
                        .buildAndExpand().toUri())
                .body(studentService.createStudent(studentServiceModel));
    }

    @PutMapping("/update")
    public ResponseEntity<StudentServiceModel> updateStudent(@RequestBody StudentServiceModel studentServiceModel) {
        if (studentRepository.findById(studentServiceModel.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(studentService.updateStudent(studentServiceModel));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteStudent(@PathVariable String id){

        if (studentRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudentByID(id);
        return ResponseEntity.noContent().build();
    }
}
