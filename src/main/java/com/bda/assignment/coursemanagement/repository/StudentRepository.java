package com.bda.assignment.coursemanagement.repository;

import com.bda.assignment.coursemanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course c")
    List<Student> findAllStudentsWithCourses();
 
    @Query(value = "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course c", countQuery = "SELECT COUNT(DISTINCT s) FROM Student s")
    Page<Student> findAllStudentsWithCourses(Pageable pageable);
}