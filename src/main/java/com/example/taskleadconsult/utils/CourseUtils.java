package com.example.taskleadconsult.utils;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Student;
import com.example.taskleadconsult.models.StudentServiceModel;
import com.example.taskleadconsult.repositories.CourseRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseUtils {

    private final CourseRepository courseRepository;

    public CourseUtils(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCreateOrUpdateCourseList(List<Course> newCourses) {
        List<Course> courses = new ArrayList<>();
        for (Course newCourse : newCourses) {
            Course findCourse = courseRepository.findByNameAndType(newCourse.getName(), newCourse.getType());
            if (findCourse != null) {
                findCourse.setType(newCourse.getType());
                courses.add(findCourse);
            } else {
                courses.add(courseRepository.save(newCourse));
            }
        }
        return courses;
    }
}
