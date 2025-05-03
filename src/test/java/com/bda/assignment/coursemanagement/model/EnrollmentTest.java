package com.bda.assignment.coursemanagement.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentTest {

    @Test
    public void testEnrollmentGettersAndSetters() {
        // Arrange
        Enrollment enrollment = new Enrollment();
        Long id = 1L;
        Enrollment.Grade grade = Enrollment.Grade.A;

        Student student = new Student();
        student.setId(100L);
        student.setName("Test Student");

        Course course = new Course();
        course.setId(200L);
        course.setName("Test Course");

        LocalDate enrollmentDate = LocalDate.of(2024, 10, 1);

        // Act
        enrollment.setId(id);
        enrollment.setGrade(grade);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(enrollmentDate);

        // Assert
        assertEquals(id, enrollment.getId());
        assertEquals(grade, enrollment.getGrade());
        assertEquals(student, enrollment.getStudent());
        assertEquals(course, enrollment.getCourse());
        assertEquals(enrollmentDate, enrollment.getEnrollmentDate());
    }

    @Test
    public void testConstructorWithArguments() {
        Student student = new Student();
        student.setId(101L);
        Course course = new Course();
        course.setId(201L);
        LocalDate date = LocalDate.now();

        Enrollment enrollment = new Enrollment(1L, Enrollment.Grade.B, student, course, date);

        assertEquals(1L, enrollment.getId());
        assertEquals(Enrollment.Grade.B, enrollment.getGrade());
        assertEquals(student, enrollment.getStudent());
        assertEquals(course, enrollment.getCourse());
        assertEquals(date, enrollment.getEnrollmentDate());
    }

    @Test
    public void testGradeEnumValues() {
        assertEquals("NOT_GRADED", Enrollment.Grade.NOT_GRADED.name());
        assertEquals("A", Enrollment.Grade.A.name());
        assertEquals("B", Enrollment.Grade.B.name());
        assertEquals("C", Enrollment.Grade.C.name());
        assertEquals("D", Enrollment.Grade.D.name());
        assertEquals("F", Enrollment.Grade.F.name());
    }
}
