package  com.bda.assignment.coursemanagement.service;

import com.bda.assignment.coursemanagement.model.Course;
import com.bda.assignment.coursemanagement.model.Enrollment;
import com.bda.assignment.coursemanagement.model.Student;
import com.bda.assignment.coursemanagement.repository.CourseRepository;
import com.bda.assignment.coursemanagement.repository.EnrollmentRepository;
import com.bda.assignment.coursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

     public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    
    // Add new paginated method
    public Page<Enrollment> getEnrollmentsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return enrollmentRepository.findAll(pageable);
    }
    
    public long getTotalEnrollments() {
        return enrollmentRepository.count();
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found with id: " + id));
    }

    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getStudentEnrollmentsWithCourses(Long studentId) {
        return enrollmentRepository.findEnrollmentsByStudentWithCourses(studentId);
    }

    @Transactional
    public Enrollment createEnrollment(Long studentId, Long courseId, Enrollment enrollment) {
        try {
            // Check if the student is already enrolled in the course
            if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
                throw new DataIntegrityViolationException("Student is already enrolled in this course");
            }

            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDate.now());
            enrollment.setGrade(Enrollment.Grade.NOT_GRADED);

            return enrollmentRepository.save(enrollment);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error creating enrollment: " + e.getMessage());
        }
    }

    @Transactional
    public Enrollment updateEnrollmentGrade(Long id, Enrollment.Grade grade) {
        Enrollment enrollment = getEnrollmentById(id);
        enrollment.setGrade(grade);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void deleteEnrollment(Long id) {
        Enrollment enrollment = getEnrollmentById(id);
        enrollmentRepository.delete(enrollment);
    }
}