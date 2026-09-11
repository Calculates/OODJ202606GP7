package com.apu.hms.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient class - Can book appointments, view medical history, submit feedback
 */
public class Patient extends User {
    private static final long serialVersionUID = 1L;
    
    private String dateOfBirth;
    private String address;
    private String medicalHistory;
    private List<String> bookings;
    private List<String> prescriptions;
    
    public Patient(String userId, String name, String email, String phone, 
                   String password, String dateOfBirth, String address) {
        super(userId, name, email, phone, password, "Patient");
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.medicalHistory = "";
        this.bookings = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }
    
    public void addBooking(String booking) {
        bookings.add(booking);
    }
    
    public void removeBooking(String booking) {
        bookings.remove(booking);
    }
    
    public void addPrescription(String prescription) {
        prescriptions.add(prescription);
    }
    
    // Getters and Setters
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public List<String> getBookings() {
        return bookings;
    }
    
    public List<String> getPrescriptions() {
        return prescriptions;
    }
}
