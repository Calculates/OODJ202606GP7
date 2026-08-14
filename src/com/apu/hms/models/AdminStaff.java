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
        super(userId, name, email, phone, password, "Admin");
        this.department = department;
        this.accessLevel = accessLevel;
    }
    
    @Override
    public void displayMenu() {
        System.out.println("\n========== Admin Staff Menu ==========");
        System.out.println("1. Create/Read/Update/Delete Users");
        System.out.println("2. Assign Doctors to Medical Managers");
        System.out.println("3. Manage Hospital Assets (Rooms, Wards, Labs)");
        System.out.println("4. Configure Consultation Rates");
        System.out.println("5. Manage Insurance Networks");
        System.out.println("6. View System Reports");
        System.out.println("7. Logout");
        System.out.println("=====================================");
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
