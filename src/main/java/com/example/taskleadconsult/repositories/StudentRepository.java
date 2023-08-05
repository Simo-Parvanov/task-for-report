package com.example.taskleadconsult.repositories;

import com.example.taskleadconsult.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findById(String id);

    Optional<Student > findByNameAndAge(String name, int age);
}