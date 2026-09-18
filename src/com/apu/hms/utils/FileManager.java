package com.apu.hms.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager - central text-file storage utility for the Hospital Management System.
 *
 * Two levels of access:
 *   - line level    : readLines / writeLines work with raw lines
 *   - record level  : readRecords / writeRecords / appendRecord split and join
 *                     fields automatically, escaping any character that would
 *                     break the format
 *
 * Records are stored one per line, fields separated by '|'. The characters
 * '|', '\' and line breaks are escaped on write and restored on read, so
 * free-text fields such as consultation notes cannot corrupt the file.
 *
 * The class is stateless and knows nothing about the fields of any model.
 */
public final class FileManager {

    private static final String DATA_FOLDER = "data";
    private static final String DELIMITER = "|";
    private static final char DELIMITER_CHAR = '|';
    private static final char ESCAPE_CHAR = '\\';

    private FileManager() {
    }

    // ---------------- line level ----------------

    public static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(DATA_FOLDER, fileName);
        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read " + file, exception);
        }
        return lines;
    }

    public static void writeLines(String fileName, List<String> lines) {
        File file = prepareFile(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write " + file, exception);
        }
    }

    public static void appendLine(String fileName, String line) {
        File file = prepareFile(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to append to " + file, exception);
        }
    }

    // ---------------- record level ----------------

    /**
     * Reads every record, already split into fields with escaping resolved.
     * Returns an empty list when the file does not exist yet.
     */
    public static List<String[]> readRecords(String fileName) {
        List<String[]> records = new ArrayList<>();
        for (String line : readLines(fileName)) {
            records.add(splitLine(line));
        }
        return records;
    }

    /**
     * Overwrites the file with the given records.
     * Used for update and delete, where the whole file has to be rewritten.
     */
    public static void writeRecords(String fileName, List<String[]> records) {
        List<String> lines = new ArrayList<>();
        for (String[] fields : records) {
            lines.add(joinFields(fields));
        }
        writeLines(fileName, lines);
    }

    /**
     * Adds a single record to the end of the file without rewriting it.
     */
    public static void appendRecord(String fileName, String[] fields) {
        appendLine(fileName, joinFields(fields));
    }

    // ---------------- helpers ----------------

    public static String delimiter() {
        return DELIMITER;
    }

    public static boolean fileExists(String fileName) {
        return new File(DATA_FOLDER, fileName).exists();
    }

    /**
     * Splits one stored line into fields, restoring escaped characters.
     * Always use this instead of String.split(delimiter()) - '|' is a regular
     * expression operator, so split("|") breaks the line into single characters.
     */
    public static String[] splitLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean escaped = false;

        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);

            if (escaped) {
                field.append(symbol == 'n' ? '\n' : symbol);
                escaped = false;
            } else if (symbol == ESCAPE_CHAR) {
                escaped = true;
            } else if (symbol == DELIMITER_CHAR) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(symbol);
            }
        }

        fields.add(field.toString());
        return fields.toArray(new String[0]);
    }

    /**
     * Joins fields into one line, escaping anything that would break the format.
     */
    public static String joinFields(String[] fields) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                line.append(DELIMITER_CHAR);
            }
            line.append(escape(fields[i]));
        }
        return line.toString();
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }

        StringBuilder escaped = new StringBuilder();
        for (char symbol : value.toCharArray()) {
            if (symbol == ESCAPE_CHAR || symbol == DELIMITER_CHAR) {
                escaped.append(ESCAPE_CHAR).append(symbol);
            } else if (symbol == '\n') {
                escaped.append(ESCAPE_CHAR).append('n');
            } else if (symbol != '\r') {
                escaped.append(symbol);
            }
        }
        return escaped.toString();
    }

    private static File prepareFile(String fileName) {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IllegalStateException("Unable to create data folder");
        }
        return new File(folder, fileName);
    }
}