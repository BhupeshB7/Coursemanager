package com.bda.assignment.coursemanagement.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testStudentCreation() {
        // Given
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("1234567890");
        student.setAddress("123 Main St");
        student.setSemester("Fall 2023");

        // Then
        assertEquals(1L, student.getId());
        assertEquals("John Doe", student.getName());
        assertEquals("john.doe@example.com", student.getEmail());
        assertEquals("1234567890", student.getPhone());
        assertEquals("123 Main St", student.getAddress());
        assertEquals("Fall 2023", student.getSemester());
        assertNotNull(student.getEnrollments());
        assertTrue(student.getEnrollments().isEmpty());
    }

    @Test
    public void testStudentConstructorWithParameters() {
        // Given
        Set<Enrollment> enrollments = new HashSet<>();
        Student student = new Student("Jane Doe", "jane.doe@example.com", "9876543210",
                "456 Oak St", "Spring 2024", enrollments);

        // Then
        assertNull(student.getId()); // ID should be null until persisted
        assertEquals("Jane Doe", student.getName());
        assertEquals("jane.doe@example.com", student.getEmail());
        assertEquals("9876543210", student.getPhone());
        assertEquals("456 Oak St", student.getAddress());
        assertEquals("Spring 2024", student.getSemester());
        assertSame(enrollments, student.getEnrollments());
    }

    @Test
    public void testNameValidation() {
        // Given
        Student student = createValidStudent();

        // Test 1: Valid name
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertTrue(violations.isEmpty());

        // Test 2: Null name
        student.setName(null);
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Name is required", violations.iterator().next().getMessage());

        // Test 3: Empty name
        student.setName("");
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Name is required", violations.iterator().next().getMessage());

        // Test 4: Name too long
        String longName = "a".repeat(101);
        student.setName(longName);
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Name must be less than 100 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailValidation() {
        // Given
        Student student = createValidStudent();

        // Test 1: Valid email
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertTrue(violations.isEmpty());

        // Test 2: Null email
        student.setEmail(null);
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Email is required", violations.iterator().next().getMessage());

        // Test 3: Empty email
        student.setEmail("");
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Email is required", violations.iterator().next().getMessage());

        // Test 4: Invalid email format
        student.setEmail("not-an-email");
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Invalid email format", violations.iterator().next().getMessage());
    }

  

    @Test
    public void testAddressValidation() {
        // Given
        Student student = createValidStudent();

        // Test 1: Valid address
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertTrue(violations.isEmpty());

        // Test 2: Null address (should be valid as not marked @NotBlank)
        student.setAddress(null);
        violations = validator.validate(student);
        assertTrue(violations.isEmpty());

        // Test 3: Address too long
        String longAddress = "a".repeat(201);
        student.setAddress(longAddress);
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Address must be less than 200 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testSemesterValidation() {
        // Given
        Student student = createValidStudent();

        // Test 1: Valid semester
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertTrue(violations.isEmpty());

        // Test 2: Null semester
        student.setSemester(null);
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Semester is required", violations.iterator().next().getMessage());

        // Test 3: Empty semester
        student.setSemester("");
        violations = validator.validate(student);
        assertEquals(1, violations.size());
        assertEquals("Semester is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testBidirectionalRelationship() {
        // Given
        Student student = createValidStudent();
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        // When - Add enrollment to student
        student.addEnrollment(enrollment);

        // Then
        assertTrue(student.getEnrollments().contains(enrollment));
        assertEquals(student, enrollment.getStudent());

        // When - Remove enrollment from student
        student.removeEnrollment(enrollment);

        // Then
        assertFalse(student.getEnrollments().contains(enrollment));
        assertNull(enrollment.getStudent());
    }

    @Test
    public void testSetEnrollments() {
        // Given
        Student student = createValidStudent();
        Set<Enrollment> enrollments = new HashSet<>();
        Enrollment enrollment1 = new Enrollment();
        Enrollment enrollment2 = new Enrollment();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);

        // When
        student.setEnrollments(enrollments);

        // Then
        assertEquals(2, student.getEnrollments().size());
        assertTrue(student.getEnrollments().contains(enrollment1));
        assertTrue(student.getEnrollments().contains(enrollment2));
    }

    private Student createValidStudent() {
        Student student = new Student();
        student.setName("Test Student");
        student.setEmail("test@example.com");
        student.setPhone("1234567890");
        student.setAddress("Test Address");
        student.setSemester("Test Semester");
        return student;
    }

}