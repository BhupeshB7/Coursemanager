<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Student Course Manager</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    />
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
              <a href="<c:url value='/' />"
                ><i class="fa-solid fa-house"></i> Home</a
              >
            </li>
            <li>
              <a href="<c:url value='/students' />"
                ><i class="fa-solid fa-user-graduate"></i> Students</a
              >
            </li>
            <li>
              <a href="<c:url value='/courses' />"
                ><i class="fa-solid fa-book-open"></i> Courses</a
              >
            </li>
            <li>
              <a href="<c:url value='/enrollments' />"
                ><i class="fa-solid fa-clipboard-list"></i> Enrollments</a
              >
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <div class="container main_component">
      <h1 class="page-title">Welcome to Student Course Manager</h1>

      <div class="card-container">
        <div class="card">
          <h2>Students</h2>
          <p>
            Manage student information, view enrollments, and update student
            details.
          </p>
          <a href="<c:url value='/students' />" class="btn">View Students</a>
          <a href="<c:url value='/students/create' />" class="btn btn-success"
            >Add Student</a
          >
        </div>

        <div class="card">
          <h2>Courses</h2>
          <p>
            Manage course information, view enrolled students, and update course
            details.
          </p>
          <a href="<c:url value='/courses' />" class="btn">View Courses</a>
          <a href="<c:url value='/courses/create' />" class="btn btn-success"
            >Add Course</a
          >
        </div>

        <div class="card">
          <h2>Enrollments</h2>
          <p>
            Manage student enrollments, assign grades, and view enrollment
            details.
          </p>
          <a href="<c:url value='/enrollments' />" class="btn"
            >View Enrollments</a
          >
          <a
            href="<c:url value='/enrollments/create' />"
            class="btn btn-success"
            >Create Enrollment</a
          >
        </div>
      </div>
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
              <a href="<c:url value='/' />"
                ><i class="fa-solid fa-house"></i> Home</a
              >
            </li>
            <li>
              <a href="<c:url value='/students' />"
                ><i class="fa-solid fa-user-graduate"></i> Students</a
              >
            </li>
            <li>
              <a href="<c:url value='/courses' />"
                ><i class="fa-solid fa-book-open"></i> Courses</a
              >
            </li>
            <li>
              <a href="<c:url value='/enrollments' />"
                ><i class="fa-solid fa-clipboard-list"></i> Enrollments</a
              >
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
