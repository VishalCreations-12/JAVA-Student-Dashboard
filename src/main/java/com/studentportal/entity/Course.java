package com.studentportal.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;
    
    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String courseCode;
    
    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "duration_years")
    private Integer durationYears;
    
    @Column(name = "fee")
    private Double fee;
    
    @Column(name = "available_seats")
    private Integer availableSeats;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Admission> admissions;
    
    public Course() {
    }
    
    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getDurationYears() {
        return durationYears;
    }
    
    public void setDurationYears(Integer durationYears) {
        this.durationYears = durationYears;
    }
    
    public Double getFee() {
        return fee;
    }
    
    public void setFee(Double fee) {
        this.fee = fee;
    }
    
    public Integer getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    public List<Admission> getAdmissions() {
        return admissions;
    }
    
    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }
}

