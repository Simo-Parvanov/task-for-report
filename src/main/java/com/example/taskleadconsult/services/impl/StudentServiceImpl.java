package com.example.taskleadconsult.services.impl;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Student;
import com.example.taskleadconsult.models.StudentServiceModel;
import com.example.taskleadconsult.repositories.CourseRepository;
import com.example.taskleadconsult.repositories.StudentRepository;
import com.example.taskleadconsult.services.StudentService;
import com.example.taskleadconsult.utils.CourseUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final ModelMapper modelMapper;
    private final CourseUtils courseUtils;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(ModelMapper modelMapper, CourseUtils courseUtils, StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.courseUtils = courseUtils;
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(StudentServiceModel studentServiceModel) {
        Student student = modelMapper.map(studentServiceModel, Student.class);
        if (!student.getCourses().isEmpty()) {
            List<Course> courses = courseUtils.getCreateOrUpdateCourseList(studentServiceModel.getCourses());
            student.setCourses(courses);
        }
       return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student updateStudent(StudentServiceModel studentServiceModel) {
        Optional<Student> studentOptional = studentRepository.findById(studentServiceModel.getId());
        Student student = studentOptional.get();
        student.setName(studentServiceModel.getName());
        student.setAge(studentServiceModel.getAge());
        student.setStudentGroup(studentServiceModel.getStudentGroup());
        List<Course> courses = courseUtils.getCreateOrUpdateCourseList(studentServiceModel.getCourses());
        student.setCourses(courses);

        return studentRepository.save(student);
    }
}
