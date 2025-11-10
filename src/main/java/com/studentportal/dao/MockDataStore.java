package com.studentportal.dao;

import com.studentportal.entity.Admission;
import com.studentportal.entity.Course;
import com.studentportal.entity.Student;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class MockDataStore {
    private static MockDataStore instance;
    
    private Map<Long, Student> students = new HashMap<>();
    private Map<Long, Course> courses = new HashMap<>();
    private Map<Long, Admission> admissions = new HashMap<>();
    
    private AtomicLong studentIdCounter = new AtomicLong(1);
    private AtomicLong courseIdCounter = new AtomicLong(1);
    private AtomicLong admissionIdCounter = new AtomicLong(1);
    
    private MockDataStore() {
        initializeDefaultCourses();
        initializeSampleData();
    }
    
    public static synchronized MockDataStore getInstance() {
        if (instance == null) {
            instance = new MockDataStore();
        }
        return instance;
    }
    
    private void initializeDefaultCourses() {
        Course[] defaultCourses = {
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
        
        for (Course course : defaultCourses) {
            Long id = courseIdCounter.getAndIncrement();
            course.setCourseId(id);
            courses.put(id, course);
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
        // Create sample students
        Student s1 = new Student();
        s1.setStudentId(studentIdCounter.getAndIncrement());
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
        students.put(s1.getStudentId(), s1);
        
        Student s2 = new Student();
        s2.setStudentId(studentIdCounter.getAndIncrement());
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
        students.put(s2.getStudentId(), s2);
        
        // Create sample admissions
        Course c1 = courses.values().iterator().next();
        Admission a1 = new Admission();
        a1.setAdmissionId(admissionIdCounter.getAndIncrement());
        a1.setStudent(s1);
        a1.setCourse(c1);
        a1.setApplicationNumber("APP20250101001");
        a1.setStatus("APPROVED");
        a1.setAdmissionDate(new Date());
        admissions.put(a1.getAdmissionId(), a1);
        
        Course c2 = courses.values().toArray(new Course[0])[1];
        Admission a2 = new Admission();
        a2.setAdmissionId(admissionIdCounter.getAndIncrement());
        a2.setStudent(s2);
        a2.setCourse(c2);
        a2.setApplicationNumber("APP20250101002");
        a2.setStatus("PENDING");
        a2.setAdmissionDate(new Date());
        admissions.put(a2.getAdmissionId(), a2);
    }
    
    // Student operations
    public Long saveStudent(Student student) {
        if (student.getStudentId() == null) {
            student.setStudentId(studentIdCounter.getAndIncrement());
        }
        students.put(student.getStudentId(), student);
        return student.getStudentId();
    }
    
    public void updateStudent(Student student) {
        students.put(student.getStudentId(), student);
    }
    
    public void deleteStudent(Long studentId) {
        students.remove(studentId);
        // Also remove related admissions
        admissions.entrySet().removeIf(entry -> 
            entry.getValue().getStudent().getStudentId().equals(studentId));
    }
    
    public Student findStudentById(Long studentId) {
        return students.get(studentId);
    }
    
    public Student findStudentByEmail(String email) {
        return students.values().stream()
            .filter(s -> s.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }
    
    public List<Student> findAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    // Course operations
    public Long saveCourse(Course course) {
        if (course.getCourseId() == null) {
            course.setCourseId(courseIdCounter.getAndIncrement());
        }
        courses.put(course.getCourseId(), course);
        return course.getCourseId();
    }
    
    public Course findCourseById(Long courseId) {
        return courses.get(courseId);
    }
    
    public List<Course> findAllCourses() {
        return new ArrayList<>(courses.values());
    }
    
    // Admission operations
    public Long saveAdmission(Admission admission) {
        if (admission.getAdmissionId() == null) {
            admission.setAdmissionId(admissionIdCounter.getAndIncrement());
        }
        admissions.put(admission.getAdmissionId(), admission);
        return admission.getAdmissionId();
    }
    
    public void updateAdmission(Admission admission) {
        admissions.put(admission.getAdmissionId(), admission);
    }
    
    public void deleteAdmission(Long admissionId) {
        admissions.remove(admissionId);
    }
    
    public Admission findAdmissionById(Long admissionId) {
        return admissions.get(admissionId);
    }
    
    public List<Admission> findAllAdmissions() {
        return new ArrayList<>(admissions.values());
    }
    
    public List<Admission> findAdmissionsByStudentId(Long studentId) {
        List<Admission> result = new ArrayList<>();
        for (Admission admission : admissions.values()) {
            if (admission.getStudent().getStudentId().equals(studentId)) {
                result.add(admission);
            }
        }
        return result;
    }
}

