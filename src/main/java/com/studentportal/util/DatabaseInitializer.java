package com.studentportal.util;

import com.studentportal.dao.CourseDAO;
import com.studentportal.dao.StudentDAO;
import com.studentportal.dao.AdmissionDAO;
import com.studentportal.entity.Course;
import com.studentportal.entity.Student;
import com.studentportal.entity.Admission;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.List;

@WebListener
public class DatabaseInitializer implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Initialize Hibernate (creates tables automatically)
            HibernateUtil.getSessionFactory();
            System.out.println("Hibernate initialized - Database tables created!");
            
            // Initialize default courses
            initializeCourses();
            
            // Initialize sample data
            initializeSampleData();
            
            System.out.println("Database initialized successfully with courses and sample data!");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }
    
    private void initializeCourses() {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> existingCourses = courseDAO.findAll();
        
        if (existingCourses == null || existingCourses.isEmpty()) {
            Course[] courses = {
                createCourse("CS101", "Computer Science", 
                    "Comprehensive computer science program covering programming, algorithms, and software engineering", 
                    4, 15000.00, 50),
                createCourse("EE201", "Electrical Engineering", 
                    "Electrical engineering program with focus on circuits, systems, and power engineering", 
                    4, 16000.00, 40),
                createCourse("ME301", "Mechanical Engineering", 
                    "Mechanical engineering program covering design, manufacturing, and thermodynamics", 
                    4, 15500.00, 45),
                createCourse("CE401", "Civil Engineering", 
                    "Civil engineering program focusing on structures, construction, and infrastructure", 
                    4, 14500.00, 35),
                createCourse("MBA501", "Master of Business Administration", 
                    "Advanced business administration program for management professionals", 
                    2, 25000.00, 30),
                createCourse("BBA601", "Bachelor of Business Administration", 
                    "Business administration program covering management, finance, and marketing", 
                    3, 12000.00, 60),
                createCourse("PHY701", "Physics", 
                    "Physics program covering theoretical and applied physics", 
                    4, 14000.00, 25),
                createCourse("CHEM801", "Chemistry", 
                    "Chemistry program with focus on organic, inorganic, and physical chemistry", 
                    4, 13500.00, 30)
            };
            
            for (Course course : courses) {
                try {
                    courseDAO.save(course);
                    System.out.println("Created course: " + course.getCourseName());
                } catch (Exception e) {
                    System.err.println("Error creating course: " + course.getCourseCode());
                    e.printStackTrace();
                }
            }
        }
    }
    
    private Course createCourse(String code, String name, String description, 
                               int duration, double fee, int seats) {
        Course course = new Course();
        course.setCourseCode(code);
        course.setCourseName(name);
        course.setDescription(description);
        course.setDurationYears(duration);
        course.setFee(fee);
        course.setAvailableSeats(seats);
        return course;
    }
    
    private void initializeSampleData() {
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        AdmissionDAO admissionDAO = new AdmissionDAO();
        
        // Check if sample data already exists
        List<Student> existingStudents = studentDAO.findAll();
        if (existingStudents != null && !existingStudents.isEmpty()) {
            System.out.println("Sample data already exists, skipping initialization.");
            return;
        }
        
        // Create sample students
        Student s1 = new Student();
        s1.setFirstName("John");
        s1.setLastName("Doe");
        s1.setEmail("john.doe@example.com");
        s1.setPhone("1234567890");
        s1.setDateOfBirth(new Date(System.currentTimeMillis() - (24L * 365 * 24 * 60 * 60 * 1000)));
        s1.setAge(24);
        s1.setAddress("123 Main St");
        s1.setCity("New York");
        s1.setState("NY");
        s1.setZipCode("10001");
        s1.setRegistrationDate(new Date());
        Long studentId1 = studentDAO.save(s1);
        s1.setStudentId(studentId1);
        System.out.println("Created sample student: " + s1.getFullName());
        
        Student s2 = new Student();
        s2.setFirstName("Jane");
        s2.setLastName("Smith");
        s2.setEmail("jane.smith@example.com");
        s2.setPhone("9876543210");
        s2.setDateOfBirth(new Date(System.currentTimeMillis() - (23L * 365 * 24 * 60 * 60 * 1000)));
        s2.setAge(23);
        s2.setAddress("456 Oak Ave");
        s2.setCity("Los Angeles");
        s2.setState("CA");
        s2.setZipCode("90001");
        s2.setRegistrationDate(new Date());
        Long studentId2 = studentDAO.save(s2);
        s2.setStudentId(studentId2);
        System.out.println("Created sample student: " + s2.getFullName());
        
        // Create sample admissions
        List<Course> courses = courseDAO.findAll();
        if (courses != null && !courses.isEmpty()) {
            Course c1 = courses.get(0);
            Admission a1 = new Admission();
            a1.setStudent(s1);
            a1.setCourse(c1);
            a1.setApplicationNumber("APP20250101001");
            a1.setStatus("APPROVED");
            a1.setAdmissionDate(new Date());
            admissionDAO.save(a1);
            System.out.println("Created sample admission: " + a1.getApplicationNumber());
            
            if (courses.size() > 1) {
                Course c2 = courses.get(1);
                Admission a2 = new Admission();
                a2.setStudent(s2);
                a2.setCourse(c2);
                a2.setApplicationNumber("APP20250101002");
                a2.setStatus("PENDING");
                a2.setAdmissionDate(new Date());
                admissionDAO.save(a2);
                System.out.println("Created sample admission: " + a2.getApplicationNumber());
            }
        }
    }
}

