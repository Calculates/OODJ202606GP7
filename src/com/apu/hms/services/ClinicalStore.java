package com.apu.hms.services;

import com.apu.hms.utils.FileManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class ClinicalStore {
    private static final String DELIMITER = FileManager.delimiter();
    private static final List<String> WARDS = load("wards.txt");
    private static final List<String> DEPARTMENTS = load("departments.txt");
    private static final List<String> ASSESSMENT_TYPES = load("assessment_types.txt");
    private static final List<ClinicalRecord> ASSESSMENTS = loadClinicalRecords("assessments.txt");
    private static final List<ClinicalRecord> FEEDBACK = loadClinicalRecords("clinical_feedback.txt");
    private static final List<ClinicalRecord> PRESCRIPTIONS = loadClinicalRecords("prescriptions.txt");
    private static final List<String> LAB_REQUESTS = load("lab_requests.txt");
    private static final List<String> DOCTOR_ROSTERS = load("doctor_rosters.txt");
    private static final List<BillRecord> BILLS = loadBills();

    private ClinicalStore() {
    }

    public static boolean addWard(String name, String type, int capacity) {
        if (isBlank(name) || isBlank(type) || capacity < 1) {
            return false;
        }
        String value = clean(name) + DELIMITER + clean(type) + DELIMITER + capacity;
        if (containsValue(WARDS, value)) {
            return false;
        }
        WARDS.add(value);
        save("wards.txt", WARDS);
        return true;
    }

    public static boolean addDepartment(String name, String specialty) {
        if (isBlank(name) || isBlank(specialty)) {
            return false;
        }
        String value = clean(name) + DELIMITER + clean(specialty);
        if (containsValue(DEPARTMENTS, value)) {
            return false;
        }
        DEPARTMENTS.add(value);
        save("departments.txt", DEPARTMENTS);
        return true;
    }

    public static boolean addAssessmentType(String name, String description) {
        if (isBlank(name) || isBlank(description)) {
            return false;
        }
        String value = clean(name) + DELIMITER + clean(description);
        if (containsValue(ASSESSMENT_TYPES, value)) {
            return false;
        }
        ASSESSMENT_TYPES.add(value);
        save("assessment_types.txt", ASSESSMENT_TYPES);
        return true;
    }

    public static boolean addAssessment(String patientId, String doctorId, String type,
            String result, String labResult) {
        if (isBlank(patientId) || isBlank(doctorId) || isBlank(type) || isBlank(result)) {
            return false;
        }
        ASSESSMENTS.add(new ClinicalRecord(patientId, doctorId, type,
                result, isBlank(labResult) ? "" : clean(labResult), LocalDateTime.now()));
        saveClinicalRecords("assessments.txt", ASSESSMENTS);
        return true;
    }

    public static boolean addFeedback(String patientId, String doctorId, String feedback) {
        if (isBlank(patientId) || isBlank(doctorId) || isBlank(feedback)) {
            return false;
        }
        FEEDBACK.add(new ClinicalRecord(patientId, doctorId, "Feedback",
                clean(feedback), "", LocalDateTime.now()));
        saveClinicalRecords("clinical_feedback.txt", FEEDBACK);
        return true;
    }

    public static boolean addPrescription(String patientId, String doctorId, String medicine) {
        if (isBlank(patientId) || isBlank(doctorId) || isBlank(medicine)) {
            return false;
        }
        PRESCRIPTIONS.add(new ClinicalRecord(patientId, doctorId, "Prescription",
                clean(medicine), "", LocalDateTime.now()));
        saveClinicalRecords("prescriptions.txt", PRESCRIPTIONS);
        return true;
    }

    public static boolean addPatientFeedback(String patientId, String feedback) {
        return addFeedback(patientId, patientId, feedback);
    }

    public static boolean addLabRequest(String patientId, String doctorId, String request) {
        if (isBlank(patientId) || isBlank(doctorId) || isBlank(request)) {
            return false;
        }
        LAB_REQUESTS.add(clean(patientId) + DELIMITER + clean(doctorId) + DELIMITER + clean(request));
        save("lab_requests.txt", LAB_REQUESTS);
        return true;
    }

    public static List<String> getLabRequests(String doctorId) {
        List<String> matches = new ArrayList<>();
        if (isBlank(doctorId)) {
            return matches;
        }
        for (String request : LAB_REQUESTS) {
            String[] fields = split(request);
            if (fields.length == 3 && fields[1].equals(doctorId.trim())) {
                matches.add("Patient: " + fields[0] + " | Request: " + fields[2]);
            }
        }
        return matches;
    }

    public static boolean addDoctorRoster(String doctorId, String shift) {
        if (isBlank(doctorId) || isBlank(shift)) {
            return false;
        }
        String value = clean(doctorId) + DELIMITER + clean(shift);
        if (containsValue(DOCTOR_ROSTERS, value)) {
            return false;
        }
        DOCTOR_ROSTERS.add(value);
        save("doctor_rosters.txt", DOCTOR_ROSTERS);
        return true;
    }

    public static List<String> getDoctorRosters() {
        return new ArrayList<>(DOCTOR_ROSTERS);
    }

    public static boolean addBill(String patientId, String grade, double consultation,
            double lab, double medication) {
        if (isBlank(patientId) || !isGrade(grade)
            || !Double.isFinite(consultation) || !Double.isFinite(lab)
            || !Double.isFinite(medication)
            || consultation < 0 || lab < 0 || medication < 0) {
            return false;
        }
        BILLS.add(new BillRecord(patientId, grade, consultation, lab, medication,
                consultation + lab + medication, "Pending", LocalDateTime.now()));
        saveBills();
        return true;
    }

    public static List<String> getPatientBills(String patientId) {
        List<String> matches = new ArrayList<>();
        if (isBlank(patientId)) {
            return matches;
        }
        for (BillRecord bill : BILLS) {
            if (bill.patientId.equals(patientId.trim())) {
                matches.add(bill.toDisplayString());
            }
        }
        return matches;
    }

    public static List<BillSummary> getPatientBillSummaries(String patientId) {
        return getPatientBillSummaries(patientId, null);
    }

    public static List<BillSummary> getPatientBillSummaries(String patientId, String status) {
        List<BillSummary> matches = new ArrayList<>();
        if (isBlank(patientId)) {
            return matches;
        }
        for (BillRecord bill : BILLS) {
            if (bill.patientId.equals(patientId.trim())
                    && (status == null || status.equals(bill.status))) {
                matches.add(new BillSummary(bill.createdAt, bill.toDisplayString()));
            }
        }
        return matches;
    }

    public static boolean payBill(String patientId, LocalDateTime createdAt) {
        if (isBlank(patientId) || createdAt == null) {
            return false;
        }
        for (BillRecord bill : BILLS) {
            if (bill.patientId.equals(patientId.trim()) && bill.createdAt.equals(createdAt)
                    && "Pending".equals(bill.status)) {
                bill.status = "Paid";
                saveBills();
                return true;
            }
        }
        return false;
    }

    public static List<String> getWards() {
        return new ArrayList<>(WARDS);
    }

    public static List<String> getDepartments() {
        return new ArrayList<>(DEPARTMENTS);
    }

    public static List<String> getAssessmentTypes() {
        return new ArrayList<>(ASSESSMENT_TYPES);
    }

    public static List<String> getClinicalRecords(String patientId, List<ClinicalRecord> records) {
        List<String> matches = new ArrayList<>();
        for (ClinicalRecord record : records) {
            if (record.patientId.equals(patientId)) {
                matches.add(record.toDisplayString());
            }
        }
        return matches;
    }

    public static List<String> getPatientHistory(String patientId) {
        List<String> history = getClinicalRecords(patientId, ASSESSMENTS);
        history.addAll(getClinicalRecords(patientId, FEEDBACK));
        return history;
    }

    public static List<String> getPatientPrescriptions(String patientId) {
        return getClinicalRecords(patientId, PRESCRIPTIONS);
    }

    public static String getReport() {
        double total = 0;
        int pending = 0;
        for (BillRecord bill : BILLS) {
            total += bill.total;
            if ("Pending".equals(bill.status)) {
                pending++;
            }
        }
        return "Analytical Report\n\n"
                + "Wards/clinics: " + WARDS.size() + "\n"
                + "Departments/specialties: " + DEPARTMENTS.size() + "\n"
                + "Assessment types: " + ASSESSMENT_TYPES.size() + "\n"
                + "Medical assessments: " + ASSESSMENTS.size() + "\n"
                + "Clinical feedback records: " + FEEDBACK.size() + "\n"
                + "Prescriptions: " + PRESCRIPTIONS.size() + "\n"
                + "Bills: " + BILLS.size() + "\n"
                + String.format("Total billed: %.2f\n", total)
                + "Pending bills: " + pending;
    }

    public static final class BillSummary {
        private final LocalDateTime createdAt;
        private final String displayText;

        private BillSummary(LocalDateTime createdAt, String displayText) {
            this.createdAt = createdAt;
            this.displayText = displayText;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public String getDisplayText() {
            return displayText;
        }
    }

    private static List<String> load(String fileName) {
        return new ArrayList<>(FileManager.readLines(fileName));
    }

    private static List<ClinicalRecord> loadClinicalRecords(String fileName) {
        List<ClinicalRecord> records = new ArrayList<>();
        for (String line : FileManager.readLines(fileName)) {
            String[] fields = split(line);
            if (fields.length == 6) {
                records.add(new ClinicalRecord(fields[0], fields[1], fields[2],
                        fields[3], fields[4], LocalDateTime.parse(fields[5])));
            }
        }
        return records;
    }

    private static List<BillRecord> loadBills() {
        List<BillRecord> bills = new ArrayList<>();
        for (String line : FileManager.readLines("bills.txt")) {
            String[] fields = split(line);
            if (fields.length == 8) {
                bills.add(new BillRecord(fields[0], fields[1], Double.parseDouble(fields[2]),
                        Double.parseDouble(fields[3]), Double.parseDouble(fields[4]),
                        Double.parseDouble(fields[5]), fields[6], LocalDateTime.parse(fields[7])));
            }
        }
        return bills;
    }

    private static void save(String fileName, List<String> values) {
        FileManager.writeLines(fileName, values);
    }

    private static void saveClinicalRecords(String fileName, List<ClinicalRecord> records) {
        List<String> lines = new ArrayList<>();
        for (ClinicalRecord record : records) {
            lines.add(record.patientId + DELIMITER + record.doctorId + DELIMITER + record.type
                    + DELIMITER + record.result + DELIMITER + record.labResult + DELIMITER + record.createdAt);
        }
        save(fileName, lines);
    }

    private static void saveBills() {
        List<String> lines = new ArrayList<>();
        for (BillRecord bill : BILLS) {
            lines.add(bill.patientId + DELIMITER + bill.grade + DELIMITER + bill.consultation
                    + DELIMITER + bill.lab + DELIMITER + bill.medication + DELIMITER + bill.total
                    + DELIMITER + bill.status + DELIMITER + bill.createdAt);
        }
        save("bills.txt", lines);
    }

    private static String[] split(String line) {
        return line.split(java.util.regex.Pattern.quote(DELIMITER), -1);
    }

    private static boolean containsValue(List<String> values, String value) {
        return values.contains(value);
    }

    private static boolean isGrade(String grade) {
        return "Good".equalsIgnoreCase(grade) || "Fair".equalsIgnoreCase(grade)
                || "Poor".equalsIgnoreCase(grade);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String clean(String value) {
        return value.trim().replace(DELIMITER, "/");
    }

    public static final class ClinicalRecord {
        private final String patientId;
        private final String doctorId;
        private final String type;
        private final String result;
        private final String labResult;
        private final LocalDateTime createdAt;

        private ClinicalRecord(String patientId, String doctorId, String type,
                String result, String labResult, LocalDateTime createdAt) {
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.type = type;
            this.result = result;
            this.labResult = labResult;
            this.createdAt = createdAt;
        }

        private String toDisplayString() {
            return type + " | " + result
                    + (labResult.isEmpty() ? "" : " | Lab: " + labResult)
                    + " | By: " + doctorId + " | " + createdAt;
        }
    }

    private static final class BillRecord {
        private final String patientId;
        private final String grade;
        private final double consultation;
        private final double lab;
        private final double medication;
        private final double total;
        private String status;
        private final LocalDateTime createdAt;

        private BillRecord(String patientId, String grade, double consultation,
                double lab, double medication, double total, String status,
                LocalDateTime createdAt) {
            this.patientId = patientId;
            this.grade = grade;
            this.consultation = consultation;
            this.lab = lab;
            this.medication = medication;
            this.total = total;
            this.status = status;
            this.createdAt = createdAt;
        }

        private String toDisplayString() {
            return "Grade: " + grade + " | Consultation: " + consultation
                    + " | Lab: " + lab + " | Medication: " + medication
                    + " | Total: " + String.format("%.2f", total)
                    + " | Status: " + status + " | Date: " + createdAt;
        }
    }
}
