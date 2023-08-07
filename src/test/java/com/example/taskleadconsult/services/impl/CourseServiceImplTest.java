package com.example.taskleadconsult.services.impl;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.CourseType;
import com.example.taskleadconsult.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCoursesByType() {
        String type = "MAIN";
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Course 1", CourseType.MAIN));
        courses.add(new Course("Course 2", CourseType.SECONDARY));
        courses.add(new Course("Course 3", CourseType.MAIN));
        courses.add(new Course("Course 4", CourseType.MAIN));

        when(courseRepository.findAllByType(CourseType.MAIN)).thenReturn(
                courses.stream()
                        .filter(course -> course.getType() == CourseType.MAIN)
                        .collect(Collectors.toList())
        );

        String result = courseService.getAllCoursesByType(type);

        assertEquals("Number of the course type MAIN: 3", result);
    }
}
