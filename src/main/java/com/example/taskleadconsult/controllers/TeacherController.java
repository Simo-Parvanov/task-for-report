package com.example.taskleadconsult.controllers;

import com.example.taskleadconsult.models.TeacherServiceModel;
import com.example.taskleadconsult.repositories.TeacherRepository;
import com.example.taskleadconsult.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
private final TeacherService teacherService;
private final TeacherRepository teacherRepository;

    public TeacherController(TeacherService teacherService, TeacherRepository teacherRepository) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody TeacherServiceModel teacherServiceModel,
                                         UriComponentsBuilder builder){
        if (teacherRepository.findByNameAndAge(teacherServiceModel.getName(), teacherServiceModel.getAge()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(String.format("A teacher with the same name: %s and age: %d already exists.",
                            teacherServiceModel.getName(), teacherServiceModel.getAge()));
        }
        return ResponseEntity.created(builder.path("/student/create")
                        .buildAndExpand().toUri())
                .body(teacherService.createTeacher(teacherServiceModel));
    }

    @PutMapping("/update")
    public ResponseEntity<TeacherServiceModel> updateStudent(@RequestBody TeacherServiceModel teacherServiceModel) {
        if (teacherRepository.findById(teacherServiceModel.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teacherService.updateTeacher(teacherServiceModel));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteTeacher(@PathVariable String id){

        if (teacherRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        teacherService.deleteTeacherByID(id);
        return ResponseEntity.noContent().build();
    }
}
