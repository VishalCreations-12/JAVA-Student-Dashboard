package com.studentportal.bean;

import com.studentportal.dao.AdmissionDAO;
import com.studentportal.dao.CourseDAO;
import com.studentportal.dao.StudentDAO;
import com.studentportal.entity.Admission;
import com.studentportal.entity.Course;
import com.studentportal.entity.Student;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "registrationBean")
@SessionScoped
public class RegistrationBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Student student;
    private Long selectedCourseId;
    private Course selectedCourse;
    private List<Course> availableCourses;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private AdmissionDAO admissionDAO;
    
    private String confirmMessage;
    private Admission currentAdmission;
    
    public RegistrationBean() {
        student = new Student();
        courseDAO = new CourseDAO();
        studentDAO = new StudentDAO();
        admissionDAO = new AdmissionDAO();
        loadCourses();
    }
    
    private void loadCourses() {
        availableCourses = courseDAO.findAll();
    }
    
    public void onCourseChange(ValueChangeEvent event) {
        selectedCourseId = (Long) event.getNewValue();
        if (selectedCourseId != null) {
            selectedCourse = courseDAO.findById(selectedCourseId);
        } else {
            selectedCourse = null;
        }
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    public void onFormSubmit(ActionEvent event) {
        // ActionEvent handler for form submission
        // This demonstrates ActionEvent usage
        System.out.println("Form submission ActionEvent triggered");
    }
    
    public String register() {
        try {
            // Validate age
            if (student.getDateOfBirth() != null) {
                Calendar dob = Calendar.getInstance();
                dob.setTime(student.getDateOfBirth());
                Calendar now = Calendar.getInstance();
                int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                
                if (age < 16 || age > 100) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Age must be between 16 and 100 years", ""));
                    return "failure";
                }
                student.setAge(age);
            }
            
            // Check if email already exists
            Student existingStudent = studentDAO.findByEmail(student.getEmail());
            if (existingStudent != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Email already registered", ""));
                return "failure";
            }
            
            // Save student
            Long studentId = studentDAO.save(student);
            student.setStudentId(studentId);
            
            // Create admission
            if (selectedCourseId != null) {
                Course course = courseDAO.findById(selectedCourseId);
                if (course != null) {
                    Admission admission = new Admission();
                    admission.setStudent(student);
                    admission.setCourse(course);
                    admission.setApplicationNumber(generateApplicationNumber());
                    
                    Long admissionId = admissionDAO.save(admission);
                    admission.setAdmissionId(admissionId);
                    currentAdmission = admission;
                    
                    confirmMessage = "Registration successful! Your application number is: " + 
                                    admission.getApplicationNumber();
                    return "success";
                }
            }
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Please select a course", ""));
            return "failure";
            
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Registration failed: " + e.getMessage(), ""));
            return "failure";
        }
    }
    
    private String generateApplicationNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());
        String randomPart = String.valueOf((int)(Math.random() * 10000));
        return "APP" + datePart + randomPart;
    }
    
    public String goToDashboard() {
        return "dashboard";
    }
    
    public String startNewRegistration() {
        student = new Student();
        selectedCourseId = null;
        currentAdmission = null;
        confirmMessage = null;
        return "register";
    }
    
    // Getters and Setters
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Long getSelectedCourseId() {
        return selectedCourseId;
    }
    
    public void setSelectedCourseId(Long selectedCourseId) {
        this.selectedCourseId = selectedCourseId;
    }
    
    public List<Course> getAvailableCourses() {
        return availableCourses;
    }
    
    public void setAvailableCourses(List<Course> availableCourses) {
        this.availableCourses = availableCourses;
    }
    
    public String getConfirmMessage() {
        return confirmMessage;
    }
    
    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }
    
    public Admission getCurrentAdmission() {
        return currentAdmission;
    }
    
    public void setCurrentAdmission(Admission currentAdmission) {
        this.currentAdmission = currentAdmission;
    }
    
    public Course getSelectedCourse() {
        if (selectedCourse == null && selectedCourseId != null) {
            selectedCourse = courseDAO.findById(selectedCourseId);
        }
        return selectedCourse;
    }
    
    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}

