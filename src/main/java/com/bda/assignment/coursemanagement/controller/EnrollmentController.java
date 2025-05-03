package com.bda.assignment.coursemanagement.controller;

import com.bda.assignment.coursemanagement.model.Course;
import com.bda.assignment.coursemanagement.model.Enrollment;
import com.bda.assignment.coursemanagement.model.Student;
import com.bda.assignment.coursemanagement.service.CourseService;
import com.bda.assignment.coursemanagement.service.EnrollmentService;
import com.bda.assignment.coursemanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import java.util.List;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public EnrollmentController(
            EnrollmentService enrollmentService,
            StudentService studentService,
            CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // @GetMapping
    // public String listEnrollments(Model model) {
    //     model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
    //     return "enrollment/list";
    // }
    @GetMapping
    public String listEnrollments(
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {
        int pageSize = 10;
        Page<Enrollment> enrollmentPage = enrollmentService.getEnrollmentsPage(page, pageSize);
        model.addAttribute("enrollments", enrollmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", enrollmentPage.getTotalPages());
        model.addAttribute("totalItems", enrollmentPage.getTotalElements());

        return "enrollment/list";
    }
    @GetMapping("/view/{id}")
    public String viewEnrollment(@PathVariable("id") Long id, Model model) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        model.addAttribute("enrollment", enrollment); 
        return "enrollment/enrollment-view";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Student> students = studentService.getAllStudents();
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        model.addAttribute("grades", Enrollment.Grade.values());

        return "enrollment/create";
    }

    @PostMapping("/create")
    public String createEnrollment(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            RedirectAttributes redirectAttributes) {

        try {
            Enrollment enrollment = new Enrollment();
            enrollmentService.createEnrollment(studentId, courseId, enrollment);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment created successfully!");
            return "redirect:/enrollments";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "redirect:/enrollments/create";
        }
    }

    @GetMapping("/student/{studentId}")
    public String getStudentEnrollments(@PathVariable Long studentId, Model model) {
        try {
            Student student = studentService.getStudentById(studentId);
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);

            model.addAttribute("student", student);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("grades", Enrollment.Grade.values());

            return "enrollment/student-enrollments";
        } catch (Exception e) {
            return "redirect:/students";
        }
    }

    @PostMapping("/update-grade/{id}")
    public String updateGrade(
            @PathVariable Long id,
            @RequestParam Enrollment.Grade grade,
            RedirectAttributes redirectAttributes) {

        try {
            Enrollment enrollment = enrollmentService.updateEnrollmentGrade(id, grade);
            redirectAttributes.addFlashAttribute("successMessage", "Grade updated successfully!");
            return "redirect:/enrollments/student/" + enrollment.getStudent().getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating grade: " + e.getMessage());
            return "redirect:/enrollments";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentById(id);
            Long studentId = enrollment.getStudent().getId();

            enrollmentService.deleteEnrollment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment deleted successfully!");

            return "redirect:/enrollments/student/" + studentId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting enrollment: " + e.getMessage());
            return "redirect:/enrollments";
        }
    }
}