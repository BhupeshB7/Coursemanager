<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Update Student - Student Course Manager</title>
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
      <h1 class="page-title">
        <i class="fa-solid fa-edit"></i> Update Student
      </h1>

      <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
          <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
        </div>
      </c:if>

      <div class="form-container">
        <form:form
          action="/students/update/${student.id}"
          method="post"
          modelAttribute="student"
        >
          <form:hidden path="id" />

          <div class="form-group">
            <label for="name"
              ><i class="fa-solid fa-user"></i> Student Name:</label
            >
            <form:input path="name" id="name" required="true" />
            <form:errors path="name" cssClass="error" />
          </div>

          <div class="form-group">
            <label for="email"
              ><i class="fa-solid fa-envelope"></i> Email:</label
            >
            <form:input path="email" id="email" type="email" required="true" />
            <form:errors path="email" cssClass="error" />
          </div>

          <div class="form-group">
            <label for="phone"><i class="fa-solid fa-phone"></i> Phone:</label>
            <form:input path="phone" id="phone" placeholder="10 digits" />
            <form:errors path="phone" cssClass="error" />
          </div>

          <div class="form-group">
            <label for="address"
              ><i class="fa-solid fa-location-dot"></i> Address:</label
            >
            <form:input path="address" id="address" />
            <form:errors path="address" cssClass="error" />
          </div>

          <div class="form-group">
            <label for="semester"
              ><i class="fa-solid fa-calendar-days"></i> Semester:</label
            >
            <form:input
              path="semester"
              id="semester"
              type="number"
              min="1"
              max="10"
              required="true"
            />
            <form:errors path="semester" cssClass="error" />
          </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-success">
              <i class="fa-solid fa-save"></i> Update Student
            </button>
            <a href="<c:url value='/students' />" class="btn">
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
