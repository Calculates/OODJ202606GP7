package com.apu.hms.accounts;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        loadAccountsFromSource();
    }

    private PatientAccount() {
    }

    public static List<String[]> getAccounts() {
        return Collections.unmodifiableList(PATIENT_ACCOUNTS);
    }

    public static boolean addAccount(String userId, String password) {
        for (String[] account : PATIENT_ACCOUNTS) {
            if (account[0].equals(userId)) {
                return false;
            }
        }
        if (!appendAccountToSource(userId, password)) {
            return false;
        }
        PATIENT_ACCOUNTS.add(new String[]{userId, password});
        return true;
    }

    private static void loadAccountsFromSource() {
        Path sourceFile = getSourceFile();
        if (!Files.isRegularFile(sourceFile)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(sourceFile, StandardCharsets.UTF_8)) {
                if (line.trim().startsWith("// ACCOUNT:")) {
                    String[] values = line.substring(line.indexOf(':') + 1).trim().split("\\|", -1);
                    if (values.length == 2 && !values[0].trim().isEmpty()
                            && findAccount(values[0].trim()) == null) {
                        PATIENT_ACCOUNTS.add(new String[]{values[0].trim(), values[1].trim()});
                    }
                }
            }
        } catch (IOException exception) {
            System.err.println("Unable to load patient accounts: " + exception.getMessage());
        }
    }

    private static boolean appendAccountToSource(String userId, String password) {
        Path sourceFile = getSourceFile();
        if (sourceFile == null) {
            return false;
        }
        String accountLine = "    // ACCOUNT: " + userId + "|" + password
                + System.lineSeparator();
        try {
            Files.write(sourceFile, accountLine.getBytes(StandardCharsets.UTF_8),
                    java.nio.file.StandardOpenOption.APPEND);
            return true;
        } catch (IOException exception) {
            System.err.println("Unable to save patient account in PatientAccount.java: "
                    + exception.getMessage());
            return false;
        }
    }

    private static String[] findAccount(String userId) {
        for (String[] account : PATIENT_ACCOUNTS) {
            if (account[0].equals(userId)) {
                return account;
            }
        }
        return null;
    }

    private static Path getSourceFile() {
        Path currentDirectory = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        Path sourceRelativePath = Paths.get("src", "com", "apu", "hms", "accounts",
                "PatientAccount.java");

        for (Path directory = currentDirectory; directory != null; directory = directory.getParent()) {
            Path sourceFile = directory.resolve(sourceRelativePath);
            if (Files.isRegularFile(sourceFile)) {
                return sourceFile;
            }
        }
        return null;
    }
}
    // ACCOUNT: lol|lol
