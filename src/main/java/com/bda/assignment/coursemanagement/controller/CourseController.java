package com.bda.assignment.coursemanagement.controller;

// package main.java.com.bda.assignment.studentcourse.controller;

import com.bda.assignment.coursemanagement.model.Course;
import com.bda.assignment.coursemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String listCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "false") boolean showStudents,
            Model model) {
        int pageSize = 10;
        Page<Course> coursePage;
        if (showStudents) {
            coursePage = courseService.getAllCoursesWithStudentsPaginated(page, pageSize);
        } else {
            coursePage = courseService.getAllCoursesPaginated(page, pageSize);
        }

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalItems", coursePage.getTotalElements());
        model.addAttribute("showStudents", showStudents);

        return "course/list";
    }

    @GetMapping("/with-students")
    public String listCoursesWithStudents(Model model) {
        model.addAttribute("courses", courseService.getAllCoursesWithStudents());
        return "course/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/create";
    }

    @PostMapping("/create")
    public String createCourse(@Valid @ModelAttribute("course") Course course,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "course/create";
        }

        try {
            courseService.createCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course created successfully!");
            return "redirect:/courses";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("code", "error.course", "Course code already exists");
            return "course/create";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        try {
            Course course = courseService.getCourseById(id);
            model.addAttribute("course", course);
            return "course/update";
        } catch (Exception e) {
            return "redirect:/courses";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id,
            @Valid @ModelAttribute("course") Course course,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "course/update";
        }

        try {
            courseService.updateCourse(id, course);
            redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
            return "redirect:/courses";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("code", "error.course", "Course code already exists");
            return "course/update";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/courses";
    }
}