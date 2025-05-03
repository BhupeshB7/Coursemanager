package com.bda.assignment.coursemanagement.repository;

import com.bda.assignment.coursemanagement.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameContainingIgnoreCase(String name);

    boolean existsByCode(String code);

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.enrollments e JOIN FETCH e.student s")
    List<Course> findAllCoursesWithStudents();

    @Query(value = "SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.enrollments e LEFT JOIN FETCH e.student s", countQuery = "SELECT COUNT(DISTINCT c) FROM Course c")
    Page<Course> findAllCoursesWithStudents(Pageable pageable);

    @Query(value = "SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.enrollments e LEFT JOIN FETCH e.student s WHERE s.id = :studentId", countQuery = "SELECT COUNT(DISTINCT c) FROM Course c JOIN c.enrollments e JOIN e.student s WHERE s.id = :studentId")
    Page<Course> findCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);
}