package com.apu.hms.tests;

import com.apu.hms.services.AccessControl;

public class RoleAccessTest {
    public static void main(String[] args) {
        assertAccess("Admin Staff", "Manage user accounts", true);
        assertAccess("Doctor", "View appointment schedule", true);
        assertAccess("Doctor", "Manage user accounts", false);
        assertAccess("Patient", "Book, reschedule or cancel appointments", true);
        assertAccess("Patient", "Manage user accounts", false);
        assertAccess("Medical Manager", "View patient health records", true);
        System.out.println("Role access checks passed.");
    }

    private static void assertAccess(String role, String action, boolean expected) {
        boolean actual = AccessControl.canAccess(role, action);
        if (actual != expected) {
            throw new AssertionError("Role " + role + " action '" + action + "' expected " + expected + " but got " + actual);
        }
    }
}
