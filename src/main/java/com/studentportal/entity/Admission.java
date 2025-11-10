package com.studentportal.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "admissions")
public class Admission implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_id")
    private Long admissionId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @Column(name = "admission_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date admissionDate;
    
    @Column(name = "status", length = 20)
    private String status; // PENDING, APPROVED, REJECTED
    
    @Column(name = "application_number", unique = true, length = 20)
    private String applicationNumber;
    
    public Admission() {
        this.admissionDate = new Date();
        this.status = "PENDING";
    }
    
    // Getters and Setters
    public Long getAdmissionId() {
        return admissionId;
    }
    
    public void setAdmissionId(Long admissionId) {
        this.admissionId = admissionId;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Date getAdmissionDate() {
        return admissionDate;
    }
    
    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getApplicationNumber() {
        return applicationNumber;
    }
    
    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }
}

