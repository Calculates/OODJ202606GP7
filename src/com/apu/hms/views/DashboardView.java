package com.apu.hms.views;

import com.apu.hms.accounts.AccountStore;
import com.apu.hms.controllers.LoginController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardView extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JFrame mainFrame;
    private final String userId;
    private final String role;
    private final LoginController loginController;

    public DashboardView(JFrame mainFrame, String userId, String role) {
        this.mainFrame = mainFrame;
        this.userId = userId;
        this.role = role;
        this.loginController = new LoginController();
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("APU Medical Centre - Main Dashboard", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 102, 204));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(14, 10, 14, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel welcomeLabel = new JLabel("Welcome " + role + " " + userId);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(welcomeLabel, BorderLayout.NORTH);

        DefaultListModel<String> functionModel = new DefaultListModel<>();
        for (String function : getAvailableFunctions()) {
            functionModel.addElement(function);
        }

        JList<String> functionList = new JList<>(functionModel);
        functionList.setFont(new Font("Arial", Font.PLAIN, 14));
        functionList.setFixedCellHeight(34);
        functionList.setBorder(BorderFactory.createEmptyBorder());
        if ("Doctor".equals(role)) {
            functionList.setCellRenderer(new DoctorFunctionRenderer(userId));
        }
        functionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    String selected = functionList.getSelectedValue();
                    if (selected != null) {
                        openSelectedFunction(selected);
                    }
                }
            }
        });
        contentPanel.add(new JScrollPane(functionList), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.BLACK);
        logoutButton.addActionListener(event -> logout());
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);
        footerPanel.add(logoutButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private String[] getAvailableFunctions() {
        switch (role) {
            case "Admin Staff":
                return new String[]{
                    "Manage user accounts",};
            case "Medical Manager":
                return new String[]{
                    "Edit personal profile",
                    "Appointment",
                    "Billing and payment",
                    "Doctors"};
            case "Doctor":
                return new String[]{
                    "Edit personal profile",
                    "Appointment",
                    "Billing and payment",
                    "View patient medical history"};
            case "Patient":
                return new String[]{
                    "Edit personal profile",
                    "Appointments",
                    "View personal medical history",
                    "Billing and payment",};
            default:
                return new String[]{"No functions available"};
        }
    }

    private void logout() {
        mainFrame.setContentPane(new LoginView(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openSelectedFunction(String selectedFunction) {
        if (selectedFunction.equals("Manage user accounts")) {
            manageUserAccounts();
        } else if (selectedFunction.equals("Edit personal profile")) {
            editProfile();
        } else if (selectedFunction.contains("Appointment")) {
            bookAppointment();
        } else if (selectedFunction.equals("View personal medical history")) {
            medicalHistory();
        } else if (selectedFunction.equals("View patient health records")) {
            viewDoctorSchedule();
        } else if (selectedFunction.equals("Billing and payment")) {
            billingAndPayment();
        } else if (selectedFunction.equals("Doctors")) {
            manageDepartments();
        }
    }

    private void editProfile() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        if (JOptionPane.showConfirmDialog(this, form, "Edit Profile",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
        }
    }

    private void bookAppointment() {
        JComboBox<String> doctorSelector = new JComboBox<>();
        for (AccountStore.AccountRecord doctor : AccountStore.getAccountsByRole("Doctor")) {
            doctorSelector.addItem(doctor.getUserId());
        }
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Doctor:"));
        form.add(doctorSelector);
        form.add(new JLabel("Date:"));
        form.add(dateSpinner);
        form.add(new JLabel("Time:"));
        form.add(timeSpinner);

        if (JOptionPane.showConfirmDialog(this, form, "Book Appointment",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
            java.util.Date selectedTime = (java.util.Date) timeSpinner.getValue();
            LocalDate date = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalTime time = selectedTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
            LocalDateTime appointmentTime = LocalDateTime.of(date, time.withSecond(0).withNano(0));
            String doctorId = (String) doctorSelector.getSelectedItem();
            AccountStore.addAppointment(userId, doctorId, appointmentTime);
        }
    }

    private void viewDoctorSchedule() {
        StringBuilder schedule = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (AccountStore.AppointmentRecord appointment : AccountStore.getAppointmentsForDoctor(userId)) {
            schedule.append("Patient: ").append(appointment.getPatientId())
                    .append(" | ").append(appointment.getDateTime().format(formatter)).append('\n');
        }
        if (schedule.length() == 0) {
            schedule.append("No appointments scheduled.");
        }
        JOptionPane.showMessageDialog(this, schedule.toString(),
            "Appointment Schedule", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class DoctorFunctionRenderer extends DefaultListCellRenderer {
        private final String doctorId;

        private DoctorFunctionRenderer(String doctorId) {
            this.doctorId = doctorId;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if ("View appointment schedule".equals(value)
                    && !AccountStore.getAppointmentsForDoctor(doctorId).isEmpty()) {
                label.setText("● " + value);
                label.setForeground(isSelected ? Color.WHITE : Color.RED);
            }
            return label;
        }
    }

    private void manageDepartments() {
        JTextField departmentField = new JTextField();
        JTextField specialtyField = new JTextField();
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.add(new JLabel("Department:"));
        form.add(departmentField);
        form.add(new JLabel("Specialty:"));
        form.add(specialtyField);
        if (JOptionPane.showConfirmDialog(this, form, "Clinical Department",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            // no success popup; continue directly to the department workflow
        }
    }

    private void billingAndPayment() {
        JTextField invoiceField = new JTextField();
        JTextField amountField = new JTextField();
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.add(new JLabel("Invoice Number:"));
        form.add(invoiceField);
        form.add(new JLabel("Amount:"));
        form.add(amountField);
        if (JOptionPane.showConfirmDialog(this, form, "Billing and Payment",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            // no success popup; continue directly to the payment workflow
        }
    }

    private void medicalHistory() {
        JTextArea historyArea = new JTextArea(10, 30);
        historyArea.setEditable(false);
        historyArea.setText("Medical history for patient " + userId + ":\n\n"
                + "No records available.");
        JScrollPane scrollPane = new JScrollPane(historyArea);
        JOptionPane.showMessageDialog(this, scrollPane,
            "Medical History", JOptionPane.INFORMATION_MESSAGE);
    }

    private void manageUserAccounts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Role", "User ID", "Password"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        boolean[] showPasswords = {false};
        Runnable refreshTable = () -> {
            model.setRowCount(0);
            for (AccountStore.AccountRecord account : AccountStore.getAllAccounts()) {
                String passwordValue = showPasswords[0] ? account.getPassword() : "********";
                model.addRow(new Object[]{account.getRole(), account.getUserId(), passwordValue});
            }
        };
        refreshTable.run();

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(28);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);

        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        JButton createButton = new JButton("Create Staff Account");
        JButton showPasswordsButton = new JButton("Show All Passwords");
        JButton closeButton = new JButton("Back");

        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedUserId = (String) model.getValueAt(selectedRow, 1);
                AccountStore.AccountRecord selected = AccountStore.getAccount(selectedUserId);
                boolean isAdmin = selected != null && "Admin Staff".equals(selected.getRole());
                removeButton.setEnabled(!isAdmin);
                removeButton.setToolTipText(isAdmin ? "Admin account cannot be removed" : "Remove selected account");
            } else {
                removeButton.setEnabled(false);
                removeButton.setToolTipText("Select an account first");
            }
        });

        createButton.addActionListener(e -> createStaffAccount());

        showPasswordsButton.addActionListener(e -> {
            showPasswords[0] = !showPasswords[0];
            showPasswordsButton.setText(showPasswords[0] ? "Hide Passwords" : "Show All Passwords");
            refreshTable.run();
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Select an account to edit.",
                        "No Account Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedUserId = (String) model.getValueAt(selectedRow, 1);
            AccountStore.AccountRecord record = AccountStore.getAccount(selectedUserId);
            if (record == null) {
                return;
            }

            JComboBox<String> roleSelector = new JComboBox<>(new String[]{
                    "Admin Staff", "Medical Manager", "Doctor", "Patient"});
            roleSelector.setSelectedItem(record.getRole());

            JTextField userIdField = new JTextField(record.getUserId());
            JPasswordField passwordField = new JPasswordField();
            passwordField.setText("");

            JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
            form.add(new JLabel("Role:"));
            form.add(roleSelector);
            form.add(new JLabel("User ID:"));
            form.add(userIdField);
            form.add(new JLabel("New Password:"));
            form.add(passwordField);

            int result = JOptionPane.showConfirmDialog(this, form,
                    "Edit Account", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            boolean updated = loginController.updateAccount(
                    record.getUserId(),
                    (String) roleSelector.getSelectedItem(),
                    userIdField.getText(),
                    new String(passwordField.getPassword()));

            if (updated) {
                JOptionPane.showMessageDialog(this, "Account updated successfully.",
                        "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                manageUserAccounts();
            } else {
                JOptionPane.showMessageDialog(this, "That user ID is already in use or the data is invalid.",
                        "Update Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Select an account to remove.",
                        "No Account Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedUserId = (String) model.getValueAt(selectedRow, 1);
            AccountStore.AccountRecord selected = AccountStore.getAccount(selectedUserId);
            if (selected != null && "Admin Staff".equals(selected.getRole())) {
                JOptionPane.showMessageDialog(this, "Admin account cannot be removed.",
                        "Removal Restricted", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Remove account '" + selectedUserId + "'?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean removed = loginController.removeAccount(selectedUserId);
            if (removed) {
                JOptionPane.showMessageDialog(this, "Account removed successfully.",
                        "Removal Successful", JOptionPane.INFORMATION_MESSAGE);
                manageUserAccounts();
            } else {
                JOptionPane.showMessageDialog(this, "This account cannot be removed.",
                        "Removal Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeButton.addActionListener(e -> {
            mainFrame.setContentPane(new DashboardView(mainFrame, userId, role));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(createButton);
        buttonPanel.add(showPasswordsButton);
        buttonPanel.add(closeButton);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.setContentPane(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void createStaffAccount() {
        JComboBox<String> roleSelector = new JComboBox<>(new String[]{"Doctor", "Medical Manager"});
        JTextField userIdField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Account Role:"));
        form.add(roleSelector);
        form.add(new JLabel("User ID:"));
        form.add(userIdField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);

        int result = JOptionPane.showConfirmDialog(this, form,
            "Create Staff Account", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        boolean created = loginController.createStaffAccount(
            (String) roleSelector.getSelectedItem(),
            userIdField.getText(),
            new String(passwordField.getPassword()));
        if (!created) {
            JOptionPane.showMessageDialog(this, "User ID is empty or already exists.",
                "Account Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
