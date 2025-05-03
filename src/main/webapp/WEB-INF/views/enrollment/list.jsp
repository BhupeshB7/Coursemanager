<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Enrollment List - Student Course Manager</title>
            <link rel="stylesheet" href="<c:url value='/css/style.css' />" />
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
           
            <script>
                function confirmDelete(enrollmentId) {
                    if (confirm('Are you sure you want to delete this enrollment?')) {
                        window.location.href = '<c:url value="/enrollments/delete/" />' + enrollmentId;
                        return true;
                    }
                    return false;
                }
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
                <h1 class="page-title"><i class="fas fa-clipboard-list"></i> Enrollment List</h1>

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
                    <a href="<c:url value='/enrollments/create' />" class="btn btn-success">
                        <i class="fas fa-plus-circle"></i> Add New Enrollment
                    </a>
                </div>

                <!-- Pagination Info -->
                <div class="pagination-info">
                    Showing ${enrollments.size()} of ${totalItems} items, Page ${currentPage + 1} of ${totalPages}
                </div>

                <div class="table-container">
                    <c:choose>
                        <c:when test="${not empty enrollments}">
                            <table>
                                <thead>
                                    <tr>
                                        <th><i class="fas fa-hashtag"></i> ID</th>
                                        <th><i class="fas fa-user-graduate"></i> Student Name</th>
                                        <th><i class="fas fa-book"></i> Course Name</th>
                                        <th><i class="fas fa-grade"></i> Grade</th>
                                        <th><i class="fas fa-calendar"></i> Enrollment Date</th>
                                        <th><i class="fas fa-cogs"></i> Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${enrollments}" var="enrollment">
                                        <tr>
                                            <td>${enrollment.id}</td>
                                            <td>${enrollment.student.name}</td>
                                            <td>${enrollment.course.name}</td>
                                            <td>${enrollment.grade}</td>
                                            <td>${enrollment.enrollmentDate}</td>
                                            <td class="actions">
                                                <a href="<c:url value='/enrollments/view/${enrollment.id}' />"
                                                    class="btn">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                                <a href="#" class="btn btn-danger"
                                                    onclick="return confirmDelete('${enrollment.id}')">
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
                                <i class="fas fa-info-circle"></i> No Enrollments Found
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Pagination Controls -->
                <c:if test="${totalPages > 1}">
                    <ul class="pagination">
                        <!-- Previous -->
                        <li class="${currentPage == 0 ? 'disabled' : ''}">
                            <c:url var="prevUrl" value="/enrollments">
                                <c:param name="page" value="${currentPage - 1}" />
                            </c:url>
                            <a href="${currentPage == 0 ? '#' : prevUrl}">
                                <i class="fa-solid fa-chevron-left"></i>
                            </a>
                        </li>

                        <!-- Page Numbers -->
                        <c:forEach begin="0" end="${totalPages - 1}" var="i">
                            <c:choose>
                                <c:when test="${currentPage == i}">
                                    <li class="active"><a href="#">${i + 1}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="pageUrl" value="/enrollments">
                                        <c:param name="page" value="${i}" />
                                    </c:url>
                                    <li><a href="${pageUrl}">${i + 1}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <!-- Next -->
                        <li class="${currentPage + 1 == totalPages ? 'disabled' : ''}">
                            <c:url var="nextUrl" value="/enrollments">
                                <c:param name="page" value="${currentPage + 1}" />
                            </c:url>
                            <a href="${currentPage + 1 == totalPages ? '#' : nextUrl}">
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
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
                            <i class="fa-solid fa-location-dot"></i> Patna , India 
                        </p>
                    </div>
                </div>

                <div class="footer-bottom">
                    <p>&copy; 2025 Student Course Manager. All rights reserved.</p>
                </div>
            </footer>
        </body>

        </html>