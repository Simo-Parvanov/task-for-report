package com.example.taskleadconsult.repositories;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Optional<Teacher> findById(String id);
    Optional<Teacher> findByNameAndAge(String name, int age);
    List<Teacher> findAll();
    @Query("SELECT t FROM Teacher t JOIN t.courses c WHERE c = :course AND t.teacherGroup = :teacherGroup")
    List<Teacher> findAllByCourseAndTeacherGroup(Course course, String teacherGroup);
}
