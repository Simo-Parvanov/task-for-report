package com.example.taskleadconsult.repositories;

import com.example.taskleadconsult.domain.Course;
import com.example.taskleadconsult.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findById(String id);
    Optional<Student > findByNameAndAge(String name, int age);
    List<Student> findAll();
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c = :course")
    List<Student> findAllByCourse(@Param("course") Course course);
    List<Student> findAllByStudentGroup(String studentGroup);
    List<Student> findAllByCoursesAndStudentGroup(Course course, String studentGroup);
    @Query("SELECT s FROM Student s WHERE s.age > :age AND :courseName MEMBER OF s.courses")
    List<Student> findStudentsByAgeAndCourse(@Param("age") int age, @Param("courseName") String courseName);
}
