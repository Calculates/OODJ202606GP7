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
    
    @Override
    public void displayMenu() {
        System.out.println("\n======= Medical Manager Menu =======");
        System.out.println("1. Edit Personal Profile");
        System.out.println("2. Create/Update Clinical Departments");
        System.out.println("3. Design Doctor Shift Rosters");
        System.out.println("4. View Hospital Metrics Reports");
        System.out.println("5. View Revenue Summaries");
        System.out.println("6. Manage Department Staff");
        System.out.println("7. Logout");
        System.out.println("====================================");
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
