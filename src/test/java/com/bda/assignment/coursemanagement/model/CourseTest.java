package com.bda.assignment.coursemanagement.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class CourseTest {

    @Test
    public void testCourseConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String code = "CS101";
        String name = "Introduction to Computer Science";
        Integer creditHours = 3;
        String description = "An introductory course to computer science";
        HashSet<Enrollment> enrollments = new HashSet<>();

        // Act
        Course course = new Course(id, code, name, creditHours, description, enrollments);

        // Assert
        assertEquals(id, course.getId());
        assertEquals(code, course.getCode());
        assertEquals(name, course.getName());
        assertEquals(creditHours, course.getCreditHours());
        assertEquals(description, course.getDescription());
        assertEquals(enrollments, course.getEnrollments());
    }

    @Test
    public void testCourseSetters() {
        // Arrange
        Course course = new Course();
        Long id = 1L;
        String code = "CS101";
        String name = "Introduction to Computer Science";
        Integer creditHours = 3;
        String description = "An introductory course to computer science";
        HashSet<Enrollment> enrollments = new HashSet<>();

        // Act
        course.setId(id);
        course.setCode(code);
        course.setName(name);
        course.setCreditHours(creditHours);
        course.setDescription(description);
        course.setEnrollments(enrollments);

        // Assert
        assertEquals(id, course.getId());
        assertEquals(code, course.getCode());
        assertEquals(name, course.getName());
        assertEquals(creditHours, course.getCreditHours());
        assertEquals(description, course.getDescription());
        assertEquals(enrollments, course.getEnrollments());
    }

    @Test
    public void testAddEnrollment() {
        // Arrange
        Course course = new Course();
        Enrollment enrollment = new Enrollment();

        // Act
        course.addEnrollment(enrollment);

        // Assert
        assertTrue(course.getEnrollments().contains(enrollment));
        assertEquals(course, enrollment.getCourse());
    }

    @Test
    public void testRemoveEnrollment() {
        // Arrange
        Course course = new Course();
        Enrollment enrollment = new Enrollment();
        course.addEnrollment(enrollment);

        // Pre-condition check
        assertTrue(course.getEnrollments().contains(enrollment));

        // Act
        course.removeEnrollment(enrollment);

        // Assert
        assertFalse(course.getEnrollments().contains(enrollment));
        assertNull(enrollment.getCourse());
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        Course course = new Course();

        // Assert
        assertNull(course.getId());
        assertNull(course.getCode());
        assertNull(course.getName());
        assertNull(course.getCreditHours());
        assertNull(course.getDescription());
        assertNotNull(course.getEnrollments());
        assertTrue(course.getEnrollments().isEmpty());
    }
}