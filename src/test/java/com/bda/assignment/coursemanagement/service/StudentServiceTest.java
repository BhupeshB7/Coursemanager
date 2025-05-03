package com.bda.assignment.coursemanagement.service;

import com.bda.assignment.coursemanagement.model.Student;
import com.bda.assignment.coursemanagement.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john@example.com");
        student.setPhone("1234567890");
        student.setAddress("Test Address");
        student.setSemester("5");
    }

    @Test
    void testGetAllStudentsPaginated() {
        Page<Student> page = new PageImpl<>(List.of(student));
        when(studentRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Student> result = studentService.getAllStudentsPaginated(0, 10);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        List<Student> result = studentService.getAllStudents();
        assertEquals(1, result.size());
    }

    @Test
    void testGetStudentByIdExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student result = studentService.getStudentById(1L);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetStudentByIdNotExists() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(2L));
    }

    @Test
    void testSearchStudentsByName() {
        when(studentRepository.findByNameContainingIgnoreCase("john")).thenReturn(List.of(student));
        List<Student> result = studentService.searchStudentsByName("john");
        assertEquals(1, result.size());
    }

    @Test
    void testCreateStudentWithUniqueEmail() {
        when(studentRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.createStudent(student);
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testCreateStudentWithExistingEmail() {
        when(studentRepository.existsByEmail("john@example.com")).thenReturn(true);
        assertThrows(DataIntegrityViolationException.class, () -> studentService.createStudent(student));
    }

    @Test
    void testUpdateStudentWithNewUniqueEmail() {
        Student updated = new Student();
        updated.setEmail("new@example.com");
        updated.setName("New Name");
        updated.setPhone("9876543210");
        updated.setAddress("New Address");
        updated.setSemester("6");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(updated);

        Student result = studentService.updateStudent(1L, updated);
        assertEquals("new@example.com", result.getEmail());
        assertEquals("New Name", result.getName());
    }

    @Test
    void testUpdateStudentWithDuplicateEmail() {
        Student updated = new Student();
        updated.setEmail("duplicate@example.com");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.existsByEmail("duplicate@example.com")).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class,
                () -> studentService.updateStudent(1L, updated));
    }

    @Test
    void testDeleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        assertDoesNotThrow(() -> studentService.deleteStudent(1L));
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void testGetAllStudentsWithCourses() {
        when(studentRepository.findAllStudentsWithCourses()).thenReturn(List.of(student));
        List<Student> result = studentService.getAllStudentsWithCourses();
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllStudentsWithCoursesPaginated() {
        Page<Student> page = new PageImpl<>(List.of(student));
        when(studentRepository.findAllStudentsWithCourses(any(Pageable.class))).thenReturn(page);
        Page<Student> result = studentService.getAllStudentsWithCoursesPaginated(0, 10);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testGetStudentWithCoursesById() {
        student.setEnrollments(new HashSet<>());
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentWithCoursesById(1L);
        assertEquals(0, result.getEnrollments().size());
    }
}
