package com.bda.assignment.coursemanagement.controller;

import com.bda.assignment.coursemanagement.model.*;
import com.bda.assignment.coursemanagement.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EnrollmentControllerTest {

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListEnrollments() {
        Page<Enrollment> enrollmentPage = new PageImpl<>(Collections.emptyList());
        when(enrollmentService.getEnrollmentsPage(0, 10)).thenReturn(enrollmentPage);

        String view = enrollmentController.listEnrollments(0, model);
        assertEquals("enrollment/list", view);
        verify(model).addAttribute("enrollments", enrollmentPage.getContent());
    }

  
    @Test
    void testViewEnrollment() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(enrollment);

        String view = enrollmentController.viewEnrollment(1L, model);
        assertEquals("enrollment/enrollment-view", view);
        verify(model).addAttribute("enrollment", enrollment);
    }

    @Test
    void testDeleteEnrollment() {
        Enrollment enrollment = new Enrollment();
        Student student = new Student();
        student.setId(1L);
        enrollment.setStudent(student);

        when(enrollmentService.getEnrollmentById(1L)).thenReturn(enrollment);

        String result = enrollmentController.deleteEnrollment(1L, redirectAttributes);
        assertEquals("redirect:/enrollments/student/1", result);
        verify(enrollmentService).deleteEnrollment(1L);
    }

    @Test
    void testUpdateGradeSuccess() {
        Enrollment enrollment = new Enrollment();
        Student student = new Student();
        student.setId(1L);
        enrollment.setStudent(student);
        enrollment.setId(1L);

        when(enrollmentService.updateEnrollmentGrade(eq(1L), any())).thenReturn(enrollment);

        String result = enrollmentController.updateGrade(1L, Enrollment.Grade.A, redirectAttributes);
        assertEquals("redirect:/enrollments/student/1", result);
    }
}
