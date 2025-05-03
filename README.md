# Course Management System


## Overview
This Course Management System is a simplified Spring Boot application designed to manage academic data for educational institutions. The application focuses on two core entities: Students and Courses. It provides functionality to perform Create, Read, Update , and delete  operations on both entities through a structured RESTful API. This system helps streamline basic academic tasks such as registering students, managing course details, and updating relevant records efficiently. While lightweight, the system forms a foundational framework for building more complex academic management systems in the future.
## Features

### Course Management
- **Course Creation and Editing**: Add new courses with validation for unique course codes
- **Course Details**: Track key course information including code, name, credit hours, and description
- **Course Listing**: View all courses with pagination support
- **Course Student Enrollment**: View students enrolled in specific courses

### Student Management
- **Student Registration**: Register new students with validation for unique email addresses
- **Student Profile Management**: Store and update student details including contact information and semester
- **Student Listing**: View all students with pagination support
- **Student Course Enrollment**: View courses enrolled by specific students

### Enrollment Management
- **Enrollment Creation**: Enroll students in courses with validation to prevent duplicate enrollments
- **Grade Management**: Assign and update grades for student enrollments
- **Enrollment History**: Track enrollment dates and academic performance
- **Pagination**: Navigate through large numbers of enrollments efficiently

### Technical Features
- **Responsive MVC Architecture**: Clean separation of concerns with Model-View-Controller pattern
- **RESTful Design**: Follows REST principles for resource management
- **Validation**: Comprehensive input validation for all data operations
- **Error Handling**: Graceful handling of errors with user-friendly feedback
- **Pagination**: Efficient handling of large datasets with server-side pagination
- **Bidirectional Relationships**: Properly managed JPA entity relationships
- **Transactional Integrity**: ACID-compliant database operations
- **Flash Messages**: User-friendly success and error notifications

## Technologies

### Backend
- **Java 17**: Modern language features for robust application development
- **Spring Boot**: Streamlined application configuration and management
- **Spring MVC**: Web request handling with controller-based architecture
- **Spring Data JPA**: Simplified data access with repository pattern
- **Hibernate**: ORM for database interaction
- **Jakarta Validation**: Bean validation for data integrity

### Database
- **JPA/Hibernate**: Object-relational mapping
- **Transaction Management**: ACID-compliant operations

### Frontend
- **Thymeleaf**: Server-side Java template engine (implied from Spring MVC structure)

## Entity Relationships

### Course Entity
- Primary properties: id, code, name, creditHours, description
- One-to-Many relationship with Enrollment

### Student Entity
- Primary properties: id, name, email, phone, address, semester
- One-to-Many relationship with Enrollment

### Enrollment Entity
- Represents the many-to-many relationship between Student and Course
- Additional properties: enrollmentDate, grade
- Enumerated grade values: NOT_GRADED, A, B, C, D, F

## Architecture

### Controller Layer
- **CourseController**: Handles course-related web requests
- **StudentController**: Manages student-related endpoints
- **EnrollmentController**: Controls enrollment operations

### Service Layer
- **CourseService**: Business logic for course operations
- **StudentService**: Business logic for student operations
- **EnrollmentService**: Business logic for enrollment operations

### Repository Layer
- **CourseRepository**: Data access for courses
- **StudentRepository**: Data access for students
- **EnrollmentRepository**: Data access for enrollments

### Model Layer
- JPA entities with appropriate relationships and validation constraints

## Implementation Details

### Database Design
- Properly structured tables with appropriate foreign key relationships
- Constraints for data integrity (unique constraints, non-null fields)
- Efficient indexing for performance

### Validation
- Bean validation on entities ensures data integrity
- Custom validation in service layer for complex business rules
- Frontend validation feedback through BindingResult

### Pagination
- Server-side pagination for all list views
- Configurable page size with sensible defaults (10 items per page)
- Page navigation controls in UI


## API Endpoints

### Course Endpoints
- `GET /courses` - List all courses (paginated)
- `GET /courses/create` - Show course creation form
- `POST /courses/create` - Create a new course
- `GET /courses/update/{id}` - Show course update form
- `POST /courses/update/{id}` - Update an existing course
- `GET /courses/delete/{id}` - Delete a course

### Student Endpoints
- `GET /students` - List all students (paginated)
- `GET /students/create` - Show student creation form
- `POST /students/create` - Create a new student
- `GET /students/update/{id}` - Show student update form
- `POST /students/update/{id}` - Update an existing student
- `GET /students/delete/{id}` - Delete a student

### Enrollment Endpoints
- `GET /enrollments` - List all enrollments (paginated)
- `GET /enrollments/create` - Show enrollment creation form
- `POST /enrollments/create` - Create a new enrollment
- `GET /enrollments/student/{studentId}` - View student enrollments
- `POST /enrollments/update-grade/{id}` - Update enrollment grade
- `GET /enrollments/delete/{id}` - Delete an enrollment
### Fronted Features
## Features

### Student Management
- Add new students with name, email, phone number, and address
- View a paginated list of all students with search and filter options
- Update existing student information
- Delete students with confirmation
- View courses enrolled by each student

### Course Management
- Create new courses with code, name, credit hours, and description
- View a paginated list of all courses
- Update course details
- Delete courses with confirmation
- View students enrolled in each course

### Enrollment Management
- Enroll students in courses with enrollment date tracking
- View all enrollments with pagination
- Filter enrollments by student or course
- Remove enrollments with confirmation
- Generate reports on enrollments

### System Features
- Responsive design that works on desktop and mobile devices
- User-friendly interface with intuitive navigation
- Form validation to ensure data integrity
- Success and error notifications
- Pagination for better performance with large datasets
- Search and filter capabilities for easy data access
- Modern UI with Font Awesome icons

## Best Practices Implemented

- **Defensive Programming**: Proper validation and error handling
- **Separation of Concerns**: Clean architecture with distinct layers
- **DRY Principle**: Code reuse through inheritance and composition
- **SOLID Principles**: Single responsibility, Open/closed, etc.
- **RESTful Design**: Resource-oriented endpoints
- **Transaction Management**: ACID-compliant operations
- **Pagination**: Efficient handling of large datasets
- **Flash Messaging**: User-friendly success/error notifications


## Project Structure

```
src/
├── main/
│   ├── java/
│   │  └── com/
│   │   └── bda/
│   │    └── assignment/
│   │         └── coursemanagement/
│   │           ├── controller/
│   │           │   ├── CourseController.java
│   │           │   ├── EnrollmentController.java
│   │           │   ├── HomeController.java
│   │           │   └── StudentController.java
│   │           ├── model/
│   │           │   ├── Course.java
│   │           │   ├── Enrollment.java
│   │           │   └── Student.java
│   │           ├── repository/
│   │           │   ├── CourseRepository.java
│   │           │   ├── EnrollmentRepository.java
│   │           │   └── StudentRepository.java
│   │           ├── service/
│   │           │   ├── CourseService.java
│   │           │   ├── EnrollmentService.java
│   │           │   └── StudentService.java
│   │           └── StudentCourseManagerApplication.java
│   ├── resources/
│   │   ├── static/
│   │   │   ├── css/
│   │   │   │   └── style.css
│   │   │   └── js/
│   │   │       └── scripts.js
│   │   └── application.properties
│   └── webapp/
│       └── WEB-INF/
│           └── views/
│               ├── course/
│               │   ├── create.jsp
│               │   ├── list.jsp
│               │   └── update.jsp
│               ├── enrollment/
│               │   ├── create.jsp
│               │   └── list.jsp
│               ├── student/
│               │   ├── create.jsp
│               │   ├── list.jsp
│               │   └── update.jsp
│               └── home.jsp
└── test/
    └── java/
        └── com/
            └── scm/
                ├── controller/
                ├── service/
                └── repository/
```
 

## Acknowledgments

- Spring Boot and Spring Data JPA teams
- Hibernate ORM developers
- All contributors to this project
