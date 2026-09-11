package com.apu.hms.models;

/**
 * AdminStaff class - Administrative user with CRUD and asset management capabilities
 */
public class AdminStaff extends User {
    private static final long serialVersionUID = 1L;
    
    private String department;
    private String accessLevel;
    
    public AdminStaff(String userId, String name, String email, String phone, 
                      String password, String department, String accessLevel) {
        super(userId, name, email, phone, password, "Admin Staff");
        this.department = department;
        this.accessLevel = accessLevel;
    }
    
    // Getters and Setters
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getAccessLevel() {
        return accessLevel;
    }
    
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
