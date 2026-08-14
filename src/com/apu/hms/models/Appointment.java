package com.apu.hms.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Appointment class - Represents a consultation appointment
 */
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentDate;
    private String status;  // "Scheduled", "Completed", "Cancelled"
    private String consultationNotes;
    private double consultationFee;
    
    public Appointment(String appointmentId, String patientId, String doctorId,
                      LocalDateTime appointmentDate, double consultationFee) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = "Scheduled";
        this.consultationNotes = "";
        this.consultationFee = consultationFee;
    }
    
    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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
    
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getConsultationNotes() {
        return consultationNotes;
    }
    
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                '}';
    }
}
