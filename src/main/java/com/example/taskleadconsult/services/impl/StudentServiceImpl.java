package com.example.taskleadconsult.services.impl;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Student;
import com.example.taskleadconsult.models.CourseServiceModel;
import com.example.taskleadconsult.models.StudentServiceModel;
import com.example.taskleadconsult.repositories.CourseRepository;
import com.example.taskleadconsult.repositories.StudentRepository;
import com.example.taskleadconsult.services.StudentService;
import com.example.taskleadconsult.utils.CourseUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final ModelMapper modelMapper;
    private final CourseUtils courseUtils;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(ModelMapper modelMapper, CourseUtils courseUtils, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.modelMapper = modelMapper;
        this.courseUtils = courseUtils;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public StudentServiceModel createStudent(StudentServiceModel studentServiceModel) {
        Student student = modelMapper.map(studentServiceModel, Student.class);
        if (!student.getCourses().isEmpty()) {
            List<Course> courses = courseUtils.getCreateOrUpdateCourseList(studentServiceModel.getCourses());
            student.setCourses(courses);
        }
        Student saveAndFlush = studentRepository.saveAndFlush(student);
        return modelMapper.map(saveAndFlush, StudentServiceModel.class);
    }

    @Override
    public StudentServiceModel updateStudent(StudentServiceModel studentServiceModel) {
        Optional<Student> studentOptional = studentRepository.findById(studentServiceModel.getId());
        Student student = studentOptional.get();
        student.setName(studentServiceModel.getName());
        student.setAge(studentServiceModel.getAge());
        student.setStudentGroup(studentServiceModel.getStudentGroup());
        List<Course> courses = courseUtils.getCreateOrUpdateCourseList(studentServiceModel.getCourses());
        student.setCourses(courses);

        Student saveStudent = studentRepository.save(student);
        return modelMapper.map(saveStudent, StudentServiceModel.class);
    }

    @Override
    public void deleteStudentByID(String id) {
        studentRepository.delete(studentRepository.findById(id).get());
    }

    @Override
    public String getAllStudents() {
        int size = studentRepository.findAll().size();
        return String.format("Number of students: %d", size);
    }

    @Override
    public String getAllStudentByCourse(CourseServiceModel courseServiceModel) {
        Course course = courseRepository.findByNameAndType(courseServiceModel.getName(), courseServiceModel.getType());
        List<Student> allByCourse = studentRepository.findAllByCourse(course);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("------------ The students from course %s-%s are:%d ------------",
                courseServiceModel.getName(), courseServiceModel.getType().name(),allByCourse.size())).append("\n");
        for (Student student : allByCourse) {
            stringBuilder.append(String.format("%s on %d years old", student.getName(), student.getAge())).append("\n");
        }
        return stringBuilder.toString();
    }
}
