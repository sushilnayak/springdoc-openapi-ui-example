package com.nayak.springdocopenapiuiexample.repository;

import com.nayak.springdocopenapiuiexample.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    Optional<Student> findByStudentId(String studentId);

    List<Student> findByStatus(Student.StudentStatus status);

    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:searchTerm% OR s.lastName LIKE %:searchTerm%")
    List<Student> searchByName(String searchTerm);
}