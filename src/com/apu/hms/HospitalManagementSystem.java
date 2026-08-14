package com.apu.hms;

import com.apu.hms.views.LoginView;
import javax.swing.*;

/**
 * HospitalManagementSystem - Main application entry point
 * Demonstrates the complete Hospital Management System with GUI
 */
public class HospitalManagementSystem {
    
    public static void main(String[] args) {
        // Create main application window
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Hospital Management System - APU Medical Centre");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(900, 600);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setResizable(false);
            
            // Set Look and Feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Add login view to main frame
            mainFrame.add(new LoginView(mainFrame));
            mainFrame.setVisible(true);
        });
    }
}
