package com.apu.hms.accounts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class AccountStore {
    private static final List<AccountRecord> ACCOUNTS = new ArrayList<>();
    private static final List<AppointmentRecord> APPOINTMENTS = new ArrayList<>();

    static {
        ACCOUNTS.add(new AccountRecord(AdminAccount.ROLE, AdminAccount.USER_ID, AdminAccount.PASSWORD));
        ACCOUNTS.add(new AccountRecord(MedicalManagerAccount.ROLE,
                MedicalManagerAccount.USER_ID, MedicalManagerAccount.PASSWORD));
        ACCOUNTS.add(new AccountRecord(DoctorAccount.ROLE,
                DoctorAccount.USER_ID, DoctorAccount.PASSWORD));
        for (String[] patientAccount : PatientAccount.getAccounts()) {
            ACCOUNTS.add(new AccountRecord(PatientAccount.ROLE,
                patientAccount[0], patientAccount[1]));
        }
    }

    private AccountStore() {
    }

    public static AccountRecord authenticate(String userId, String password) {
        for (AccountRecord account : ACCOUNTS) {
            if (account.getUserId().equals(userId) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public static boolean registerPatient(String userId, String password) {
        if (userId == null || userId.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || findByUserId(userId) != null) {
            return false;
        }
        if (!PatientAccount.addAccount(userId.trim(), password)) {
            return false;
        }
        ACCOUNTS.add(new AccountRecord(PatientAccount.ROLE, userId.trim(), password));
        return true;
    }

    public static boolean createStaffAccount(String role, String userId, String password) {
        if (!"Doctor".equals(role) && !"Medical Manager".equals(role)) {
            return false;
        }
        return addAccount(role, userId, password);
    }

    public static List<AccountRecord> getAccountsByRole(String role) {
        List<AccountRecord> matches = new ArrayList<>();
        for (AccountRecord account : ACCOUNTS) {
            if (account.getRole().equals(role)) {
                matches.add(account);
            }
        }
        return matches;
    }

    public static void addAppointment(String patientId, String doctorId, LocalDateTime dateTime) {
        APPOINTMENTS.add(new AppointmentRecord(patientId, doctorId, dateTime));
    }

    public static List<AppointmentRecord> getAppointmentsForDoctor(String doctorId) {
        List<AppointmentRecord> matches = new ArrayList<>();
        for (AppointmentRecord appointment : APPOINTMENTS) {
            if (appointment.getDoctorId().equals(doctorId)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    private static boolean addAccount(String role, String userId, String password) {
        if (userId == null || userId.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || findByUserId(userId) != null) {
            return false;
        }
        ACCOUNTS.add(new AccountRecord(role, userId.trim(), password));
        return true;
    }

    private static AccountRecord findByUserId(String userId) {
        for (AccountRecord account : ACCOUNTS) {
            if (account.getUserId().equals(userId.trim())) {
                return account;
            }
        }
        return null;
    }

    public static final class AccountRecord {
        private final String role;
        private final String userId;
        private final String password;

        private AccountRecord(String role, String userId, String password) {
            this.role = role;
            this.userId = userId;
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public String getUserId() {
            return userId;
        }

        private String getPassword() {
            return password;
        }
    }

    public static final class AppointmentRecord {
        private final String patientId;
        private final String doctorId;
        private final LocalDateTime dateTime;

        private AppointmentRecord(String patientId, String doctorId, LocalDateTime dateTime) {
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.dateTime = dateTime;
        }

        public String getPatientId() {
            return patientId;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}
