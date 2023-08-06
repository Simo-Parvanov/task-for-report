package com.example.taskleadconsult.repositories;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByNameAndType (String name, CourseType type);
    List<Course> findAllByType(CourseType type);
}
