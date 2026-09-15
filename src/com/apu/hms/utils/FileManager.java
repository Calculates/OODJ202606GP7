package com.apu.hms.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileManager {

    private static final String DATA_FOLDER = "data";
    private static final String DELIMITER = "|";

    private FileManager() {
    }

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
        File folder = new File(DATA_FOLDER);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IllegalStateException("Unable to create data folder");
        }

        File file = new File(folder, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write " + file, exception);
        }
    }

    public static String delimiter() {
        return DELIMITER;
    }
}