package com.apu.hms.models;

import java.io.Serializable;

/**
 * Abstract base class for all users in the Hospital Management System
 * Demonstrates abstraction and inheritance in OOP
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String userId;
    protected String name;
    protected String email;
    protected String phone;
    protected String password;
    protected String role;
    
    public User(String userId, String name, String email, String phone, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract void displayMenu();
    
    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }

    public PermissionLevel getPermission(SystemModule module) {
        return PermissionMatrix.getPermission(role, module);
    }

    public boolean canRead(SystemModule module) {
        return PermissionMatrix.canRead(role, module);
    }

    public boolean canWrite(SystemModule module) {
        return PermissionMatrix.canWrite(role, module);
    }

    public boolean canCreateOrDelete(SystemModule module) {
        return PermissionMatrix.canCreateOrDelete(role, module);
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
