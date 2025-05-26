package com.mycompany.hw2;

import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

/**
 * Main application window for the MotorPH Employee App.
 * This class builds a simple GUI using Java Swing to search and display employee information.
 */
public class HW2 extends JFrame {

    private Employee employeeManager;   // Handles employee data loading and searching
    private JTextField searchField;     // Field to input employee ID
    private JPanel detailsPanel;        // Panel to show employee details

    public HW2() {
        // Set up the main window (JFrame)
        setTitle("MotorPH Employee App");
        setSize(600, 500);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the Employee manager and load data from Excel file
        employeeManager = new Employee();
        employeeManager.loadEmployeesFromExcel("src/MotorPH Employee Data.xlsx");

        // Title label at the top
        JLabel titleLabel = new JLabel("MotorPH Employee App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Search panel (contains input field and button)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel searchLabel = new JLabel("Enter Employee ID:");
        searchField = new JTextField(10);               // Text field for input
        JButton searchButton = new JButton("Search");   // Button to trigger search

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Panel that will show employee details when found
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1, 5, 5)); // Vertical list layout
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Employee Details:"));

        // Add panels to the main frame using BorderLayout
        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.SOUTH);

        // Event listener for the search button
        searchButton.addActionListener((ActionEvent e) -> {
            String input = searchField.getText().trim();
            try {
                int id = Integer.parseInt(input); // Parse ID
                EmployeeData emp = employeeManager.getEmployeeById(id); // Look up employee
                if (emp != null) {
                    displayEmployee(emp); // Show employee details
                } else {
                    showMessage("Employee not found.");
                }
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid numeric ID."); // If input is not a number
            }
        });

        setVisible(true); // Show the GUI
    }

    /**
     * Displays the employee information on the details panel.
     */
    private void displayEmployee(EmployeeData emp) {
        detailsPanel.removeAll(); // Clear previous content

        // Format birth date to readable form
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String formattedBirthDate = dateFormat.format(emp.getBirthDate());

        // Format salary and allowance values with currency
        DecimalFormat Format = new DecimalFormat("₱#,##0");

        // Add each detail as a label in the panel
        detailsPanel.add(new JLabel("ID: " + emp.getEmployeeId()));
        detailsPanel.add(new JLabel("Name: " + emp.getFullName()));
        detailsPanel.add(new JLabel("Birthdate: " + formattedBirthDate));
        detailsPanel.add(new JLabel("Address: " + emp.getAddress()));
        detailsPanel.add(new JLabel("Phone: " + emp.getPhoneNumber()));
        detailsPanel.add(new JLabel("Position: " + emp.getPosition()));
        detailsPanel.add(new JLabel("Status: " + emp.getStatus()));
        detailsPanel.add(new JLabel("Supervisor: " + emp.getDepartmentSupervisor()));
        detailsPanel.add(new JLabel("SSS: " + emp.getSSSNumber()));
        detailsPanel.add(new JLabel("PhilHealth: " + emp.getPhilHealthNumber()));
        detailsPanel.add(new JLabel("Pag-IBIG: " + emp.getPagIbigNumber()));
        detailsPanel.add(new JLabel("TIN: " + emp.getTinNumber()));
        detailsPanel.add(new JLabel("Basic Salary: " + Format.format(emp.getBasicSalary())));
        detailsPanel.add(new JLabel("Rice Subsidy: " + Format.format(emp.getRiceSubsidy())));
        detailsPanel.add(new JLabel("Phone Allowance: " + Format.format(emp.getPhoneAllowance())));
        detailsPanel.add(new JLabel("Clothing Allowance: " + Format.format(emp.getClothingAllowance())));
        detailsPanel.add(new JLabel("Gross Semi-Monthly: " + Format.format(emp.getGrossSemiMonthlyRate())));
        detailsPanel.add(new JLabel("Hourly Rate: " + Format.format(emp.getHourlyRate())));

        // Refresh the panel to show new data
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    /**
     * Shows a message dialog to the user.
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Entry point of the program.
     * Uses SwingUtilities to safely launch the GUI on the Event Dispatch Thread.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HW2());
    }
}
