package com.mycompany.hw2;

// Author: gmmercullo

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HW2 extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Employee employeeManager;
    private final DecimalFormat money = new DecimalFormat("\u20b1#,##0.00");
    private final DecimalFormat hoursFormat = new DecimalFormat("#0.##");
    private String role;

    public HW2(String userRole) {
        this.role = userRole;
        setTitle("MotorPH Payroll System - Logged in as " + role);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        employeeManager = new Employee();
        employeeManager.loadEmployeesFromCSV("src/MotorPH Employee Data - Employee Details.csv");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createHomePanel(), "home");
        mainPanel.add(createAllEmployeePanel(), "allEmployees");

        add(mainPanel);
        cardLayout.show(mainPanel, "home");
        setVisible(true);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("MotorPH Employee App", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JButton viewAllButton = new JButton("View All Employee Records");
        viewAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAllButton.setMaximumSize(new Dimension(200, 40));
        viewAllButton.addActionListener(e -> cardLayout.show(mainPanel, "allEmployees"));

        JButton searchByIdButton = new JButton("Search Employee");
        searchByIdButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchByIdButton.setMaximumSize(new Dimension(200, 40));
        searchByIdButton.addActionListener(e -> showSearchEmployeeDialog());

        JButton searchPayCoverageButton = new JButton("Pay Coverage");
        searchPayCoverageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPayCoverageButton.setMaximumSize(new Dimension(200, 40));
        searchPayCoverageButton.addActionListener(e -> showSearchPayCoverageDialog());

        center.add(Box.createRigidArea(new Dimension(0, 10)));
        center.add(searchByIdButton);
        center.add(Box.createRigidArea(new Dimension(0, 10)));
        center.add(searchPayCoverageButton);

        center.add(Box.createRigidArea(new Dimension(0, 20)));
        center.add(viewAllButton);
        center.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(title, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAllEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        topPanel.add(backButton, BorderLayout.WEST);

        JButton newEmployeeButton = new JButton("Add New Employee");
        newEmployeeButton.addActionListener(e -> {
            NewEmployeeRecord dialog = new NewEmployeeRecord(this);
            dialog.setVisible(true);
            EmployeeData newEmp = dialog.getNewEmployee();
            if (newEmp != null) {
                employeeManager.addEmployee(newEmp);
                try {
                    CSVHandler.appendEmployeeToCSV("src/MotorPH Employee Data - Employee Details.csv", newEmp);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving employee: " + ex.getMessage());
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Employee added!");
                refreshAllEmployeePanel();
            }
        });
        topPanel.add(newEmployeeButton, BorderLayout.EAST);

        JLabel title = new JLabel("All MotorPH Employee Records", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.add(createTableHeader());

        List<EmployeeData> employees = employeeManager.getAllEmployees();
        for (EmployeeData emp : employees) {
            tablePanel.add(createEmployeeRow(emp));
        }

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTableHeader() {
        JPanel header = new JPanel(new GridLayout(1, 8));
        header.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        String[] labels = { "Employee #", "Last Name", "First Name", "SSS #", "PhilHealth #", "TIN #", "Pag-IBIG #", "" };
        for (String label : labels) {
            JLabel lbl = new JLabel(label, SwingConstants.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            header.add(lbl);
        }
        return header;
    }

    private JPanel createEmployeeRow(EmployeeData emp) {
        JPanel row = new JPanel(new GridLayout(1, 8));
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        GovernmentDetails gov = emp.getGovernmentDetails();

        row.add(new JLabel(String.valueOf(emp.getEmployeeId())));
        row.add(new JLabel(emp.getLastName()));
        row.add(new JLabel(emp.getFirstName()));
        row.add(new JLabel(gov.getSssNumber()));
        row.add(new JLabel(gov.getPhilHealthNumber()));
        row.add(new JLabel(gov.getTinNumber()));
        row.add(new JLabel(gov.getPagIbigNumber()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(e -> showEmployeeDetails(emp));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            NewEmployeeRecord dialog = new NewEmployeeRecord(this, emp);
            dialog.setVisible(true);
            EmployeeData updated = dialog.getNewEmployee();
            if (updated != null) {
                employeeManager.updateEmployee(updated.getEmployeeId(), updated);
                JOptionPane.showMessageDialog(this, "Employee updated!");
                refreshAllEmployeePanel();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                employeeManager.deleteEmployee(emp.getEmployeeId());
                JOptionPane.showMessageDialog(this, "Employee deleted!");
                refreshAllEmployeePanel();
            }
        });
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        row.add(buttonPanel);
        return row;
    }

    private void refreshAllEmployeePanel() {
        mainPanel.remove(mainPanel.getComponent(1));
        mainPanel.add(createAllEmployeePanel(), "allEmployees");
        cardLayout.show(mainPanel, "allEmployees");
    }

    private void showEmployeeDetails(EmployeeData emp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        CompensationDetails comp = emp.getCompensation();

        String details = String.format(
                "<html><body style='width: 400px;'>"
                        + "<b>ID:</b> %d<br>"
                        + "<b>Name:</b> %s<br>"
                        + "<b>Birthdate:</b> %s<br>"
                        + "<b>Address:</b> %s<br>"
                        + "<b>Phone:</b> %s<br>"
                        + "<b>Position:</b> %s<br>"
                        + "<b>Status:</b> %s<br>"
                        + "<b>Supervisor:</b> %s<br><br>"
                        + "<b>SSS:</b> %s<br>"
                        + "<b>PhilHealth:</b> %s<br>"
                        + "<b>Pag-IBIG:</b> %s<br>"
                        + "<b>TIN:</b> %s<br><br>"
                        + "<b>Basic Salary:</b> %s<br>"
                        + "<b>Rice Subsidy:</b> %s<br>"
                        + "<b>Phone Allowance:</b> %s<br>"
                        + "<b>Clothing Allowance:</b> %s<br>"
                        + "<b>Gross Semi-Monthly:</b> %s<br>"
                        + "<b>Hourly Rate:</b> %s<br>"
                        + "</body></html>",
                emp.getEmployeeId(), emp.getFullName(), dateFormat.format(emp.getBirthDate()), emp.getAddress(), emp.getPhoneNumber(),
                emp.getPosition(), emp.getStatus(), emp.getSupervisor(),
                emp.getGovernmentDetails().getSssNumber(), emp.getGovernmentDetails().getPhilHealthNumber(),
                emp.getGovernmentDetails().getPagIbigNumber(), emp.getGovernmentDetails().getTinNumber(),
                money.format(comp.getBasicSalary()), money.format(comp.getRiceSubsidy()),
                money.format(comp.getPhoneAllowance()), money.format(comp.getClothingAllowance()),
                money.format(comp.getGrossSemiMonthlyRate()), money.format(comp.getHourlyRate())
        );

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel(details), BorderLayout.CENTER);

        JButton calcButton = new JButton("Calculate Monthly Salary");
        calcButton.addActionListener(e -> showMonthInputDialog(emp));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calcButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(this, panel, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMonthInputDialog(EmployeeData emp) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] monthNames = new java.text.DateFormatSymbols().getMonths();
        JComboBox<String> monthCombo = new JComboBox<>(monthNames);
        JComboBox<Integer> yearCombo = new JComboBox<>();
        int currentYear = YearMonth.now().getYear();
        for (int y = 2024; y <= currentYear + 10; y++) {
            yearCombo.addItem(y);
        }
        panel.add(new JLabel("Select Month:"));
        panel.add(monthCombo);
        panel.add(new JLabel("Select Year:"));
        panel.add(yearCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Select Month and Year", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int monthIndex = monthCombo.getSelectedIndex() + 1;
            int year = (int) yearCombo.getSelectedItem();
            String monthFormatted = String.format("%02d-%d", monthIndex, year);
            if (monthExistsInAttendance(emp.getEmployeeId(), monthFormatted)) {
                PayrollService(emp, monthFormatted);
            } else {
                JOptionPane.showMessageDialog(this, "No attendance records found for " + monthFormatted);
            }
        }
    }

    private boolean monthExistsInAttendance(int employeeId, String month) {
        return new Attendance().hasAttendanceForMonth(employeeId, month);
    }

    private void PayrollService(EmployeeData empData, String monthInput) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            YearMonth monthYear = YearMonth.parse(monthInput, inputFormatter);
            LocalDate start = monthYear.atDay(1);
            LocalDate end = monthYear.atEndOfMonth();

            Attendance attendance = new Attendance();
            double regularHours = attendance.getTotalRegularHours(empData.getEmployeeId(), start, end);
            double overtimeHours = attendance.getTotalOvertimeHours(empData.getEmployeeId(), start, end);

            double hourlyRate = GrossWage.calculateHourlyRate(empData.getCompensation().getBasicSalary());
            double gross = GrossWage.calculateGross(hourlyRate, regularHours, overtimeHours);

            double sss = Deductions.calculateSSS(gross);
            double philHealth = Deductions.calculatePhilHealth(gross);
            double pagIbig = Deductions.calculatePagIbig(gross);
            double tax = Deductions.calculateWithholdingTax(gross);

            double rice = empData.getCompensation().getRiceSubsidy();
            double phone = empData.getCompensation().getPhoneAllowance();
            double clothing = empData.getCompensation().getClothingAllowance();
            double net = gross - (sss + philHealth + pagIbig + tax) + rice + phone + clothing;

            // Populate the full report panel
            PayrollReport summary = new PayrollReport();
            summary.setValue("Employee #:", String.valueOf(empData.getEmployeeId()));
            summary.setValue("Last Name:", empData.getLastName());
            summary.setValue("First Name:", empData.getFirstName());
            summary.setValue("Birth Date:", new SimpleDateFormat("MMM dd, yyyy").format(empData.getBirthDate()));
            summary.setValue("Address:", empData.getAddress());
            summary.setValue("Phone #:", empData.getPhoneNumber());
            summary.setValue("Position:", empData.getPosition());
            summary.setValue("Status:", empData.getStatus());
            summary.setValue("Supervisor:", empData.getSupervisor());

            summary.setValue("SSS #:", empData.getGovernmentDetails().getSssNumber());
            summary.setValue("PhilHealth #:", empData.getGovernmentDetails().getPhilHealthNumber());
            summary.setValue("TIN #:", empData.getGovernmentDetails().getTinNumber());
            summary.setValue("Pag-IBIG #:", empData.getGovernmentDetails().getPagIbigNumber());

            summary.setValue("Basic Salary:", money.format(empData.getCompensation().getBasicSalary()));
            summary.setValue("Rice Subsidy:", money.format(rice));
            summary.setValue("Phone Subsidy:", money.format(phone));
            summary.setValue("Clothing Allowance:", money.format(clothing));
            summary.setValue("Gross Semi-Monthly Salary:", money.format(empData.getCompensation().getGrossSemiMonthlyRate()));
            summary.setValue("Hourly Rate:", money.format(hourlyRate));
            summary.setValue("Month:", monthYear.format(outputFormatter));

            summary.setValue("Regular Hours:", hoursFormat.format(regularHours));
            summary.setValue("Overtime Hours:", hoursFormat.format(overtimeHours));
            summary.setValue("Gross Salary:", money.format(gross));

            summary.setValue("SSS Deduction:", money.format(sss));
            summary.setValue("PhilHealth Deduction:", money.format(philHealth));
            summary.setValue("Pag-IBIG Deduction:", money.format(pagIbig));
            summary.setValue("Withholding Tax:", money.format(tax));

            summary.setValue("Net Salary:", money.format(net));

            JScrollPane scrollPane = new JScrollPane(summary);
            scrollPane.setPreferredSize(new Dimension(600, 700));
            JOptionPane.showMessageDialog(this, scrollPane, "Payroll Summary", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error in payroll calculation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void displayPayrollReport(PayrollReport reportPanel) {
        JScrollPane scrollPane = new JScrollPane(reportPanel);
        JOptionPane.showMessageDialog(null, scrollPane, "Payroll Report", JOptionPane.INFORMATION_MESSAGE);
    }


    private void showEmployeeInfoReadOnly(EmployeeData emp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        CompensationDetails comp = emp.getCompensation();

        String details = String.format(
                "<html><body style='width: 400px;'>"
                        + "<b>ID:</b> %d<br>"
                        + "<b>Name:</b> %s<br>"
                        + "<b>Birthdate:</b> %s<br>"
                        + "<b>Address:</b> %s<br>"
                        + "<b>Phone:</b> %s<br>"
                        + "<b>Position:</b> %s<br>"
                        + "<b>Status:</b> %s<br>"
                        + "<b>Supervisor:</b> %s<br><br>"
                        + "<b>SSS:</b> %s<br>"
                        + "<b>PhilHealth:</b> %s<br>"
                        + "<b>Pag-IBIG:</b> %s<br>"
                        + "<b>TIN:</b> %s<br><br>"
                        + "<b>Basic Salary:</b> %s<br>"
                        + "<b>Rice Subsidy:</b> %s<br>"
                        + "<b>Phone Allowance:</b> %s<br>"
                        + "<b>Clothing Allowance:</b> %s<br>"
                        + "<b>Gross Semi-Monthly:</b> %s<br>"
                        + "<b>Hourly Rate:</b> %s<br>"
                        + "</body></html>",
                emp.getEmployeeId(),
                emp.getFullName(),
                dateFormat.format(emp.getBirthDate()),
                emp.getAddress(),
                emp.getPhoneNumber(),
                emp.getPosition(),
                emp.getStatus(),
                emp.getSupervisor(),
                emp.getGovernmentDetails().getSssNumber(),
                emp.getGovernmentDetails().getPhilHealthNumber(),
                emp.getGovernmentDetails().getPagIbigNumber(),
                emp.getGovernmentDetails().getTinNumber(),
                money.format(comp.getBasicSalary()),
                money.format(comp.getRiceSubsidy()),
                money.format(comp.getPhoneAllowance()),
                money.format(comp.getClothingAllowance()),
                money.format(comp.getGrossSemiMonthlyRate()),
                money.format(comp.getHourlyRate())
        );

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(details), BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Employee Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSearchEmployeeDialog() {
        String input = JOptionPane.showInputDialog(this, "Enter Employee ID:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int empId = Integer.parseInt(input.trim());
                EmployeeData emp = employeeManager.findById(empId);
                if (emp != null) {
                    showEmployeeInfoReadOnly(emp); // Show in read-only mode
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Employee ID. Please enter a valid number.");
            }
        }
    }

    private void showSearchPayCoverageDialog() {
        // First prompt for Employee ID only
        String inputId = JOptionPane.showInputDialog(this, "Enter Employee ID:");
        if (inputId == null || inputId.trim().isEmpty()) return;

        try {
            int empId = Integer.parseInt(inputId.trim());
            EmployeeData emp = employeeManager.findById(empId);

            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Employee not found.");
                return;
            }

            // Show month and year dropdowns just like in showMonthInputDialog
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            String[] monthNames = new java.text.DateFormatSymbols().getMonths();
            JComboBox<String> monthCombo = new JComboBox<>(monthNames);
            JComboBox<Integer> yearCombo = new JComboBox<>();
            int currentYear = YearMonth.now().getYear();
            for (int y = 2024; y <= currentYear + 10; y++) {
                yearCombo.addItem(y);
            }

            panel.add(new JLabel("Select Month:"));
            panel.add(monthCombo);
            panel.add(new JLabel("Select Year:"));
            panel.add(yearCombo);

            int result = JOptionPane.showConfirmDialog(this, panel, "Select Month and Year", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int monthIndex = monthCombo.getSelectedIndex() + 1;
                int year = (int) yearCombo.getSelectedItem();
                String monthFormatted = String.format("%02d-%d", monthIndex, year);

                if (monthExistsInAttendance(emp.getEmployeeId(), monthFormatted)) {
                    PayrollService(emp, monthFormatted);
                } else {
                    JOptionPane.showMessageDialog(this, "No attendance records found for " + monthFormatted);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID. Please enter a valid number.");
        }
    }


}