package com.apu.hms.models;

/**
 * MedicalManager class - Manages departments, shifts, and hospital metrics
 */
public class MedicalManager extends User {
    private static final long serialVersionUID = 1L;
    
    private String specialization;
    private String departmentId;
    
    public MedicalManager(String userId, String name, String email, String phone, 
                         String password, String specialization, String departmentId) {
        super(userId, name, email, phone, password, "Medical Manager");
        this.specialization = specialization;
        this.departmentId = departmentId;
    }
    
    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
