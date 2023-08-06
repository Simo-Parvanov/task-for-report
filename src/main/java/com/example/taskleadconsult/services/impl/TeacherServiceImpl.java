package com.example.taskleadconsult.services.impl;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Teacher;
import com.example.taskleadconsult.models.TeacherServiceModel;
import com.example.taskleadconsult.repositories.TeacherRepository;
import com.example.taskleadconsult.services.TeacherService;
import com.example.taskleadconsult.utils.CourseUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final ModelMapper modelMapper;
    private final CourseUtils courseUtils;
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(ModelMapper modelMapper, CourseUtils courseUtils, TeacherRepository teacherRepository) {
        this.modelMapper = modelMapper;
        this.courseUtils = courseUtils;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherServiceModel createTeacher(TeacherServiceModel teacherServiceModel) {
        Teacher teacher = modelMapper.map(teacherServiceModel, Teacher.class);
        if (!teacher.getCourses().isEmpty()) {
            List<Course> courses = courseUtils.getCreateOrUpdateCourseList(teacherServiceModel.getCourses());
            teacher.setCourses(courses);
        }
        Teacher saveAndFlushTeacher = teacherRepository.saveAndFlush(teacher);
        return  modelMapper.map(saveAndFlushTeacher, TeacherServiceModel.class);
    }

    @Override
    public TeacherServiceModel updateTeacher(TeacherServiceModel teacherServiceModel) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherServiceModel.getId());
        Teacher teacher = teacherOptional.get();
        teacher.setName(teacherServiceModel.getName());
        teacher.setAge(teacherServiceModel.getAge());
        teacher.setTeacherGroup(teacherServiceModel.getTeacherGroup());
        List<Course> courses = courseUtils.getCreateOrUpdateCourseList(teacherServiceModel.getCourses());
        teacher.setCourses(courses);

        Teacher saveTeacher = teacherRepository.save(teacher);
        return modelMapper.map(saveTeacher, TeacherServiceModel.class);
    }

    @Override
    public void deleteTeacherByID(String id) {
        teacherRepository.delete(teacherRepository.findById(id).get());
    }

    @Override
    public String getAllTeachers() {
        int size = teacherRepository.findAll().size();
        return String.format("Number of teachers: %d", size);
    }
}
