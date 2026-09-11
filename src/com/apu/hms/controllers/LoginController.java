package com.apu.hms.controllers;

import com.apu.hms.accounts.AccountStore;

public class LoginController {
    public AccountStore.AccountRecord login(String userId, String password) {
        if (userId == null || password == null
                || userId.trim().isEmpty() || password.isEmpty()) {
            return null;
        }
        return AccountStore.authenticate(userId.trim(), password);
    }

    public boolean registerPatient(String userId, String password, String confirmation) {
        if (password == null || !password.equals(confirmation) || password.isEmpty()) {
            return false;
        }
        return AccountStore.registerPatient(userId, password);
    }

    public boolean createStaffAccount(String role, String userId, String password) {
        return AccountStore.createStaffAccount(role, userId, password);
    }

    public boolean updateAccount(String currentUserId, String newRole, String newUserId, String newPassword) {
        return AccountStore.updateAccount(currentUserId, newRole, newUserId, newPassword);
    }

    public boolean removeAccount(String userId) {
        return AccountStore.removeAccount(userId);
    }
}
