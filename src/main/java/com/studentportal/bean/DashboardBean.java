package com.studentportal.bean;

import com.studentportal.dao.AdmissionDAO;
import com.studentportal.dao.CourseDAO;
import com.studentportal.dao.StudentDAO;
import com.studentportal.entity.Admission;
import com.studentportal.entity.Course;
import com.studentportal.entity.Student;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Student> students;
    private List<Admission> admissions;
    private List<Course> courses;
    
    private StudentDAO studentDAO;
    private AdmissionDAO admissionDAO;
    private CourseDAO courseDAO;
    
    private Student selectedStudent;
    private Admission selectedAdmission;
    private boolean editMode;
    
    public DashboardBean() {
        studentDAO = new StudentDAO();
        admissionDAO = new AdmissionDAO();
        courseDAO = new CourseDAO();
        loadData();
    }
    
    public void loadData() {
        try {
            students = studentDAO.findAll();
            admissions = admissionDAO.findAll();
            courses = courseDAO.findAll();
            
            // Ensure lists are not null
            if (students == null) {
                students = new java.util.ArrayList<>();
            }
            if (admissions == null) {
                admissions = new java.util.ArrayList<>();
            }
            if (courses == null) {
                courses = new java.util.ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error loading dashboard data: " + e.getMessage());
            e.printStackTrace();
            students = new java.util.ArrayList<>();
            admissions = new java.util.ArrayList<>();
            courses = new java.util.ArrayList<>();
        }
    }
    
    // This method is called when the page is accessed (via preRenderView event)
    public void onPageLoad() {
        loadData();
    }
    
    // Getter that ensures data is loaded
    public List<Student> getStudents() {
        if (students == null) {
            loadData();
        }
        return students;
    }
    
    public List<Admission> getAdmissions() {
        if (admissions == null) {
            loadData();
        }
        return admissions;
    }
    
    public List<Course> getCourses() {
        if (courses == null) {
            loadData();
        }
        return courses;
    }
    
    // ActionEvent Handlers
    public void onEditStudentClick(ActionEvent event) {
        // ActionEvent handler - called before action method
        if (selectedStudent != null) {
            editMode = true;
            System.out.println("Edit student ActionEvent: " + selectedStudent.getFullName());
        }
    }
    
    public void onDeleteStudentClick(ActionEvent event) {
        // ActionEvent handler - called before action method
        if (selectedStudent != null) {
            System.out.println("Delete student ActionEvent: " + selectedStudent.getFullName());
        }
    }
    
    public void onApproveAdmissionClick(ActionEvent event) {
        // ActionEvent handler - called before action method
        if (selectedAdmission != null) {
            System.out.println("Approve admission ActionEvent: " + selectedAdmission.getApplicationNumber());
        }
    }
    
    public void onRejectAdmissionClick(ActionEvent event) {
        // ActionEvent handler - called before action method
        if (selectedAdmission != null) {
            System.out.println("Reject admission ActionEvent: " + selectedAdmission.getApplicationNumber());
        }
    }
    
    public void onDeleteAdmissionClick(ActionEvent event) {
        // ActionEvent handler - called before action method
        if (selectedAdmission != null) {
            System.out.println("Delete admission ActionEvent: " + selectedAdmission.getApplicationNumber());
        }
    }
    
    // Action Methods
    public String deleteStudent() {
        try {
            if (selectedStudent != null) {
                studentDAO.delete(selectedStudent.getStudentId());
                loadData();
                selectedStudent = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Student deleted successfully", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error deleting student: " + e.getMessage(), ""));
        }
        return null; // Stay on same page
    }
    
    public String deleteAdmission() {
        try {
            if (selectedAdmission != null) {
                admissionDAO.delete(selectedAdmission.getAdmissionId());
                loadData();
                selectedAdmission = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Admission deleted successfully", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error deleting admission: " + e.getMessage(), ""));
        }
        return null; // Stay on same page
    }
    
    public String editStudent() {
        if (selectedStudent != null) {
            editMode = true;
        }
        return null; // Stay on same page
    }
    
    public String updateStudent() {
        try {
            if (selectedStudent != null) {
                studentDAO.update(selectedStudent);
                loadData();
                editMode = false;
                selectedStudent = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Student updated successfully", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error updating student: " + e.getMessage(), ""));
        }
        return null; // Stay on same page
    }
    
    public void cancelEdit() {
        editMode = false;
        selectedStudent = null;
    }
    
    public String approveAdmission() {
        try {
            if (selectedAdmission != null) {
                selectedAdmission.setStatus("APPROVED");
                admissionDAO.update(selectedAdmission);
                loadData();
                selectedAdmission = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Admission approved successfully", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error approving admission: " + e.getMessage(), ""));
        }
        return null; // Stay on same page
    }
    
    public String rejectAdmission() {
        try {
            if (selectedAdmission != null) {
                selectedAdmission.setStatus("REJECTED");
                admissionDAO.update(selectedAdmission);
                loadData();
                selectedAdmission = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Admission rejected", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error rejecting admission: " + e.getMessage(), ""));
        }
        return null; // Stay on same page
    }
    
    public void onRefreshClick(ActionEvent event) {
        // ActionEvent handler for refresh button
        System.out.println("Refresh data ActionEvent triggered");
        loadData(); // Refresh data when button is clicked
    }
    
    // Setters
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public Student getSelectedStudent() {
        return selectedStudent;
    }
    
    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }
    
    public Admission getSelectedAdmission() {
        return selectedAdmission;
    }
    
    public void setSelectedAdmission(Admission selectedAdmission) {
        this.selectedAdmission = selectedAdmission;
    }
    
    public boolean isEditMode() {
        return editMode;
    }
    
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}

