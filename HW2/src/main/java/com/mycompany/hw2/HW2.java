package com.mycompany.hw2;

// Author: gmmercullo

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class HW2 extends JFrame {


    // Layout manager to switch between panels
    private CardLayout cardLayout;

    // Main panel containing all other panels
    private JPanel mainPanel;

    // Handles loading, storing, and managing employees
    private Employee employeeManager;

    // Format for displaying currency values
    private final DecimalFormat money = new DecimalFormat("₱#,##0.00");

    // Format for displaying hours
    private final DecimalFormat hoursFormat = new DecimalFormat("#0.##");

    // Accepts and show user role when logging in
    private String role;

    public HW2(String userRole) {
        this.role = userRole;
        setTitle("MotorPH Payroll System - Logged in as " + role);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Load employee data from CSV
        employeeManager = new Employee();
        employeeManager.loadEmployeesFromCSV("src/MotorPH Employee Data - Employee Details.csv");

        // Set up main card layout panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add home and all employee views
        mainPanel.add(createHomePanel(), "home");
        mainPanel.add(createAllEmployeePanel(), "allEmployees");

        add(mainPanel);
        cardLayout.show(mainPanel, "home");

        setVisible(true);
    }

    // Creates the home panel with buttons to navigate
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("MotorPH Employee App", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Button to view all employees
        JButton viewAllButton = new JButton("View All Employee Records");
        viewAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAllButton.setMaximumSize(new Dimension(200, 40));
        viewAllButton.addActionListener(e -> cardLayout.show(mainPanel, "allEmployees"));

        center.add(Box.createRigidArea(new Dimension(0, 20)));
        center.add(viewAllButton);
        center.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(title, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // Creates the panel displaying all employee records
    private JPanel createAllEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Top navigation and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        topPanel.add(backButton, BorderLayout.WEST);

        // Add New Employee button (right)
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
                refreshAllEmployeePanel(); // Refresh after addition
            }
        });
        topPanel.add(newEmployeeButton, BorderLayout.EAST);

        JLabel title = new JLabel("All MotorPH Employee Records", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);

        // Container for table
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.add(createTableHeader());

        // Add employee rows
        List<EmployeeData> employees = employeeManager.getAllEmployees();
        for (EmployeeData emp : employees) {
            tablePanel.add(createEmployeeRow(emp));
        }

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Creates table headers
    private JPanel createTableHeader() {
        JPanel header = new JPanel(new GridLayout(1, 8));
        header.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

        String[] labels = {
            "Employee #", "Last Name", "First Name", "SSS #",
            "PhilHealth #", "TIN #", "Pag-IBIG #", ""
        };

        for (String label : labels) {
            JLabel lbl = new JLabel(label, SwingConstants.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            header.add(lbl);
        }

        return header;
    }

    // Creates a row in the employee table
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

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(e -> showEmployeeDetails(emp));

        // Button to update employee
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

        // Button to delete employee
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

        row.add(buttonPanel); // Add the panel containing all buttons
        return row;
    }

    // Refreshes employee panel after data changes
    private void refreshAllEmployeePanel() {
        mainPanel.remove(mainPanel.getComponent(1)); // Remove current panel
        mainPanel.add(createAllEmployeePanel(), "allEmployees"); // Recreate panel
        cardLayout.show(mainPanel, "allEmployees");
    }

    // Displays detailed info and options for an employee
    private void showEmployeeDetails(EmployeeData emp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        CompensationDetails comp = emp.getCompensation();

        // Build HTML-styled string for formatting
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

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel(details), BorderLayout.CENTER);

        // Button to calculate salary
        JButton calcButton = new JButton("Calculate Monthly Salary");
        calcButton.addActionListener(e -> showMonthInputDialog(emp));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calcButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Show employee detail dialog
        JOptionPane.showMessageDialog(this, panel, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // Prompts for month and triggers payroll calculation
    private void showMonthInputDialog(EmployeeData emp) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Month dropdown with full names
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        JComboBox<String> monthCombo = new JComboBox<>(monthNames);

        // Year dropdown
        int currentYear = YearMonth.now().getYear();
        JComboBox<Integer> yearCombo = new JComboBox<>();
        for (int year = 2000; year <= currentYear + 20; year++) {
            yearCombo.addItem(year);
        }

        panel.add(new JLabel("Select Month:"));
        panel.add(monthCombo);
        panel.add(new JLabel("Select Year:"));
        panel.add(yearCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Select Month and Year", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String monthName = (String) monthCombo.getSelectedItem();
            int monthIndex = monthCombo.getSelectedIndex() + 1; // convert to 1-based month
            int selectedYear = (int) yearCombo.getSelectedItem();

            // Format as "MM-yyyy" for internal processing
            String monthFormatted = String.format("%02d-%d", monthIndex, selectedYear);

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
                YearMonth yearMonth = YearMonth.parse(monthFormatted, formatter);

                // Use for display: "June 2025"
                String displayMonthYear = monthName + " " + selectedYear;

                if (monthExistsInAttendance(emp.getEmployeeId(), monthFormatted)) {
                    PayrollService(emp, monthFormatted); // Still use MM-yyyy format internally
                } else {
                    JOptionPane.showMessageDialog(this, "No attendance records found for " + displayMonthYear + ".");
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error parsing the selected date.");
            }
        }
    }

    // Checks if attendance data exists for the given month
    private boolean monthExistsInAttendance(int employeeId, String targetMonth) {
        Attendance attendance = new Attendance();
        return attendance.hasAttendanceForMonth(employeeId, targetMonth);
    }

    // Performs full payroll calculation and displays the report
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

            // Populate the report panel
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

    // Displays a payroll report passed from other sources
    public static void displayPayrollReport(PayrollReport reportPanel) {
        JScrollPane scrollPane = new JScrollPane(reportPanel);
        JOptionPane.showMessageDialog(null, scrollPane, "Payroll Report", JOptionPane.INFORMATION_MESSAGE);
    }

}