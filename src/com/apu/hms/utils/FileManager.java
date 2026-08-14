package com.apu.hms.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager class - Handles serialization and deserialization of objects to/from text files
 */
public class FileManager {
    
    /**
     * Save object to file using Java serialization
     */
    public static void saveObject(String filePath, Object obj) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(obj);
        }
    }
    
    /**
     * Load object from file using Java deserialization
     */
    public static Object loadObject(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            return ois.readObject();
        }
    }
    
    /**
     * Save list of objects to a text file
     */
    public static void saveListToFile(String filePath, List<String> lines) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }
    
    /**
     * Load list of strings from a text file
     */
    public static List<String> loadListFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return lines;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
    
    /**
     * Check if file exists
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
    
    /**
     * Create directory if it doesn't exist
     */
    public static void createDirectoryIfNotExists(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Delete file
     */
    public static boolean deleteFile(String filePath) {
        return new File(filePath).delete();
    }
}
