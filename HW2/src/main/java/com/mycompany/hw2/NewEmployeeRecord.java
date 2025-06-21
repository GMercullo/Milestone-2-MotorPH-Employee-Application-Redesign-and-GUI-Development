package com.mycompany.hw2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;
import java.util.List;

import com.opencsv.CSVReader;

public class NewEmployeeRecord extends JDialog {
    private String filePath = "src/MotorPH Employee Data - Employee Details.csv";
    private JTextField firstNameField, lastNameField, phoneField, addressField, positionField, statusField, supervisorField;
    private JTextField sssField, philHealthField, tinField, pagIbigField;
    private JTextField salaryField, riceField, phoneAllowanceField, clothingField, hourlyRateField;
    private JComboBox<String> birthMonthDropdown;
    private JComboBox<Integer> birthDayDropdown;
    private JComboBox<Integer> birthYearDropdown;

    private EmployeeData employeeToUpdate;
    private boolean updateMode = false;
    private EmployeeData newEmployee;

    public NewEmployeeRecord(JFrame parent) {
        super(parent, "Add New Employee", true);
        initUI(parent);
    }

    public NewEmployeeRecord(JFrame parent, EmployeeData existingEmp) {
        super(parent, "Edit Employee", true);
        this.employeeToUpdate = existingEmp;
        this.updateMode = true;
        initUI(parent);
        populateForm(existingEmp);
    }

    private void initUI(JFrame parent) {
        setSize(400, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton(updateMode ? "Update" : "Save");
        saveButton.addActionListener(updateMode ? this::onUpdate : this::onSave);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        firstNameField = new JTextField(); lastNameField = new JTextField();
        phoneField = new JTextField(); addressField = new JTextField();
        positionField = new JTextField(); statusField = new JTextField(); supervisorField = new JTextField();

        sssField = new JTextField(); philHealthField = new JTextField(); tinField = new JTextField(); pagIbigField = new JTextField();
        salaryField = new JTextField(); riceField = new JTextField(); phoneAllowanceField = new JTextField(); clothingField = new JTextField();
        hourlyRateField = new JTextField(); hourlyRateField.setEditable(false);

        // Month names from DateFormatSymbols
        String[] months = new java.text.DateFormatSymbols().getMonths();
        birthMonthDropdown = new JComboBox<>(Arrays.copyOf(months, 12)); // Only Jan–Dec

        // Days: 1 to 31
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        birthDayDropdown = new JComboBox<>(days);

        // Years: 1925 to current year
        birthYearDropdown = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = 1925; y <= currentYear; y++) {
            birthYearDropdown.addItem(y);
        }

        addField(form, "First Name:", firstNameField);
        addField(form, "Last Name:", lastNameField);
        addField(form, "Birth Month:", birthMonthDropdown);
        addField(form, "Birth Day:", birthDayDropdown);
        addField(form, "Birth Year:", birthYearDropdown);
        addField(form, "Phone:", phoneField);
        addField(form, "Address:", addressField);
        addField(form, "Position:", positionField);
        addField(form, "Status:", statusField);
        addField(form, "Supervisor:", supervisorField);
        addField(form, "SSS #:", sssField);
        addField(form, "PhilHealth #:", philHealthField);
        addField(form, "TIN #:", tinField);
        addField(form, "Pag-IBIG #:", pagIbigField);
        addField(form, "Basic Salary:", salaryField);
        addField(form, "Rice Subsidy:", riceField);
        addField(form, "Phone Allowance:", phoneAllowanceField);
        addField(form, "Clothing Allowance:", clothingField);
        addField(form, "Hourly Rate:", hourlyRateField);

        salaryField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateHourlyRate() {
                try {
                    double salary = Double.parseDouble(salaryField.getText());
                    double hourly = salary / (22 * 8);
                    hourlyRateField.setText(String.format("%.2f", hourly));
                } catch (NumberFormatException e) {
                    hourlyRateField.setText("");
                }
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateHourlyRate(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateHourlyRate(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateHourlyRate(); }
        });

        return form;
    }

    private void addField(JPanel panel, String label, JComponent field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }

    private void populateForm(EmployeeData emp) {
        firstNameField.setText(emp.getFirstName());
        lastNameField.setText(emp.getLastName());
        Calendar cal = Calendar.getInstance();
        cal.setTime(emp.getBirthDate());
        birthMonthDropdown.setSelectedIndex(cal.get(Calendar.MONTH));
        birthDayDropdown.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
        birthYearDropdown.setSelectedItem(cal.get(Calendar.YEAR));

        phoneField.setText(emp.getPhoneNumber());
        addressField.setText(emp.getAddress());
        positionField.setText(emp.getPosition());
        statusField.setText(emp.getStatus());
        supervisorField.setText(emp.getSupervisor());

        GovernmentDetails gov = emp.getGovernmentDetails();
        sssField.setText(gov.getSssNumber());
        philHealthField.setText(gov.getPhilHealthNumber());
        tinField.setText(gov.getTinNumber());
        pagIbigField.setText(gov.getPagIbigNumber());

        CompensationDetails comp = emp.getCompensation();
        salaryField.setText(String.valueOf(comp.getBasicSalary()));
        riceField.setText(String.valueOf(comp.getRiceSubsidy()));
        phoneAllowanceField.setText(String.valueOf(comp.getPhoneAllowance()));
        clothingField.setText(String.valueOf(comp.getClothingAllowance()));
        hourlyRateField.setText(String.format("%.2f", comp.getHourlyRate()));
    }

    private void onSave(ActionEvent e) {
        try {
            List<EmployeeData> allEmployees = CSVHandler.loadAllEmployees(filePath);
            int newId = allEmployees.stream().mapToInt(EmployeeData::getEmployeeId).max().orElse(0) + 1;

            newEmployee = createEmployeeFromForm(newId);  // Save to field


            CSVHandler.appendEmployeeToCSV(filePath, newEmployee);

            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            dispose();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void onUpdate(ActionEvent e) {
        try {
            List<EmployeeData> allEmployees = CSVHandler.loadAllEmployees(filePath);
            newEmployee = createEmployeeFromForm(employeeToUpdate.getEmployeeId());  // Save to field

            boolean updated = false;
            for (int i = 0; i < allEmployees.size(); i++) {
                if (allEmployees.get(i).getEmployeeId() == newEmployee.getEmployeeId()) {
                    allEmployees.set(i, newEmployee);
                    updated = true;
                    break;
                }
            }

            if (updated) {
                CSVHandler.saveEmployeeToCSV(filePath, allEmployees);
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Employee ID not found.");
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    public EmployeeData getNewEmployee() {
        return newEmployee;
    }

    private EmployeeData createEmployeeFromForm(int empId) throws ParseException {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String position = positionField.getText().trim();
        String status = statusField.getText().trim();
        String supervisor = supervisorField.getText().trim();

        int day = (int) birthDayDropdown.getSelectedItem();
        int month = birthMonthDropdown.getSelectedIndex(); // 0-based
        int year = (int) birthYearDropdown.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date birthDate = cal.getTime();

        GovernmentDetails gov = new GovernmentDetails(
                sssField.getText().trim(),
                philHealthField.getText().trim(),
                tinField.getText().trim(),
                pagIbigField.getText().trim()
        );

        double basic = Double.parseDouble(salaryField.getText().trim());
        double rice = Double.parseDouble(riceField.getText().trim());
        double phoneAllowance = Double.parseDouble(phoneAllowanceField.getText().trim());
        double clothing = Double.parseDouble(clothingField.getText().trim());
        double hourlyRate = Double.parseDouble(hourlyRateField.getText().trim());
        double grossSemiMonthly = basic / 2.0;

        CompensationDetails comp = new CompensationDetails(basic, rice, phoneAllowance, clothing, grossSemiMonthly, hourlyRate);

        return new EmployeeData(empId, firstName, lastName, birthDate, address, phone, status, position, supervisor, comp, gov);
    }

    private List<EmployeeData> loadAllEmployees() throws IOException {
        List<EmployeeData> list = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("src/MotorPH Employee Data - Employee Details.csv"))) {
            String[] line;
            boolean isHeader = true;

            while ((line = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                list.add(CSVHandler.parseEmployeeRow(line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}