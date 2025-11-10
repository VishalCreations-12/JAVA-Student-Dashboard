-- Student Admission Portal Database Initialization Script
-- MySQL Database Setup

CREATE DATABASE IF NOT EXISTS student_portal;
USE student_portal;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS admissions;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

-- Create Courses Table
CREATE TABLE courses (
    course_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    duration_years INT,
    fee DECIMAL(10, 2),
    available_seats INT,
    INDEX idx_course_code (course_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create Students Table
CREATE TABLE students (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    date_of_birth DATE NOT NULL,
    age INT,
    address VARCHAR(255),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_registration_date (registration_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create Admissions Table
CREATE TABLE admissions (
    admission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    admission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING',
    application_number VARCHAR(20) UNIQUE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    INDEX idx_student_id (student_id),
    INDEX idx_course_id (course_id),
    INDEX idx_application_number (application_number),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert Sample Courses
INSERT INTO courses (course_code, course_name, description, duration_years, fee, available_seats) VALUES
('CS101', 'Computer Science', 'Comprehensive computer science program covering programming, algorithms, and software engineering', 4, 15000.00, 50),
('EE201', 'Electrical Engineering', 'Electrical engineering program with focus on circuits, systems, and power engineering', 4, 16000.00, 40),
('ME301', 'Mechanical Engineering', 'Mechanical engineering program covering design, manufacturing, and thermodynamics', 4, 15500.00, 45),
('CE401', 'Civil Engineering', 'Civil engineering program focusing on structures, construction, and infrastructure', 4, 14500.00, 35),
('MBA501', 'Master of Business Administration', 'Advanced business administration program for management professionals', 2, 25000.00, 30),
('BBA601', 'Bachelor of Business Administration', 'Business administration program covering management, finance, and marketing', 3, 12000.00, 60),
('PHY701', 'Physics', 'Physics program covering theoretical and applied physics', 4, 14000.00, 25),
('CHEM801', 'Chemistry', 'Chemistry program with focus on organic, inorganic, and physical chemistry', 4, 13500.00, 30);

-- Insert Sample Students (Optional - for testing)
INSERT INTO students (first_name, last_name, email, phone, date_of_birth, age, address, city, state, zip_code) VALUES
('John', 'Doe', 'john.doe@example.com', '1234567890', '2000-05-15', 24, '123 Main St', 'New York', 'NY', '10001'),
('Jane', 'Smith', 'jane.smith@example.com', '9876543210', '2001-08-20', 23, '456 Oak Ave', 'Los Angeles', 'CA', '90001'),
('Michael', 'Johnson', 'michael.j@example.com', '5551234567', '1999-12-10', 25, '789 Pine Rd', 'Chicago', 'IL', '60601');

-- Insert Sample Admissions (Optional - for testing)
INSERT INTO admissions (student_id, course_id, status, application_number) VALUES
(1, 1, 'APPROVED', 'APP20250101001'),
(2, 2, 'PENDING', 'APP20250101002'),
(3, 1, 'PENDING', 'APP20250101003');

-- Display confirmation
SELECT 'Database initialized successfully!' AS Status;
SELECT COUNT(*) AS 'Total Courses' FROM courses;
SELECT COUNT(*) AS 'Total Students' FROM students;
SELECT COUNT(*) AS 'Total Admissions' FROM admissions;

