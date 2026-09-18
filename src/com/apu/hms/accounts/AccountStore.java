package com.apu.hms.accounts;

import com.apu.hms.utils.FileManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public final class AccountStore {
    private static final List<AccountRecord> ACCOUNTS = new ArrayList<>();
    private static final List<AppointmentRecord> APPOINTMENTS = new ArrayList<>();
    private static final String DELIMITER = FileManager.delimiter();

    static {
        loadAccounts();
        loadAppointments();
    }

    private AccountStore() {
    }

    public static AccountRecord authenticate(String userId, String password) {
        if (userId == null || password == null) {
            return null;
        }
        for (AccountRecord account : ACCOUNTS) {
            if (account.getUserId().equals(userId) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public static boolean registerPatient(String userId, String password) {
        if (!isValidAccount(userId, password) || findByUserId(userId) != null) {
            return false;
        }
        String cleanUserId = userId.trim();
        return addAccount(PatientAccount.ROLE, cleanUserId, password);
    }

    public static boolean createStaffAccount(String role, String userId, String password) {
        if (!"Doctor".equals(role) && !"Medical Manager".equals(role)) {
            return false;
        }
        return addAccount(role, userId, password);
    }

    public static List<AccountRecord> getAllAccounts() {
        return new ArrayList<>(ACCOUNTS);
    }

    public static AccountRecord getAccount(String userId) {
        return findByUserId(userId);
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

    public static boolean updateAccount(String currentUserId, String newRole, String newUserId, String newPassword) {
        if (currentUserId == null || currentUserId.trim().isEmpty()) {
            return false;
        }
        AccountRecord existing = findByUserId(currentUserId.trim());
        if (existing == null) {
            return false;
        }
        if (!isValidRole(newRole)) {
            return false;
        }

        String finalUserId = (newUserId == null) ? existing.getUserId() : newUserId.trim();
        if (finalUserId.isEmpty()) {
            return false;
        }

        if (!existing.getUserId().equals(finalUserId) && findByUserId(finalUserId) != null) {
            return false;
        }

        String finalPassword = (newPassword == null || newPassword.trim().isEmpty())
                ? existing.getPassword()
                : newPassword.trim();

        ACCOUNTS.remove(existing);
        ACCOUNTS.add(new AccountRecord(newRole.trim(), finalUserId, finalPassword));
        saveAccounts();
        return true;
    }

    public static boolean updateOwnAccount(String currentUserId, String newUserId, String newPassword) {
        AccountRecord existing = findByUserId(currentUserId);
        if (existing == null) {
            return false;
        }
        return updateAccount(existing.getUserId(), existing.getRole(), newUserId, newPassword);
    }

    public static boolean removeAccount(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }
        AccountRecord existing = findByUserId(userId.trim());
        if (existing == null) {
            return false;
        }

        if ("Admin Staff".equals(existing.getRole())) {
            return false;
        }

        ACCOUNTS.remove(existing);
        saveAccounts();
        return true;
    }

    public static boolean addAppointment(String patientId, String doctorId, LocalDateTime dateTime) {
        if (!hasRole(patientId, PatientAccount.ROLE)
                || !hasRole(doctorId, DoctorAccount.ROLE)
                || dateTime == null
                || hasAppointment(doctorId, dateTime)) {
            return false;
        }
        APPOINTMENTS.add(new AppointmentRecord(patientId.trim(), doctorId.trim(), dateTime));
        saveAppointments();
        return true;
    }

    public static List<AppointmentRecord> getAppointmentsForDoctor(String doctorId) {
        List<AppointmentRecord> matches = new ArrayList<>();
        if (doctorId == null) {
            return matches;
        }
        for (AppointmentRecord appointment : APPOINTMENTS) {
            if (appointment.getDoctorId().equals(doctorId)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    public static List<AppointmentRecord> getAllAppointments() {
        return new ArrayList<>(APPOINTMENTS);
    }

    public static List<AppointmentRecord> getAppointmentsForPatient(String patientId) {
        List<AppointmentRecord> matches = new ArrayList<>();
        if (patientId == null) {
            return matches;
        }
        for (AppointmentRecord appointment : APPOINTMENTS) {
            if (appointment.getPatientId().equals(patientId.trim())) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    public static boolean removeAppointment(String patientId, LocalDateTime dateTime) {
        if (patientId == null || dateTime == null) {
            return false;
        }
        for (AppointmentRecord appointment : APPOINTMENTS) {
            if (appointment.getPatientId().equals(patientId.trim())
                    && appointment.getDateTime().equals(dateTime)) {
                APPOINTMENTS.remove(appointment);
                saveAppointments();
                return true;
            }
        }
        return false;
    }

    public static boolean rescheduleAppointment(String patientId, LocalDateTime currentDateTime,
            LocalDateTime newDateTime) {
        if (patientId == null || currentDateTime == null || newDateTime == null
                || currentDateTime.equals(newDateTime)) {
            return false;
        }
        for (AppointmentRecord appointment : APPOINTMENTS) {
            if (appointment.getPatientId().equals(patientId.trim())
                    && appointment.getDateTime().equals(currentDateTime)) {
                if (hasAppointment(appointment.getDoctorId(), newDateTime)) {
                    return false;
                }
                APPOINTMENTS.remove(appointment);
                APPOINTMENTS.add(new AppointmentRecord(appointment.getPatientId(),
                        appointment.getDoctorId(), newDateTime));
                saveAppointments();
                return true;
            }
        }
        return false;
    }

    public static boolean addAccount(String role, String userId, String password) {
        if (!isValidRole(role) || !isValidAccount(userId, password) || findByUserId(userId) != null) {
            return false;
        }
        ACCOUNTS.add(new AccountRecord(role, userId.trim(), password));
        saveAccounts();
        return true;
    }

    private static boolean isValidAccount(String userId, String password) {
        return userId != null && !userId.trim().isEmpty()
                && password != null && !password.trim().isEmpty();
    }

    private static boolean isValidRole(String role) {
        return AdminAccount.ROLE.equals(role)
                || MedicalManagerAccount.ROLE.equals(role)
                || DoctorAccount.ROLE.equals(role)
                || PatientAccount.ROLE.equals(role);
    }

    private static AccountRecord findByUserId(String userId) {
        if (userId == null) {
            return null;
        }
        for (AccountRecord account : ACCOUNTS) {
            if (account.getUserId().equals(userId.trim())) {
                return account;
            }
        }
        return null;
    }

    private static boolean hasRole(String userId, String role) {
        AccountRecord account = findByUserId(userId);
        return account != null && role.equals(account.getRole());
    }

    private static boolean hasAppointment(String doctorId, LocalDateTime dateTime) {
        for (AppointmentRecord appointment : APPOINTMENTS) {
            if (appointment.getDoctorId().equals(doctorId.trim())
                    && appointment.getDateTime().equals(dateTime)) {
                return true;
            }
        }
        return false;
    }

    private static void loadAccounts() {
        for (String line : FileManager.readLines("accounts.txt")) {
            String[] fields = line.split(java.util.regex.Pattern.quote(DELIMITER), -1);
            if (fields.length == 3 && isValidAccount(fields[1], fields[2])) {
                ACCOUNTS.add(new AccountRecord(fields[0], fields[1], fields[2]));
            }
        }
        if (ACCOUNTS.isEmpty()) {
            ACCOUNTS.add(new AccountRecord(AdminAccount.ROLE, AdminAccount.USER_ID, AdminAccount.PASSWORD));
            ACCOUNTS.add(new AccountRecord(MedicalManagerAccount.ROLE,
                    MedicalManagerAccount.USER_ID, MedicalManagerAccount.PASSWORD));
            ACCOUNTS.add(new AccountRecord(DoctorAccount.ROLE,
                    DoctorAccount.USER_ID, DoctorAccount.PASSWORD));
            ACCOUNTS.add(new AccountRecord(PatientAccount.ROLE,
                    PatientAccount.USER_ID, PatientAccount.PASSWORD));
            saveAccounts();
        }
    }

    private static void loadAppointments() {
        for (String line : FileManager.readLines("appointments.txt")) {
            String[] fields = line.split(java.util.regex.Pattern.quote(DELIMITER), -1);
            if (fields.length == 3) {
                try {
                    APPOINTMENTS.add(new AppointmentRecord(fields[0], fields[1],
                            LocalDateTime.parse(fields[2])));
                } catch (DateTimeParseException exception) {
                }
            }
        }
    }

    private static void saveAccounts() {
        List<String> lines = new ArrayList<>();
        for (AccountRecord account : ACCOUNTS) {
            lines.add(account.getRole() + DELIMITER + account.getUserId()
                    + DELIMITER + account.getPassword());
        }
        FileManager.writeLines("accounts.txt", lines);
    }

    private static void saveAppointments() {
        List<String> lines = new ArrayList<>();
        for (AppointmentRecord appointment : APPOINTMENTS) {
            lines.add(appointment.getPatientId() + DELIMITER + appointment.getDoctorId()
                    + DELIMITER + appointment.getDateTime());
        }
        FileManager.writeLines("appointments.txt", lines);
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

        public String getPassword() {
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
