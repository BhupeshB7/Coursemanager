// package  com.bda.assignment.coursemanagement.repository;

// import  com.bda.assignment.coursemanagement.model.Enrollment;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;
// // import org.springframework.data.domain.Page;
// // import org.springframework.data.domain.Pageable;
// import java.util.List;

// @Repository
// public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> { 
//     List<Enrollment> findByStudentId(Long studentId);
 
//     List<Enrollment> findByCourseId(Long courseId);
 
//     boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
 
//     @Query("SELECT e FROM Enrollment e INNER JOIN e.student s INNER JOIN e.course c WHERE s.id = :studentId")
//     List<Enrollment> findEnrollmentsByStudentWithCourses(@Param("studentId") Long studentId);
//     //  @Query("SELECT e FROM Enrollment e INNER JOIN e.student s INNER JOIN e.course c WHERE s.id = :studentId")
//     //  Page<Enrollment> findEnrollmentsByStudentsWithCoursesPaginated(@Param("courseId") Long courseId, Pageable pageable);
//     @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId")
//     Long countByCourseId(@Param("courseId") Long courseId);
//     // @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
//     // Page<Enrollment> findEnrollmentsByCourseId(@Param("courseId") Long courseId, Pageable pageable);
// }


package com.bda.assignment.coursemanagement.repository;

import com.bda.assignment.coursemanagement.model.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("SELECT e FROM Enrollment e INNER JOIN e.student s INNER JOIN e.course c WHERE s.id = :studentId")
    List<Enrollment> findEnrollmentsByStudentWithCourses(@Param("studentId") Long studentId);

    @Query("SELECT e FROM Enrollment e INNER JOIN e.student s INNER JOIN e.course c WHERE s.id = :studentId")
    Page<Enrollment> findEnrollmentsByStudentWithCoursesPaginated(@Param("studentId") Long studentId,
            Pageable pageable);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
    Page<Enrollment> findEnrollmentsByCourseId(@Param("courseId") Long courseId, Pageable pageable);

    // Add pagination support to findAll
    Page<Enrollment> findAll(Pageable pageable);
}