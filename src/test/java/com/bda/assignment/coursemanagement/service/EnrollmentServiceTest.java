package com.bda.assignment.coursemanagement.service;

import com.bda.assignment.coursemanagement.model.Course;
import com.bda.assignment.coursemanagement.model.Enrollment;
import com.bda.assignment.coursemanagement.model.Student;
import com.bda.assignment.coursemanagement.repository.CourseRepository;
import com.bda.assignment.coursemanagement.repository.EnrollmentRepository;
import com.bda.assignment.coursemanagement.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Student student;
    private Course course;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setId(1L);
        student.setName("Test Student");

        course = new Course();
        course.setId(1L);
        course.setName("Test Course");

        enrollment = new Enrollment();
    }

    @Test
    void testCreateEnrollment_Success() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(i -> i.getArgument(0));

        Enrollment created = enrollmentService.createEnrollment(1L, 1L, enrollment);

        assertNotNull(created);
        assertEquals(student, created.getStudent());
        assertEquals(course, created.getCourse());
        assertEquals(Enrollment.Grade.NOT_GRADED, created.getGrade());
        assertEquals(LocalDate.now(), created.getEnrollmentDate());
    }

    @Test
    void testCreateEnrollment_AlreadyExists_ThrowsException() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(true);

        DataIntegrityViolationException ex = assertThrows(DataIntegrityViolationException.class,
                () -> enrollmentService.createEnrollment(1L, 1L, enrollment));

        assertTrue(ex.getMessage().contains("Student is already enrolled"));
    }

    @Test
    void testCreateEnrollment_StudentNotFound_ThrowsException() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> enrollmentService.createEnrollment(1L, 1L, enrollment));

        assertEquals("Student not found with id: 1", ex.getMessage());
    }

    @Test
    void testUpdateEnrollmentGrade_Success() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setGrade(Enrollment.Grade.NOT_GRADED);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(i -> i.getArgument(0));

        Enrollment updated = enrollmentService.updateEnrollmentGrade(1L, Enrollment.Grade.A);

        assertEquals(Enrollment.Grade.A, updated.getGrade());
    }

    @Test
    void testDeleteEnrollment_Success() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        enrollmentService.deleteEnrollment(1L);

        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void testDeleteEnrollment_NotFound_ThrowsException() {
        when(enrollmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> enrollmentService.deleteEnrollment(99L));
    }
}
