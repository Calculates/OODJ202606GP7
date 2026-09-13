package com.apu.hms.views;

import com.apu.hms.accounts.AccountStore;
import com.apu.hms.controllers.LoginController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.*;

public class LoginView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Color BACKGROUND = new Color(232, 244, 252);
    private static final Color BUTTON_BLUE = new Color(103, 181, 235);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);

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
        setBackground(BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(28, 48, 36, 48));

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(8, 8, 8, 8);

        JLabel logo = new JLabel("<html><div style='text-align:center;'>"
                + "<font color='#0b8f4d' size='10'><b>H</b></font> "
                + "<font color='#1655a8' size='10'><b>M</b></font> "
                + "<font color='#0b8f4d' size='10'><b>S</b></font><br>"
                + "<font color='#1f2937' size='4'>Hospital Management System</font>"
                + "</div></html>");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(180, 90));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0.25;
        content.add(logo, constraints);

        JLabel title = new JLabel("LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(25, 47, 75));
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 0.75;
        content.add(title, constraints);

        JLabel usernameLabel = createLabel("USERNAME:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        content.add(usernameLabel, constraints);

        userIdField = new JTextField();
        styleInput(userIdField);
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        content.add(userIdField, constraints);

        JLabel passwordLabel = createLabel("PASSWORD:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        content.add(passwordLabel, constraints);

        passwordField = new JPasswordField();
        styleInput(passwordField);
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        content.add(passwordField, constraints);

        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setOpaque(false);
        showPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        showPassword.addActionListener(e -> passwordField.setEchoChar(
                showPassword.isSelected() ? (char) 0 : '\u2022'));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        content.add(showPassword, constraints);

        JButton resetButton = new JButton("Reset Password");
        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setForeground(Color.BLUE);
        resetButton.setFont(new Font("Arial", Font.PLAIN, 18));
        resetButton.addActionListener(e -> handleResetPassword());
        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.EAST;
        content.add(resetButton, constraints);

        JButton loginButton = createActionButton("LOGIN");
        loginButton.addActionListener(e -> handleLogin());
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.CENTER;
        content.add(loginButton, constraints);

        JButton registerButton = createActionButton("SIGN UP");
        registerButton.addActionListener(e -> handleRegister());
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        content.add(registerButton, constraints);

        add(content, BorderLayout.CENTER);
        SwingUtilities.invokeLater(() -> userIdField.requestFocusInWindow());
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(new Color(25, 47, 75));
        return label;
    }

    private void styleInput(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 20));
        field.setPreferredSize(new Dimension(0, 50));
        field.setBorder(BorderFactory.createLineBorder(new Color(35, 35, 35)));
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_BLUE);
        button.setForeground(Color.BLACK);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        button.setPreferredSize(new Dimension(250, 64));
        return button;
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

    private void handleResetPassword() {
        JOptionPane.showMessageDialog(this,
                "Please contact the administrator to reset your password.",
                "Reset Password", JOptionPane.INFORMATION_MESSAGE);
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
