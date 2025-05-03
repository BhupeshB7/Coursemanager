<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Course List - Student Course Manager</title>
            <link rel="stylesheet" href="<c:url value='/css/style.css' />" />
            <!-- Font Awesome CDN -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
            <style>
                .pagination {
                    display: flex;
                    justify-content: center;
                    margin: 20px 0;
                    list-style: none;
                    padding: 0;
                }

                .pagination li {
                    margin: 0 5px;
                }

                .pagination a {
                    display: inline-block;
                    padding: 8px 16px;
                    text-decoration: none;
                    color: #333;
                    background-color: #f4f4f4;
                    border-radius: 4px;
                    transition: background-color 0.3s;
                }

                .pagination a:hover {
                    background-color: #ddd;
                }

                .pagination .active a {
                    background-color: #4CAF50;
                    color: white;
                }

                .pagination .disabled a {
                    color: #aaa;
                    cursor: not-allowed;
                }

                .pagination-info {
                    text-align: center;
                    margin-bottom: 10px;
                    font-size: 0.9em;
                    color: #666;
                }
            </style>
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
                <h1 class="page-title"><i class="fas fa-book"></i> Course List</h1>

                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">
                        <i class="fas fa-check-circle"></i> ${successMessage}
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-error">
                        <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                    </div>
                </c:if>

                <div class="actions">
                    <a href="<c:url value='/courses/create' />" class="btn btn-success">
                        <i class="fas fa-plus-circle"></i> Add New Course
                    </a>

                    <c:choose>
                        <c:when test="${showStudents}">
                            <c:url var="hideStudentsUrl" value="/courses">
                                <c:param name="page" value="${currentPage}" />
                            </c:url>
                            <a href="${hideStudentsUrl}" class="btn">
                                <i class="fas fa-list"></i> Hide Students
                            </a>
                        </c:when>
                        <c:otherwise>
                            <c:url var="showStudentsUrl" value="/courses">
                                <c:param name="page" value="${currentPage}" />
                                <c:param name="showStudents" value="true" />
                            </c:url>
                            <a href="${showStudentsUrl}" class="btn">
                                <i class="fas fa-users"></i> View With Students
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Pagination Info -->
                <div class="pagination-info">
                    Showing ${courses.size()} of ${totalItems} items, Page ${currentPage + 1} of ${totalPages}
                </div>

                <div class="table-container">
                    <c:choose>
                        <c:when test="${not empty courses}">
                            <table>
                                <thead>
                                    <tr>
                                        <th><i class="fas fa-hashtag"></i> ID</th>
                                        <th><i class="fas fa-code"></i> Code</th>
                                        <th><i class="fas fa-book"></i> Name</th>
                                        <th><i class="fas fa-clock"></i> Credit Hours</th>
                                        <th><i class="fas fa-align-left"></i> Description</th>
                                        <c:if test="${showStudents}">
                                            <th>
                                                <i class="fas fa-user-graduate"></i> Enrolled Students
                                            </th>
                                        </c:if>
                                        <th><i class="fas fa-cogs"></i> Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${courses}" var="course">
                                        <tr>
                                            <td>${course.id}</td>
                                            <td>${course.code}</td>
                                            <td>${course.name}</td>
                                            <td>${course.creditHours}</td>
                                            <td>${course.description}</td>
                                            <c:if test="${showStudents}">
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty course.enrollments}">
                                                            <ul class="student-list">
                                                                <c:forEach items="${course.enrollments}"
                                                                    var="enrollment">
                                                                    <li>
                                                                        ${enrollment.student.name}
                                                                        (${enrollment.student.email})
                                                                    </li>
                                                                </c:forEach>
                                                            </ul>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">No students enrolled</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </c:if>
                                            <td class="actions">
                                                <a href="<c:url value='/courses/update/${course.id}' />"
                                                    class="btn actions-button">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="<c:url value='/courses/delete/${course.id}' />"
                                                    class="btn btn-danger actions-button"
                                                    onclick="return confirm('Are you sure you want to delete this course?')">
                                                    <i class="fas fa-trash-alt"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-info">
                                <i class="fas fa-info-circle"></i> No Courses Found
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Pagination Controls -->
                <c:if test="${totalPages > 1}">
                    <ul class="pagination">
                        <!-- Previous -->
                        <li class="${currentPage == 0 ? 'disabled' : ''}">
                            <c:url var="prevUrl" value="/courses">
                                <c:param name="page" value="${currentPage - 1}" />
                                <c:if test="${showStudents}">
                                    <c:param name="showStudents" value="true" />
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
                                    <c:url var="pageUrl" value="/courses">
                                        <c:param name="page" value="${i}" />
                                        <c:if test="${showStudents}">
                                            <c:param name="showStudents" value="true" />
                                        </c:if>
                                    </c:url>
                                    <li><a href="${pageUrl}">${i + 1}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <!-- Next -->
                        <li class="${currentPage + 1 == totalPages ? 'disabled' : ''}">
                            <c:url var="nextUrl" value="/courses">
                                <c:param name="page" value="${currentPage + 1}" />
                                <c:if test="${showStudents}">
                                    <c:param name="showStudents" value="true" />
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