package com.bda.assignment.coursemanagement.service;

import com.bda.assignment.coursemanagement.model.Course;
import com.bda.assignment.coursemanagement.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course testCourse;
    private List<Course> courseList;
    private Page<Course> coursePage;

    @BeforeEach
    public void setUp() {
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setCode("CS101");
        testCourse.setName("Introduction to Computer Science");
        testCourse.setCreditHours(3);
        testCourse.setDescription("An introductory course to computer science");

        courseList = new ArrayList<>();
        courseList.add(testCourse);

        coursePage = new PageImpl<>(courseList);
    }

    @Test
    public void testGetAllCourses() {
        when(courseRepository.findAll()).thenReturn(courseList);
        List<Course> result = courseService.getAllCourses();
        assertEquals(1, result.size());
        assertEquals(testCourse.getId(), result.get(0).getId());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCoursesWithStudents() {
        when(courseRepository.findAllCoursesWithStudents()).thenReturn(courseList);
        List<Course> result = courseService.getAllCoursesWithStudents();
        assertEquals(1, result.size());
        assertEquals(testCourse.getId(), result.get(0).getId());
        verify(courseRepository, times(1)).findAllCoursesWithStudents();
    }
    @Test
    public void testGetAllCoursesPaginated() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        when(courseRepository.findAll(pageable)).thenReturn(coursePage);
        Page<Course> result = courseService.getAllCoursesPaginated(0, 10);
        assertEquals(1, result.getContent().size());
        assertEquals(testCourse.getId(), result.getContent().get(0).getId());
        verify(courseRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetAllCoursesWithStudentsPaginated() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        when(courseRepository.findAllCoursesWithStudents(pageable)).thenReturn(coursePage);
        Page<Course> result = courseService.getAllCoursesWithStudentsPaginated(0, 10);
        assertEquals(1, result.getContent().size());
        assertEquals(testCourse.getId(), result.getContent().get(0).getId());
        verify(courseRepository, times(1)).findAllCoursesWithStudents(pageable);
    }

    @Test
    public void testGetCoursesByStudentIdPaginated() {
        Long studentId = 1L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        when(courseRepository.findCoursesByStudentId(studentId, pageable)).thenReturn(coursePage);
        Page<Course> result = courseService.getCoursesByStudentIdPaginated(studentId, 0, 10);
        assertEquals(1, result.getContent().size());
        assertEquals(testCourse.getId(), result.getContent().get(0).getId());
        verify(courseRepository, times(1)).findCoursesByStudentId(studentId, pageable);
    }

    @Test
    public void testGetCourseById_ExistingId() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));
        Course result = courseService.getCourseById(1L);
        assertNotNull(result);
        assertEquals(testCourse.getId(), result.getId());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCourseById_NonExistingId() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> courseService.getCourseById(999L));
        verify(courseRepository, times(1)).findById(999L);
    }

    @Test
    public void testCreateCourse_Success() {
        when(courseRepository.existsByCode(anyString())).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);
        Course result = courseService.createCourse(testCourse);
        assertNotNull(result);
        assertEquals(testCourse.getId(), result.getId());
        verify(courseRepository, times(1)).existsByCode(testCourse.getCode());
        verify(courseRepository, times(1)).save(testCourse);
    }

    @Test
    public void testCreateCourse_CodeAlreadyExists() {
        when(courseRepository.existsByCode(anyString())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> courseService.createCourse(testCourse));
        verify(courseRepository, times(1)).existsByCode(testCourse.getCode());
        verify(courseRepository, never()).save(any(Course.class));
    }
    @Test
    public void testUpdateCourse_WithNewCodeSuccess() { 
        Course existingCourse = new Course();
        existingCourse.setId(1L);
        existingCourse.setCode("CS101");
        existingCourse.setName("Old Name");

        Course updatedDetails = new Course();
        updatedDetails.setCode("CS102"); 
        updatedDetails.setName("New Name");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.existsByCode("CS102")).thenReturn(false); // New code doesn't exist
        when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);
 
        Course result = courseService.updateCourse(1L, updatedDetails);
 
        assertEquals("CS102", result.getCode());
        assertEquals("New Name", result.getName());

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).existsByCode("CS102");
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    public void testUpdateCourse_NewCodeAlreadyExists() { 
        Course existingCourse = new Course();
        existingCourse.setId(1L);
        existingCourse.setCode("CS101");

        Course updatedDetails = new Course();
        updatedDetails.setCode("CS102");  

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.existsByCode("CS102")).thenReturn(true); // Code already exists
 
        assertThrows(DataIntegrityViolationException.class,
                () -> courseService.updateCourse(1L, updatedDetails));

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).existsByCode("CS102");
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    public void testDeleteCourse() { 
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));
        doNothing().when(courseRepository).delete(any(Course.class));
 
        courseService.deleteCourse(1L); 
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).delete(testCourse);
    }

    @Test
    public void testSearchCoursesByName() { 
        String searchTerm = "Computer";
        when(courseRepository.findByNameContainingIgnoreCase(searchTerm)).thenReturn(courseList);
 
        List<Course> result = courseService.searchCoursesByName(searchTerm);
 
        assertEquals(1, result.size());
        assertEquals(testCourse.getId(), result.get(0).getId());
        verify(courseRepository, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }
}