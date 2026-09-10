package com.apu.hms.views;

import com.apu.hms.accounts.AccountStore;
import com.apu.hms.controllers.LoginController;
import java.awt.*;
import javax.swing.*;

/**
 * LoginView - GUI for user login
 * Demonstrates use of Java Swing for graphical interface
 */
public class LoginView extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private final JFrame mainFrame;
    private final LoginController loginController;
    private JTextField userIdField;
    private JPasswordField passwordField;
    
    public LoginView(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.loginController = new LoginController();
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
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.addActionListener(e -> handleLogin());
        loginBox.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 153, 76));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));
        registerButton.addActionListener(e -> handleRegister());
        loginBox.add(registerButton);

        loginBox.add(new JLabel());
        JButton quitButton = new JButton("Quit");
        quitButton.setForeground(Color.BLACK);
        quitButton.setFont(new Font("Arial", Font.BOLD, 12));
        quitButton.addActionListener(e -> System.exit(0));
        loginBox.add(quitButton);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(loginBox, gbc);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter User ID and Password", 
                "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        AccountStore.AccountRecord account = loginController.login(userId, password);
        if (account == null) {
            JOptionPane.showMessageDialog(this, "Invalid User ID or Password.",
                "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        passwordField.setText("");
        mainFrame.setContentPane(new DashboardView(mainFrame, account.getUserId(), account.getRole()));
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    private void handleRegister() {
        JTextField newUserId = new JTextField();
        JPasswordField newPassword = new JPasswordField();
        JPasswordField confirmPassword = new JPasswordField();
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Patient User ID:"));
        form.add(newUserId);
        form.add(new JLabel("Password:"));
        form.add(newPassword);
        form.add(new JLabel("Confirm Password:"));
        form.add(confirmPassword);

        int result = JOptionPane.showConfirmDialog(this, form,
            "Register Patient Account", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String password = new String(newPassword.getPassword());
        String confirmation = new String(confirmPassword.getPassword());
        if (!password.equals(confirmation) || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.",
                "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (loginController.registerPatient(newUserId.getText(), password, confirmation)) {
            JOptionPane.showMessageDialog(this, "Patient account created successfully.",
                "Registration", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "User ID is empty or already exists.",
                "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
