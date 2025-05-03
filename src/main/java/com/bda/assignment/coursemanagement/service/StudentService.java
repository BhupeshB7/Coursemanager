package com.bda.assignment.coursemanagement.service;

import com.bda.assignment.coursemanagement.model.Student;
import com.bda.assignment.coursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
  
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Page<Student> getAllStudentsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return studentRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Page<Student> getAllStudentsWithCoursesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return studentRepository.findAllStudentsWithCourses(pageable);
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudentsWithCourses() {
        return studentRepository.findAllStudentsWithCourses();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Student getStudentWithCoursesById(Long id) {
        Student student = getStudentById(id);
        if (student.getEnrollments() != null) {
            student.getEnrollments().size();
        }
        return student;
    }

    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public Student createStudent(Student student) {
        try {
            if (studentRepository.existsByEmail(student.getEmail())) {
                throw new DataIntegrityViolationException("Email already exists");
            }
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error creating student: " + e.getMessage());
        }
    }

    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
       if (!student.getEmail().equals(studentDetails.getEmail()) &&
                studentRepository.existsByEmail(studentDetails.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setAddress(studentDetails.getAddress());
        student.setSemester(studentDetails.getSemester());

        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}