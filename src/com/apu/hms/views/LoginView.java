package com.apu.hms.views;

import java.awt.*;
import javax.swing.*;

/**
 * LoginView - GUI for user login
 * Demonstrates use of Java Swing for graphical interface
 */
public class LoginView extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JFrame mainFrame;
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    
    public LoginView(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("APU Medical Centre - Hospital Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Login box panel
        JPanel loginBox = new JPanel();
        loginBox.setLayout(new GridLayout(4, 2, 10, 10));
        loginBox.setBorder(BorderFactory.createTitledBorder("Login"));
        loginBox.setBackground(Color.WHITE);
        loginBox.setPreferredSize(new Dimension(400, 250));
        
        // User Role
        loginBox.add(new JLabel("Select Role:"));
        roleCombo = new JComboBox<>(new String[]{"Admin", "Medical Manager", "Doctor", "Patient"});
        loginBox.add(roleCombo);
        
        // User ID
        loginBox.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        loginBox.add(userIdField);
        
        // Password
        loginBox.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginBox.add(passwordField);
        
        // Buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.addActionListener(e -> handleLogin());
        loginBox.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 153, 76));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));
        registerButton.addActionListener(e -> handleRegister());
        loginBox.add(registerButton);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(loginBox, gbc);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void handleLogin() {
        String userId = userIdField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem();
        
        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter User ID and Password", 
                "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Login feature to be implemented.\nUser: " + userId + "\nRole: " + role,
            "Login", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleRegister() {
        JOptionPane.showMessageDialog(this, "Registration feature to be implemented.",
            "Register", JOptionPane.INFORMATION_MESSAGE);
    }
}
