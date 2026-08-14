package com.apu.hms.utils;

import java.util.regex.Pattern;

/**
 * ValidationUtil class - Provides input validation methods
 */
public class ValidationUtil {
    
    // Email regex pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    // Phone number pattern (simple: 10-12 digits)
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\d{10,12}$");
    
    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate phone number format
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validate if string is not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Validate if string has minimum length
     */
    public static boolean hasMinLength(String str, int minLength) {
        return str != null && str.length() >= minLength;
    }
    
    /**
     * Validate if password is strong (minimum 8 chars, mix of upper/lower/digits)
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        
        return hasUpper && hasLower && hasDigit;
    }
    
    /**
     * Validate if input is a valid integer
     */
    public static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate if input is a valid double
     */
    public static boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate date format (dd/MM/yyyy)
     */
    public static boolean isValidDate(String date) {
        return date != null && Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$").matcher(date).matches();
    }
}
