package com.bda.assignment.coursemanagement.service;

import com.bda.assignment.coursemanagement.model.Course;
import com.bda.assignment.coursemanagement.model.Student;
import com.bda.assignment.coursemanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getAllCoursesWithStudents() {
        return courseRepository.findAllCoursesWithStudents();
    }
 
     public Page<Course> getAllCoursesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return courseRepository.findAll(pageable);
    } 
    
    @Transactional(readOnly = true)
    public Page<Course> getAllCoursesWithStudentsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return courseRepository.findAllCoursesWithStudents(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Course> getCoursesByStudentIdPaginated(Long studentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return courseRepository.findCoursesByStudentId(studentId, pageable);
    }
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with ID: " + id));
    }

    @Transactional
    public Course createCourse(Course course) {
        if (courseRepository.existsByCode(course.getCode())) {
            throw new DataIntegrityViolationException("Course code already exists: " + course.getCode());
        }
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        Course existingCourse = getCourseById(id);

        // Check if another course already has this code (excluding current course)
        if (!existingCourse.getCode().equals(courseDetails.getCode()) &&
                courseRepository.existsByCode(courseDetails.getCode())) {
            throw new DataIntegrityViolationException("Course code already exists: " + courseDetails.getCode());
        }

        existingCourse.setCode(courseDetails.getCode());
        existingCourse.setName(courseDetails.getName());
        existingCourse.setDescription(courseDetails.getDescription());
        existingCourse.setCreditHours(courseDetails.getCreditHours());

        return courseRepository.save(existingCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    public List<Course> searchCoursesByName(String name) {
        return courseRepository.findByNameContainingIgnoreCase(name);
    }
}