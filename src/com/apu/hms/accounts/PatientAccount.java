package com.apu.hms.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PatientAccount {
    public static final String ROLE = "Patient";
    public static final String USER_ID = "patient";
    public static final String PASSWORD = "patient123";
    private static final List<String[]> PATIENT_ACCOUNTS = new ArrayList<>();

    static {
        PATIENT_ACCOUNTS.add(new String[]{USER_ID, PASSWORD});
    }

    private PatientAccount() {
    }

    public static List<String[]> getAccounts() {
        return Collections.unmodifiableList(PATIENT_ACCOUNTS);
    }

    public static boolean addAccount(String userId, String password) {
        if (findAccount(userId) != null) {
            return false;
        }
        PATIENT_ACCOUNTS.add(new String[]{userId, password});
        return true;
    }

    private static String[] findAccount(String userId) {
        for (String[] account : PATIENT_ACCOUNTS) {
            if (account[0].equals(userId)) {
                return account;
            }
        }
        return null;
    }
}
