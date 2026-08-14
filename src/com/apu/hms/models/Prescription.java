package com.apu.hms.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Prescription class - Represents a digital medication prescription
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String prescriptionId;
    private String patientId;
    private String doctorId;
    private LocalDateTime issuedDate;
    private String medication;
    private String dosage;
    private String frequency;  // e.g., "Once daily", "Twice daily"
    private int durationDays;
    private String notes;
    
    public Prescription(String prescriptionId, String patientId, String doctorId,
                       LocalDateTime issuedDate, String medication, String dosage,
                       String frequency, int durationDays) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.issuedDate = issuedDate;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.notes = "";
    }
    
    // Getters and Setters
    public String getPrescriptionId() {
        return prescriptionId;
    }
    
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    
    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }
    
    public void setIssuedDate(LocalDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }
    
    public String getMedication() {
        return medication;
    }
    
    public void setMedication(String medication) {
        this.medication = medication;
    }
    
    public String getDosage() {
        return dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public int getDurationDays() {
        return durationDays;
    }
    
    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
