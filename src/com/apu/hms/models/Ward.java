package com.apu.hms.models;

import java.io.Serializable;

/**
 * Ward class - Represents a hospital ward/clinic
 */
public class Ward implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String wardId;
    private String name;
    private String type;  // e.g., "Inpatient", "Outpatient", "ICU"
    private int capacity;
    private int occupancy;
    private String departmentId;
    
    public Ward(String wardId, String name, String type, int capacity, String departmentId) {
        this.wardId = wardId;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.occupancy = 0;
        this.departmentId = departmentId;
    }
    
    // Getters and Setters
    public String getWardId() {
        return wardId;
    }
    
    public void setWardId(String wardId) {
        this.wardId = wardId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getOccupancy() {
        return occupancy;
    }
    
    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }
    
    public String getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    
    public boolean hasAvailableBeds() {
        return occupancy < capacity;
    }
    
    @Override
    public String toString() {
        return "Ward{" +
                "wardId='" + wardId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", occupancy=" + occupancy +
                '}';
    }
}
