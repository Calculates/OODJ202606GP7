package com.apu.hms.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Doctor class - Can log patient vitals, write notes, issue prescriptions
 */
public class Doctor extends User {
    private static final long serialVersionUID = 1L;
    
    private String license;
    private String specialization;
    private String departmentId;
    private List<String> consultationSlots;
    
    public Doctor(String userId, String name, String email, String phone, 
                  String password, String license, String specialization, String departmentId) {
        super(userId, name, email, phone, password, "Doctor");
        this.license = license;
        this.specialization = specialization;
        this.departmentId = departmentId;
        this.consultationSlots = new ArrayList<>();
    }
    
    public void addConsultationSlot(String slot) {
        consultationSlots.add(slot);
    }
    
    public void removeConsultationSlot(String slot) {
        consultationSlots.remove(slot);
    }
    
    // Getters and Setters
    public String getLicense() {
        return license;
    }
    
    public void setLicense(String license) {
        this.license = license;
    }
    
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
    
    public List<String> getConsultationSlots() {
        return consultationSlots;
    }
    
    public void setConsultationSlots(List<String> consultationSlots) {
        this.consultationSlots = consultationSlots;
    }
}
