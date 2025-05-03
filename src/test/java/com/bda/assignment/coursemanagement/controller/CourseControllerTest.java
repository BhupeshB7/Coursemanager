package com.bda.assignment.coursemanagement.controller;

import com.bda.assignment.coursemanagement.model.Course;
import com.bda.assignment.coursemanagement.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CourseController courseController;

    private Course testCourse;
    private List<Course> courseList;
    private Page<Course> coursePage;

    @BeforeEach
    public void setUp() {
        // Initialize test data
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
    public void testListCourses_WithoutStudents() {
        // Arrange
        when(courseService.getAllCoursesPaginated(anyInt(), anyInt())).thenReturn(coursePage);

        // Act
        String viewName = courseController.listCourses(0, false, model);

        // Assert
        assertEquals("course/list", viewName);
        verify(courseService, times(1)).getAllCoursesPaginated(0, 10);
        verify(courseService, never()).getAllCoursesWithStudentsPaginated(anyInt(), anyInt());
        verify(model, times(1)).addAttribute(eq("courses"), any());
        verify(model, times(1)).addAttribute(eq("currentPage"), eq(0));
        verify(model, times(1)).addAttribute(eq("totalPages"), any());
        verify(model, times(1)).addAttribute(eq("totalItems"), any());
        verify(model, times(1)).addAttribute(eq("showStudents"), eq(false));
    }

    @Test
    public void testListCourses_WithStudents() {
        // Arrange
        when(courseService.getAllCoursesWithStudentsPaginated(anyInt(), anyInt())).thenReturn(coursePage);

        // Act
        String viewName = courseController.listCourses(0, true, model);

        // Assert
        assertEquals("course/list", viewName);
        verify(courseService, times(1)).getAllCoursesWithStudentsPaginated(0, 10);
        verify(courseService, never()).getAllCoursesPaginated(anyInt(), anyInt());
        verify(model, times(1)).addAttribute(eq("courses"), any());
        verify(model, times(1)).addAttribute(eq("currentPage"), eq(0));
        verify(model, times(1)).addAttribute(eq("totalPages"), any());
        verify(model, times(1)).addAttribute(eq("totalItems"), any());
        verify(model, times(1)).addAttribute(eq("showStudents"), eq(true));
    }

    @Test
    public void testListCoursesWithStudents() {
        // Arrange
        when(courseService.getAllCoursesWithStudents()).thenReturn(courseList);

        // Act
        String viewName = courseController.listCoursesWithStudents(model);

        // Assert
        assertEquals("course/list", viewName);
        verify(courseService, times(1)).getAllCoursesWithStudents();
        verify(model, times(1)).addAttribute(eq("courses"), eq(courseList));
    }

    @Test
    public void testShowCreateForm() {
        // Act
        String viewName = courseController.showCreateForm(model);

        // Assert
        assertEquals("course/create", viewName);
        verify(model, times(1)).addAttribute(eq("course"), any(Course.class));
    }

    @Test
    public void testCreateCourse_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseService.createCourse(any(Course.class))).thenReturn(testCourse);

        // Act
        String viewName = courseController.createCourse(testCourse, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/courses", viewName);
        verify(courseService, times(1)).createCourse(testCourse);
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("successMessage"), anyString());
    }

    @Test
    public void testCreateCourse_ValidationError() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = courseController.createCourse(testCourse, bindingResult, redirectAttributes);

        // Assert
        assertEquals("course/create", viewName);
        verify(courseService, never()).createCourse(any(Course.class));
    }

    @Test
    public void testCreateCourse_DuplicateCode() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseService.createCourse(any(Course.class)))
                .thenThrow(new DataIntegrityViolationException("Course code already exists"));

        // Act
        String viewName = courseController.createCourse(testCourse, bindingResult, redirectAttributes);

        // Assert
        assertEquals("course/create", viewName);
        verify(bindingResult, times(1)).rejectValue(eq("code"), eq("error.course"), anyString());
    }

    @Test
    public void testShowUpdateForm_ExistingCourse() {
        // Arrange
        when(courseService.getCourseById(anyLong())).thenReturn(testCourse);

        // Act
        String viewName = courseController.showUpdateForm(1L, model);

        // Assert
        assertEquals("course/update", viewName);
        verify(courseService, times(1)).getCourseById(1L);
        verify(model, times(1)).addAttribute(eq("course"), eq(testCourse));
    }

    @Test
    public void testShowUpdateForm_NonExistingCourse() {
        // Arrange
        when(courseService.getCourseById(anyLong())).thenThrow(new NoSuchElementException());

        // Act
        String viewName = courseController.showUpdateForm(999L, model);

        // Assert
        assertEquals("redirect:/courses", viewName);
        verify(courseService, times(1)).getCourseById(999L);
    }

    @Test
    public void testUpdateCourse_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseService.updateCourse(anyLong(), any(Course.class))).thenReturn(testCourse);

        // Act
        String viewName = courseController.updateCourse(1L, testCourse, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/courses", viewName);
        verify(courseService, times(1)).updateCourse(1L, testCourse);
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("successMessage"), anyString());
    }

    @Test
    public void testUpdateCourse_ValidationError() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = courseController.updateCourse(1L, testCourse, bindingResult, redirectAttributes);

        // Assert
        assertEquals("course/update", viewName);
        verify(courseService, never()).updateCourse(anyLong(), any(Course.class));
    }

    @Test
    public void testUpdateCourse_DuplicateCode() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseService.updateCourse(anyLong(), any(Course.class)))
                .thenThrow(new DataIntegrityViolationException("Course code already exists"));

        // Act
        String viewName = courseController.updateCourse(1L, testCourse, bindingResult, redirectAttributes);

        // Assert
        assertEquals("course/update", viewName);
        verify(bindingResult, times(1)).rejectValue(eq("code"), eq("error.course"), anyString());
    }

    @Test
    public void testDeleteCourse_Success() {
        // Arrange
        doNothing().when(courseService).deleteCourse(anyLong());

        // Act
        String viewName = courseController.deleteCourse(1L, redirectAttributes);

        // Assert
        assertEquals("redirect:/courses", viewName);
        verify(courseService, times(1)).deleteCourse(1L);
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("successMessage"), anyString());
    }

    @Test
    public void testDeleteCourse_Failure() {
        // Arrange
        doThrow(new RuntimeException("Delete error")).when(courseService).deleteCourse(anyLong());

        // Act
        String viewName = courseController.deleteCourse(1L, redirectAttributes);

        // Assert
        assertEquals("redirect:/courses", viewName);
        verify(courseService, times(1)).deleteCourse(1L);
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("errorMessage"), anyString());
    }
}