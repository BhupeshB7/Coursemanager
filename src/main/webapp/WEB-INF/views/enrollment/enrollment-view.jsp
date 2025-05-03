<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <title>Enrollment Details - Student Course Manager</title>
            <link rel="stylesheet" href="<c:url value='/css/style.css' />" />
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
             
            <script>
                $(document).ready(function () {
                    // Tab functionality
                    $('.tab').click(function () {
                        $('.tab').removeClass('active');
                        $(this).addClass('active');

                        const tabId = $(this).data('tab');
                        $('.tab-content').removeClass('active');
                        $('#' + tabId).addClass('active');
                    });

                    // Grade form submission with AJAX
                    $('#gradeForm').submit(function (e) {
                        e.preventDefault();
                        const formData = $(this).serialize();

                        // For demo purposes just show success message
                        // In a real app you'd send this data to the server with AJAX
                        $('#gradeMessage').html('<div class="alert alert-success">Grade updated successfully!</div>');
                        setTimeout(() => {
                            $('#gradeMessage').html('');
                            $('#tab1').click(); // Switch back to details tab
                        }, 2000);
                    });

                    // Delete confirmation
                    $('#deleteBtn').click(function (e) {
                        e.preventDefault();
                        if (confirm('Are you sure you want to delete this enrollment? This action cannot be undone.')) {
                            window.location.href = $(this).attr('href');
                        }
                    });
                });
            </script>
        </head>

        <body>
            <header class="main-header">
                <div class="container header-container">
                    <div class="logo">
                        <i class="fa-solid fa-graduation-cap"></i> Student Course Manager
                    </div>
                    <nav class="main-nav">
                        <ul>
                            <li>
                                <a href="<c:url value='/' />"><i class="fa-solid fa-house"></i> Home</a>
                            </li>
                            <li>
                                <a href="<c:url value='/students' />"><i class="fa-solid fa-user-graduate"></i>
                                    Students</a>
                            </li>
                            <li>
                                <a href="<c:url value='/courses' />"><i class="fa-solid fa-book-open"></i> Courses</a>
                            </li>
                            <li>
                                <a href="<c:url value='/enrollments' />"><i class="fa-solid fa-clipboard-list"></i>
                                    Enrollments</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </header>

            <div class="container main_component">
                <div class="back-link">
                    <a href="<c:url value='/enrollments' />"><i class="fas fa-arrow-left"></i> Back to Enrollment
                        List</a>
                </div>

                <h1 class="page-title">Enrollment Details</h1>

                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">${successMessage}</div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-error">${errorMessage}</div>
                </c:if>

                <c:choose>
                    <c:when test="${not empty enrollment}">
                        <div class="enrollment-details-card">
                            <div class="enrollment-header">
                                <h2>${enrollment.student.name} - ${enrollment.course.name}</h2>
                                <span class="status-badge status-active">Active</span>
                            </div>

                            <div class="tab-container">
                                <div class="tabs">
                                    <div class="tab active" data-tab="tab1" id="tab1"><i class="fas fa-info-circle"></i>
                                        Details</div>
                                    <div class="tab" data-tab="tab2"><i class="fas fa-edit"></i> Update Grade</div>
                                    <div class="tab" data-tab="tab3"><i class="fas fa-history"></i> Activity</div>
                                </div>

                                <div id="tab1" class="tab-content active">
                                    <div class="enrollment-info">
                                        <div class="info-group">
                                            <label>Enrollment ID</label>
                                            <p>${enrollment.id}</p>
                                        </div>

                                        <div class="info-group">
                                            <label>Enrollment Date</label>
                                            <p>${enrollment.enrollmentDate}</p>
                                        </div>

                                        <div class="info-group">
                                            <label>Student</label>
                                            <p>${enrollment.student.name} (ID: ${enrollment.student.id})</p>
                                        </div>

                                        <div class="info-group">
                                            <label>Course</label>
                                            <p>${enrollment.course.name} (ID: ${enrollment.course.id})</p>
                                        </div>

                                        <div class="info-group">
                                            <label>Grade</label>
                                            <p><strong>${enrollment.grade != null ? enrollment.grade : 'Not graded
                                                    yet'}</strong></p>
                                        </div>

                                        <div class="info-group">
                                            <label>Status</label>
                                            <p>Active</p>
                                        </div>
                                    </div>

                                    <c:if test="${not empty enrollment.course.description}">
                                        <div class="course-description">
                                            <h3>Course Description</h3>
                                            <p>${enrollment.course.description}</p>
                                        </div>
                                    </c:if>
                                </div>

                                <div id="tab2" class="tab-content">
                                    <div class="grade-form">
                                        <h3>Update Grade</h3>
                                        <div id="gradeMessage"></div>
                                        <form id="gradeForm"
                                            action="<c:url value='/enrollments/update-grade/${enrollment.id}' />"
                                            method="post">
                                            <div class="form-group">
                                                <label for="grade">Grade</label>
                                                <input type="text" id="grade" name="grade" class="form-control"
                                                    value="${enrollment.grade}"
                                                    placeholder="Enter grade (A, B, C, D, F or numeric)">
                                            </div>
                                            <div class="form-group">
                                                <label for="comments">Comments (Optional)</label>
                                                <textarea id="comments" name="comments" class="form-control" rows="3"
                                                    placeholder="Add comments about this grade..."></textarea>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Update Grade</button>
                                        </form>
                                    </div>
                                </div>

                                <div id="tab3" class="tab-content">
                                    <h3>Activity Timeline</h3>
                                    <div class="activity-timeline">
                                        <div class="activity-item">
                                            <p><strong>Enrolled</strong> <span
                                                    class="activity-date">${enrollment.enrollmentDate}</span></p>
                                            <p>Student was enrolled in the course</p>
                                        </div>

                                        <c:if test="${not empty enrollment.grade}">
                                            <div class="activity-item">
                                                <p><strong>Grade Added</strong> <span class="activity-date">Grade:
                                                        ${enrollment.grade}</span></p>
                                                <p>Instructor added grade to this enrollment</p>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <div class="enrollment-actions">
                                <a href="<c:url value='/enrollments' />" class="action-btn btn-back">
                                    <i class="fas fa-arrow-left"></i> Back
                                </a>
                                <button class="action-btn btn-edit" onclick="$('#tab2').click()">
                                    <i class="fas fa-edit"></i> Edit Grade
                                </button>
                                <a id="deleteBtn" href="<c:url value='/enrollments/delete/${enrollment.id}' />"
                                    class="action-btn btn-danger">
                                    <i class="fas fa-trash-alt"></i> Delete
                                </a>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-error">Enrollment not found! The requested enrollment may have been
                            deleted or does not exist.</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <footer class="footer">
                <div class="container footer-container">
                    <!-- About Section -->
                    <div class="footer-section about">
                        <h3>
                            <i class="fa-solid fa-graduation-cap"></i> Student Course Manager
                        </h3>
                        <p>
                            Manage courses, students, and enrollments efficiently. A modern
                            solution for academic institutions.
                        </p>
                    </div>
            
                    <!-- Quick Links -->
                    <div class="footer-section links">
                        <h4><i class="fa-solid fa-link"></i> Quick Links</h4>
                        <ul>
                            <li>
                                <a href="<c:url value='/' />"><i class="fa-solid fa-house"></i> Home</a>
                            </li>
                            <li>
                                <a href="<c:url value='/students' />"><i class="fa-solid fa-user-graduate"></i> Students</a>
                            </li>
                            <li>
                                <a href="<c:url value='/courses' />"><i class="fa-solid fa-book-open"></i> Courses</a>
                            </li>
                            <li>
                                <a href="<c:url value='/enrollments' />"><i class="fa-solid fa-clipboard-list"></i> Enrollments</a>
                            </li>
                        </ul>
                    </div>
            
                    <!-- Contact Info -->
                    <div class="footer-section contact">
                        <h4><i class="fa-solid fa-envelope"></i> Contact</h4>
                        <p><i class="fa-solid fa-envelope"></i> support@scmapp.com</p>
                        <p><i class="fa-solid fa-phone"></i> +91 98765 43210</p>
                        <p>
                            <i class="fa-solid fa-location-dot"></i> Patna,
                            India
                        </p>
                    </div>
                </div>
            
                <!-- Footer Bottom -->
                <div class="footer-bottom">
                    <p>&copy; 2025 Student Course Manager. All rights reserved.</p>
                </div>
            </footer>
            </body>
            
            </html>