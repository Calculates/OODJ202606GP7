package com.apu.hms.models;

import java.util.EnumMap;
import java.util.Map;

public final class PermissionMatrix {
    private static final Map<String, Map<SystemModule, PermissionLevel>> PERMISSIONS =
            new java.util.HashMap<>();

    static {
        addRole("Admin Staff", PermissionLevel.READ_WRITE, PermissionLevel.READ,
            PermissionLevel.FULL, PermissionLevel.READ, PermissionLevel.FULL,
                PermissionLevel.READ_WRITE);
        addRole("Medical Manager", PermissionLevel.READ_WRITE, PermissionLevel.READ,
                PermissionLevel.READ_WRITE, PermissionLevel.READ, PermissionLevel.READ_WRITE,
                PermissionLevel.FULL);
        addRole("Doctor", PermissionLevel.NONE, PermissionLevel.FULL,
                PermissionLevel.READ_WRITE, PermissionLevel.FULL, PermissionLevel.NONE,
                PermissionLevel.READ);
        addRole("Patient", PermissionLevel.NONE, PermissionLevel.READ_WRITE,
                PermissionLevel.READ_WRITE, PermissionLevel.READ, PermissionLevel.READ_WRITE,
                PermissionLevel.NONE);
    }

    private PermissionMatrix() {
    }

    private static void addRole(String role, PermissionLevel userStaff,
                                PermissionLevel healthRecords,
                                PermissionLevel appointments,
                                PermissionLevel prescriptions,
                                PermissionLevel billing,
                                PermissionLevel settingsReports) {
        Map<SystemModule, PermissionLevel> rolePermissions = new EnumMap<>(SystemModule.class);
        rolePermissions.put(SystemModule.USER_AND_STAFF_MANAGEMENT, userStaff);
        rolePermissions.put(SystemModule.PATIENT_HEALTH_RECORDS, healthRecords);
        rolePermissions.put(SystemModule.APPOINTMENTS_AND_SCHEDULING, appointments);
        rolePermissions.put(SystemModule.PRESCRIPTIONS_AND_TREATMENTS, prescriptions);
        rolePermissions.put(SystemModule.BILLING_AND_PAYMENTS, billing);
        rolePermissions.put(SystemModule.SYSTEM_SETTINGS_AND_REPORTS, settingsReports);
        PERMISSIONS.put(role, rolePermissions);
    }

    public static PermissionLevel getPermission(String role, SystemModule module) {
        Map<SystemModule, PermissionLevel> rolePermissions = PERMISSIONS.get(role);
        if (rolePermissions == null) {
            return PermissionLevel.NONE;
        }
        return rolePermissions.getOrDefault(module, PermissionLevel.NONE);
    }

    public static boolean canRead(String role, SystemModule module) {
        PermissionLevel permission = getPermission(role, module);
        return permission != PermissionLevel.NONE;
    }

    public static boolean canWrite(String role, SystemModule module) {
        PermissionLevel permission = getPermission(role, module);
        return permission == PermissionLevel.READ_WRITE || permission == PermissionLevel.FULL;
    }

    public static boolean canCreateOrDelete(String role, SystemModule module) {
        return getPermission(role, module) == PermissionLevel.FULL;
    }
}
