package com.apu.hms.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class AccessControl {
    private static final Set<String> ADMIN_ACTIONS = new HashSet<>(Arrays.asList(
            "Manage user accounts",
            "Assign doctors to medical managers",
            "Manage hospital rooms, wards, labs and imaging facilities",
            "Configure consultation rates and insurance networks",
            "View hospital records and reports"
    ));

    private static final Set<String> MEDICAL_MANAGER_ACTIONS = new HashSet<>(Arrays.asList(
            "Edit personal profile",
            "Create and update clinical departments",
            "Design doctor shift rosters",
            "View patient health records",
            "View appointments and scheduling",
            "View prescriptions and treatments",
            "View billing and payment records",
            "View hospital metrics and revenue reports",
            "Oversee department staff"
    ));

    private static final Set<String> DOCTOR_ACTIONS = new HashSet<>(Arrays.asList(
            "Edit personal profile",
            "View appointment schedule",
            "Log patient vital signs",
            "Write consultation notes",
            "Issue digital prescriptions",
            "Request lab tests and imaging",
            "View patient medical history"
    ));

    private static final Set<String> PATIENT_ACTIONS = new HashSet<>(Arrays.asList(
            "Edit personal profile",
            "Browse doctor consultation slots",
            "Book, reschedule or cancel appointments",
            "View personal medical history",
            "View prescriptions",
            "Submit ratings and comments"
    ));

    private AccessControl() {
    }

    public static boolean canAccess(String role, String action) {
        if (role == null || action == null) {
            return false;
        }

        String normalizedAction = action.trim();
        switch (role.trim()) {
            case "Admin Staff":
                return ADMIN_ACTIONS.contains(normalizedAction);
            case "Medical Manager":
                return MEDICAL_MANAGER_ACTIONS.contains(normalizedAction);
            case "Doctor":
                return DOCTOR_ACTIONS.contains(normalizedAction);
            case "Patient":
                return PATIENT_ACTIONS.contains(normalizedAction);
            default:
                return false;
        }
    }
}
