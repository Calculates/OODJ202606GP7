package com.apu.hms.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Billing class - Represents patient billing and medical grades based on health metrics
 */
public class Billing implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String billId;
    private String patientId;
    private LocalDateTime billDate;
    private double consultationCost;
    private double labTestCost;
    private double medicationCost;
    private double totalCost;
    private String insuranceProvider;
    private String medicalGrade;  // "Good", "Fair", "Poor" based on health metrics
    private String paymentStatus;  // "Paid", "Pending", "Overdue"
    
    public Billing(String billId, String patientId, LocalDateTime billDate,
                   String insuranceProvider) {
        this.billId = billId;
        this.patientId = patientId;
        this.billDate = billDate;
        this.insuranceProvider = insuranceProvider;
        this.paymentStatus = "Pending";
        this.consultationCost = 0;
        this.labTestCost = 0;
        this.medicationCost = 0;
        this.totalCost = 0;
    }
    
    public void calculateTotalCost() {
        this.totalCost = consultationCost + labTestCost + medicationCost;
    }
    
    public void assignMedicalGrade(String grade) {
        if (grade.equalsIgnoreCase("Good") || 
            grade.equalsIgnoreCase("Fair") || 
            grade.equalsIgnoreCase("Poor")) {
            this.medicalGrade = grade;
        }
    }
    
    // Getters and Setters
    public String getBillId() {
        return billId;
    }
    
    public void setBillId(String billId) {
        this.billId = billId;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
    
    public double getConsultationCost() {
        return consultationCost;
    }
    
    public void setConsultationCost(double consultationCost) {
        this.consultationCost = consultationCost;
    }
    
    public double getLabTestCost() {
        return labTestCost;
    }
    
    public void setLabTestCost(double labTestCost) {
        this.labTestCost = labTestCost;
    }
    
    public double getMedicationCost() {
        return medicationCost;
    }
    
    public void setMedicationCost(double medicationCost) {
        this.medicationCost = medicationCost;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getInsuranceProvider() {
        return insuranceProvider;
    }
    
    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }
    
    public String getMedicalGrade() {
        return medicalGrade;
    }
    
    public void setMedicalGrade(String medicalGrade) {
        this.medicalGrade = medicalGrade;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
