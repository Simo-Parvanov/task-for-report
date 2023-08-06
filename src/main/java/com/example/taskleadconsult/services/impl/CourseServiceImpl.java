package com.example.taskleadconsult.services.impl;

import com.example.taskleadconsult.domain.CourseType;
import com.example.taskleadconsult.repositories.CourseRepository;
import com.example.taskleadconsult.services.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public String getAllCoursesByType(String type) {
       int size = courseRepository.findAllByType(CourseType.MAIN).size();
        return String.format("Number of the course type %s: %d", type, size);
    }
}
