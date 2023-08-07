package com.example.taskleadconsult.services.impl;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Student;
import com.example.taskleadconsult.domain.Teacher;
import com.example.taskleadconsult.models.ReportServiceModel;
import com.example.taskleadconsult.models.TeacherServiceModel;
import com.example.taskleadconsult.repositories.CourseRepository;
import com.example.taskleadconsult.repositories.TeacherRepository;
import com.example.taskleadconsult.services.TeacherService;
import com.example.taskleadconsult.utils.CourseUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final ModelMapper modelMapper;
    private final CourseUtils courseUtils;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public TeacherServiceImpl(ModelMapper modelMapper, CourseUtils courseUtils, TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.modelMapper = modelMapper;
        this.courseUtils = courseUtils;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public TeacherServiceModel createTeacher(TeacherServiceModel teacherServiceModel) {
        if (teacherRepository.findByNameAndAge(teacherServiceModel.getName(), teacherServiceModel.getAge()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("A teacher with the same name: %s and age: %d already exists.",
                            teacherServiceModel.getName(), teacherServiceModel.getAge()));
        }
        Teacher teacher = modelMapper.map(teacherServiceModel, Teacher.class);
        if (!teacher.getCourses().isEmpty()) {
            List<Course> courses = courseUtils.getCreateOrUpdateCourseList(teacherServiceModel.getCourses());
            teacher.setCourses(courses);
        }
        Teacher saveAndFlushTeacher = teacherRepository.saveAndFlush(teacher);
        return modelMapper.map(saveAndFlushTeacher, TeacherServiceModel.class);
    }

    @Override
    public TeacherServiceModel updateTeacher(TeacherServiceModel teacherServiceModel) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherServiceModel.getId());
        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            teacher.setName(teacherServiceModel.getName());
            teacher.setAge(teacherServiceModel.getAge());
            teacher.setTeacherGroup(teacherServiceModel.getTeacherGroup());
            List<Course> courses = courseUtils.getCreateOrUpdateCourseList(teacherServiceModel.getCourses());
            teacher.setCourses(courses);

            Teacher saveTeacher = teacherRepository.save(teacher);
            return modelMapper.map(saveTeacher, TeacherServiceModel.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Teacher with ID %s not found!", teacherServiceModel.getId()));
        }
    }

    @Override
    public void deleteTeacherByID(String id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if (teacherOptional.isPresent()) {
        teacherRepository.delete(teacherRepository.findById(id).get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Teacher with ID %s not found!", id));
        }
    }

    @Override
    public String getAllTeachers() {
        int size = teacherRepository.findAll().size();
        return String.format("Number of teachers: %d", size);
    }

    @Override
    public String getTeachersByCoursesAndGroup(ReportServiceModel reportServiceModel) {
        Course course = courseRepository.findByNameAndType(reportServiceModel.getCourses().getName(),
                reportServiceModel.getCourses().getType());
        List<Teacher> teacherList = teacherRepository.findAllByCourseAndTeacherGroup(course, reportServiceModel.getGroup());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("------------ The teachers from course %s-%s and group %s are:%d ------------",
                reportServiceModel.getCourses().getName(),
                reportServiceModel.getCourses().getType(),
                reportServiceModel.getGroup(),
                teacherList.size())).append("\n");
        for (Teacher teacher : teacherList) {
            stringBuilder.append(String.format("%s on %d years old - group: %s",
                    teacher.getName(),
                    teacher.getAge(),
                    teacher.getTeacherGroup())).append("\n");
        }
        return stringBuilder.toString();
    }
}
