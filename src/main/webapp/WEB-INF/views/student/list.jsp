<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Student List - Student Course Manager</title>
            <link rel="stylesheet" href="<c:url value='/css/style.css' />" />
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
           
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
                                <a href="<c:url value='/students' />"><i class="fa-solid fa-user-graduate"></i> Students</a>
                            </li>
                            <li>
                                <a href="<c:url value='/courses' />"><i class="fa-solid fa-book-open"></i> Courses</a>
                            </li>
                            <li>
                                <a href="<c:url value='/enrollments' />"><i class="fa-solid fa-clipboard-list"></i> Enrollments</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </header>


            <div class="container main_component">
                <h1 class="page-title">
                    <i class="fa-solid fa-user-graduate"></i> Student List
                </h1>

                <!-- Messages -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">
                        <i class="fa-solid fa-circle-check"></i> ${successMessage}
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-error">
                        <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
                    </div>
                </c:if>

                <!-- Action Buttons -->
                <div class="actions">
                    <a href="<c:url value='/students/create' />" class="btn btn-success">
                        <i class="fa-solid fa-plus"></i> Add New Student
                    </a>

                    <c:choose>
                        <c:when test="${showCourses}">
                            <c:url var="hideCoursesUrl" value="/students">
                                <c:param name="page" value="${currentPage}" />
                            </c:url>
                            <a href="${hideCoursesUrl}" class="btn">
                                <i class="fa-solid fa-list"></i> Hide Courses
                            </a>
                        </c:when>
                        <c:otherwise>
                            <c:url var="showCoursesUrl" value="/students">
                                <c:param name="page" value="${currentPage}" />
                                <c:param name="showCourses" value="true" />
                            </c:url>
                            <a href="${showCoursesUrl}" class="btn">
                                <i class="fa-solid fa-book-open"></i> View With Courses
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Pagination Info -->
                <div class="pagination-info">
                    Showing ${students.size()} of ${totalItems} items, Page ${currentPage + 1} of ${totalPages}
                </div>

                <!-- Student Table -->
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Semester</th>
                                <c:if test="${showCourses}">
                                    <th>Enrolled Courses</th>
                                </c:if>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${students}" var="student">
                                <tr>
                                    <td>${student.id}</td>
                                    <td>${student.name}</td>
                                    <td>${student.email}</td>
                                    <td>${student.phone}</td>
                                    <td>${student.semester}</td>
                                    <c:if test="${showCourses}">
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty student.enrollments}">
                                                    <ul class="course-list">
                                                        <c:forEach items="${student.enrollments}" var="enrollment">
                                                            <li>${enrollment.course.code} - ${enrollment.course.name}
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">No courses</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:if>
                                    <td class="actions">
                                        <a href="<c:url value='/students/update/${student.id}' />" class="btn">
                                            <i class="fa-solid fa-edit"></i>
                                        </a>
                                        <a href="<c:url value='/enrollments/student/${student.id}' />" class="btn">
                                            <i class="fa-solid fa-clipboard-list"></i>
                                        </a>
                                        <a href="<c:url value='/students/delete/${student.id}' />"
                                            class="btn btn-danger"
                                            onclick="return confirm('Are you sure you want to delete this student?')">
                                            <i class="fa-solid fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Pagination Controls -->
                <c:if test="${totalPages > 1}">
                    <ul class="pagination">
                        <!-- Previous -->
                        <li class="${currentPage == 0 ? 'disabled' : ''}">
                            <c:url var="prevUrl" value="/students">
                                <c:param name="page" value="${currentPage - 1}" />
                                <c:if test="${showCourses}">
                                    <c:param name="showCourses" value="true" />
                                </c:if>
                            </c:url>
                            <a href="${prevUrl}"><i class="fa-solid fa-chevron-left"></i></a>
                        </li>

                        <!-- Page Numbers -->
                        <c:forEach begin="0" end="${totalPages - 1}" var="i">
                            <c:choose>
                                <c:when test="${currentPage == i}">
                                    <li class="active"><a href="#">${i + 1}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="pageUrl" value="/students">
                                        <c:param name="page" value="${i}" />
                                        <c:if test="${showCourses}">
                                            <c:param name="showCourses" value="true" />
                                        </c:if>
                                    </c:url>
                                    <li><a href="${pageUrl}">${i + 1}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <!-- Next -->
                        <li class="${currentPage + 1 == totalPages ? 'disabled' : ''}">
                            <c:url var="nextUrl" value="/students">
                                <c:param name="page" value="${currentPage + 1}" />
                                <c:if test="${showCourses}">
                                    <c:param name="showCourses" value="true" />
                                </c:if>
                            </c:url>
                            <a href="${nextUrl}"><i class="fa-solid fa-chevron-right"></i></a>
                        </li>
                    </ul>
                </c:if>
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