package com.apu.hms.models;

import java.io.Serializable;

/**
 * Department class - Represents a medical department/specialty (e.g., Cardiology)
 */
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String departmentId;
    private String name;
    private String specialization;
    private String managerId;
    private String description;
    
    public Department(String departmentId, String name, String specialization, 
                     String managerId, String description) {
        this.departmentId = departmentId;
        this.name = name;
        this.specialization = specialization;
        this.managerId = managerId;
        this.description = description;
    }
    
    // Getters and Setters
    public String getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getManagerId() {
        return managerId;
    }
    
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", managerId='" + managerId + '\'' +
                '}';
    }
}
