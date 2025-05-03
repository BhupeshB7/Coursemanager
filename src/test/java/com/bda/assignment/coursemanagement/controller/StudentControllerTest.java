package com.bda.assignment.coursemanagement.controller;

import com.bda.assignment.coursemanagement.model.Student;
import com.bda.assignment.coursemanagement.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john@example.com");
    }

    @Test
    void testListStudentsWithoutCourses() throws Exception {
        Page<Student> page = new PageImpl<>(List.of(student));
        when(studentService.getAllStudentsPaginated(0, 10)).thenReturn(page);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/list"))
                .andExpect(
                        model().attributeExists("students", "currentPage", "totalPages", "totalItems", "showCourses"));
    }

    @Test
    void testListStudentsWithCourses() throws Exception {
        Page<Student> page = new PageImpl<>(List.of(student));
        when(studentService.getAllStudentsWithCoursesPaginated(0, 10)).thenReturn(page);

        mockMvc.perform(get("/students").param("showCourses", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/list"))
                .andExpect(
                        model().attributeExists("students", "currentPage", "totalPages", "totalItems", "showCourses"));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/students/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/create"))
                .andExpect(model().attributeExists("student"));
    }

   

    @Test
    void testShowUpdateForm() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/update"))
                .andExpect(model().attributeExists("student"));
    }


    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(get("/students/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentService, times(1)).deleteStudent(1L);
    }
}
