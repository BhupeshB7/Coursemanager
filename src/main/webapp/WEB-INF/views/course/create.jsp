<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Course - Student Course Manager</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
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
                <li><a href="<c:url value='/' />"><i class="fa-solid fa-house"></i> Home</a></li>
                <li><a href="<c:url value='/students' />"><i class="fa-solid fa-user-graduate"></i> Students</a></li>
                <li><a href="<c:url value='/courses' />"><i class="fa-solid fa-book-open"></i> Courses</a></li>
                <li><a href="<c:url value='/enrollments' />"><i class="fa-solid fa-clipboard-list"></i> Enrollments</a>
                </li>
            </ul>
        </nav>
    </div>
</header>


    <div class="container main_component">
        <h1 class="page-title"><i class="fa-solid fa-plus"></i> Add New Course</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
            </div>
        </c:if>

        <div class="form-container">
            <form:form action="/courses/create" method="post" modelAttribute="course">
                <div class="form-group">
                    <label for="code"><i class="fa-solid fa-code"></i> Course Code:</label>
                    <form:input path="code" id="code" required="true" />
                    <form:errors path="code" cssClass="error" />
                </div>

                <div class="form-group">
                    <label for="name"><i class="fa-solid fa-book"></i> Course Name:</label>
                    <form:input path="name" id="name" required="true" />
                    <form:errors path="name" cssClass="error" />
                </div>

                <div class="form-group">
                    <label for="creditHours"><i class="fa-solid fa-clock"></i> Credits:</label>
                    <form:input path="creditHours" id="creditHours" type="number" min="1" max="6" required="true" />
                    <form:errors path="creditHours" cssClass="error" />
                </div>

                <div class="form-group">
                    <label for="description"><i class="fa-solid fa-align-left"></i> Description:</label>
                    <form:textarea path="description" id="description" rows="4" />
                    <form:errors path="description" cssClass="error" />
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-success">
                        <i class="fa-solid fa-floppy-disk"></i> Save Course
                    </button>
                    <a href="<c:url value='/courses' />" class="btn">
                        <i class="fa-solid fa-xmark"></i> Cancel
                    </a>
                </div>
            </form:form>
        </div>
    </div>

<footer class="footer">
    <div class="container footer-container">

        <!-- About Section -->
        <div class="footer-section about">
            <h3><i class="fa-solid fa-graduation-cap"></i> Student Course Manager</h3>
            <p>Manage courses, students, and enrollments efficiently. A modern solution for academic institutions.</p>
        </div>

        <!-- Quick Links -->
        <div class="footer-section links">
            <h4><i class="fa-solid fa-link"></i> Quick Links</h4>
            <ul>
                <li><a href="<c:url value='/' />"><i class="fa-solid fa-house"></i> Home</a></li>
                <li><a href="<c:url value='/students' />"><i class="fa-solid fa-user-graduate"></i> Students</a></li>
                <li><a href="<c:url value='/courses' />"><i class="fa-solid fa-book-open"></i> Courses</a></li>
                <li><a href="<c:url value='/enrollments' />"><i class="fa-solid fa-clipboard-list"></i> Enrollments</a>
                </li>
            </ul>
        </div>

        <!-- Contact Info -->
        <div class="footer-section contact">
            <h4><i class="fa-solid fa-envelope"></i> Contact</h4>
            <p><i class="fa-solid fa-envelope"></i> support@scmapp.com</p>
            <p><i class="fa-solid fa-phone"></i> +91 98765 43210</p>
            <p><i class="fa-solid fa-location-dot"></i> Patna, India</p>
        </div>

    </div>


</body>

</html>
