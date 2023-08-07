package com.example.taskleadconsult.services.impl;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Student;
import com.example.taskleadconsult.domain.Teacher;
import com.example.taskleadconsult.models.CourseServiceModel;
import com.example.taskleadconsult.models.ReportServiceModel;
import com.example.taskleadconsult.models.StudentServiceModel;
import com.example.taskleadconsult.repositories.CourseRepository;
import com.example.taskleadconsult.repositories.StudentRepository;
import com.example.taskleadconsult.services.StudentService;
import com.example.taskleadconsult.utils.CourseUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (studentRepository.findByNameAndAge(studentServiceModel.getName(), studentServiceModel.getAge()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("A student with the same name: %s and age: %d already exists.",
                            studentServiceModel.getName(), studentServiceModel.getAge()));
        }
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
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setName(studentServiceModel.getName());
            student.setAge(studentServiceModel.getAge());
            student.setStudentGroup(studentServiceModel.getStudentGroup());
            List<Course> courses = courseUtils.getCreateOrUpdateCourseList(studentServiceModel.getCourses());
            student.setCourses(courses);

            Student saveStudent = studentRepository.save(student);
            return modelMapper.map(saveStudent, StudentServiceModel.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Student with ID %s not found!", studentServiceModel.getId()));
        }
    }

    @Override
    public void deleteStudentByID(String id) {
        Optional<Student> teacherOptional = studentRepository.findById(id);
        if (teacherOptional.isPresent()) {
            studentRepository.delete(studentRepository.findById(id).get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Student with ID %s not found!", id));
        }
    }

    @Override
    public String getAllStudents() {
        int size = studentRepository.findAll().size();
        return String.format("Number of students: %d", size);
    }

    @Override
    public String getAllStudentsByCourse(CourseServiceModel courseServiceModel) {
        Course course = courseRepository.findByNameAndType(courseServiceModel.getName(), courseServiceModel.getType());
        if (course == null) {
            return String.format("Course %s-%s not found!",
                    courseServiceModel.getName(),
                    courseServiceModel.getType());
        }
        List<Student> allByCourse = studentRepository.findAllByCourse(course);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("------------ The students from course %s-%s are:%d ------------",
                courseServiceModel.getName(), courseServiceModel.getType().name(), allByCourse.size())).append("\n");
        for (Student student : allByCourse) {
            stringBuilder.append(String.format("%s on %d years old", student.getName(), student.getAge())).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String getAllStudentsByGroup(String group) {
        List<Student> allByStudentGroup = studentRepository.findAllByStudentGroup(group);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("------------ The students from group %s are:%d ------------",
                group, allByStudentGroup.size())).append("\n");
        for (Student student : allByStudentGroup) {
            stringBuilder.append(String.format("%s on %d years old", student.getName(), student.getAge())).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String getStudentsByCoursesAndGroup(ReportServiceModel reportServiceModel) {
        Course course = courseRepository.findByNameAndType(reportServiceModel.getCourses().getName(),
                reportServiceModel.getCourses().getType());
        if (course == null) {
            return String.format("Course %s-%s not found!",
                    reportServiceModel.getCourses().getName(),
                    reportServiceModel.getCourses().getType());
        }
        List<Student> studentList = studentRepository.findAllByCourseAndStudentGroup(course, reportServiceModel.getGroup());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("------------ The students from course %s-%s and group %s are:%d ------------",
                reportServiceModel.getCourses().getName(),
                reportServiceModel.getCourses().getType(),
                reportServiceModel.getGroup(),
                studentList.size())).append("\n");
        for (Student student : studentList) {
            stringBuilder.append(String.format("%s on %d years old - group: %s",
                    student.getName(),
                    student.getAge(),
                    student.getStudentGroup())).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String gerStudentsByUnderCertainAgeAndCourse(ReportServiceModel reportServiceModel) {
        Course course = courseRepository.findByNameAndType(reportServiceModel.getCourses().getName(),
                reportServiceModel.getCourses().getType());
        if (course == null) {
            return String.format("Course %s-%s not found!",
                    reportServiceModel.getCourses().getName(),
                    reportServiceModel.getCourses().getType());
        }
        List<Student> studentList = studentRepository.findStudentsByAgeAndCourse(reportServiceModel.getAge(), course);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("------------ The students from course %s-%s and age under %d are:%d ------------",
                reportServiceModel.getCourses().getName(),
                reportServiceModel.getCourses().getType(),
                reportServiceModel.getAge(),
                studentList.size())).append("\n");
        for (Student student : studentList) {
            stringBuilder.append(String.format("%s on %d years old",
                    student.getName(),
                    student.getAge())).append("\n");
        }
        return stringBuilder.toString();
    }
}
