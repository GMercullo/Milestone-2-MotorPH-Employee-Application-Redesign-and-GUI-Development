package com.mycompany.hw2;

import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class HW2 extends JFrame {

    private Employee employeeManager;
    private JTextField searchField;
    private JPanel detailsPanel;

    public HW2() {
        setTitle("MotorPH Employee App");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        employeeManager = new Employee();
        employeeManager.loadEmployeesFromExcel("src/MotorPH Employee Data.xlsx");

        // Top panel with title
        JLabel titleLabel = new JLabel("MotorPH Employee App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel searchLabel = new JLabel("Enter Employee ID:");
        searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Employee details panel
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Employee Details:"));

        // Root layout
        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.SOUTH);

        // Search button action
        searchButton.addActionListener((ActionEvent e) -> {
            String input = searchField.getText().trim();
            try {
                int id = Integer.parseInt(input);
                EmployeeData emp = employeeManager.getEmployeeById(id);
                if (emp != null) {
                    displayEmployee(emp);
                } else {
                    showMessage("Employee not found.");
                }
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid numeric ID.");
            }
        });

        setVisible(true);
    }

    private void displayEmployee(EmployeeData emp) {
        detailsPanel.removeAll();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String formattedBirthDate = dateFormat.format(emp.getBirthDate());
        
        DecimalFormat Format = new DecimalFormat("₱#,##0");
        
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

        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HW2());
    }
}
