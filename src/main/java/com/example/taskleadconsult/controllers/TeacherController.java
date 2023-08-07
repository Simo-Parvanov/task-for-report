package com.example.taskleadconsult.controllers;

import com.example.taskleadconsult.models.TeacherServiceModel;
import com.example.taskleadconsult.services.TeacherService;
import com.example.taskleadconsult.validation.ValidationGroups;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody @Validated(ValidationGroups.CreateValidation.class)
                                         TeacherServiceModel teacherServiceModel) {
        try {
            TeacherServiceModel createdTeacher = teacherService.createTeacher(teacherServiceModel);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdTeacher.getId())
                            .toUri())
                    .body(createdTeacher);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TeacherServiceModel> updateStudent(@RequestBody @Validated(ValidationGroups.UpdateValidation.class)
                                                             TeacherServiceModel teacherServiceModel) {
        return ResponseEntity.ok(teacherService.updateTeacher(teacherServiceModel));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacherByID(id);
        return ResponseEntity.noContent().build();
    }
}
