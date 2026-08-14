package com.apu.hms.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MedicalAssessment class - Records patient vital signs and medical assessments
 */
public class MedicalAssessment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String assessmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime assessmentDate;
    private String assessmentType;  // e.g., "General Check-up", "Lab Test", "X-ray"
    private String findings;
    private String diagnosis;
    private double temperature;
    private int bloodPressure;  // systolic
    private int heartRate;
    private double weight;
    
    public MedicalAssessment(String assessmentId, String patientId, String doctorId,
                            LocalDateTime assessmentDate, String assessmentType) {
        this.assessmentId = assessmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.assessmentDate = assessmentDate;
        this.assessmentType = assessmentType;
        this.findings = "";
        this.diagnosis = "";
    }
    
    // Getters and Setters
    public String getAssessmentId() {
        return assessmentId;
    }
    
    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
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
    
    public LocalDateTime getAssessmentDate() {
        return assessmentDate;
    }
    
    public void setAssessmentDate(LocalDateTime assessmentDate) {
        this.assessmentDate = assessmentDate;
    }
    
    public String getAssessmentType() {
        return assessmentType;
    }
    
    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }
    
    public String getFindings() {
        return findings;
    }
    
    public void setFindings(String findings) {
        this.findings = findings;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public int getBloodPressure() {
        return bloodPressure;
    }
    
    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
    
    public int getHeartRate() {
        return heartRate;
    }
    
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
