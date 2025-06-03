package com.mycompany.hw2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class NewEmployeeRecord extends JDialog {

    // Use a relative data folder outside src for persistence
    private static final String EMPLOYEE_CSV_FILE = "src/MotorPH Employee Data - Employee Details.csv";

    private JTextField idField, firstNameField, lastNameField, birthDateField, phoneField, addressField, positionField, statusField, supervisorField;
    private JTextField sssField, philHealthField, tinField, pagIbigField;
    private JTextField salaryField, riceField, phoneAllowanceField, clothingField, hourlyRateField;

    private EmployeeData newEmployee;

    public NewEmployeeRecord(JFrame parent) {
        super(parent, "Add New Employee", true);
        setSize(400, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Personal Info
        form.add(new JLabel("Employee ID:")); idField = new JTextField(); form.add(idField);
        form.add(new JLabel("First Name:")); firstNameField = new JTextField(); form.add(firstNameField);
        form.add(new JLabel("Last Name:")); lastNameField = new JTextField(); form.add(lastNameField);
        form.add(new JLabel("Birthdate (yyyy-MM-dd):")); birthDateField = new JTextField(); birthDateField.setToolTipText("Format: yyyy-MM-dd"); form.add(birthDateField);
        form.add(new JLabel("Phone:")); phoneField = new JTextField(); form.add(phoneField);
        form.add(new JLabel("Address:")); addressField = new JTextField(); form.add(addressField);
        form.add(new JLabel("Position:")); positionField = new JTextField(); form.add(positionField);
        form.add(new JLabel("Status:")); statusField = new JTextField(); form.add(statusField);
        form.add(new JLabel("Supervisor:")); supervisorField = new JTextField(); form.add(supervisorField);

        // Government Details
        form.add(new JLabel("SSS #:")); sssField = new JTextField(); form.add(sssField);
        form.add(new JLabel("PhilHealth #:")); philHealthField = new JTextField(); form.add(philHealthField);
        form.add(new JLabel("TIN #:")); tinField = new JTextField(); form.add(tinField);
        form.add(new JLabel("Pag-IBIG #:")); pagIbigField = new JTextField(); form.add(pagIbigField);

        // Compensation
        form.add(new JLabel("Basic Salary:")); salaryField = new JTextField(); form.add(salaryField);
        form.add(new JLabel("Rice Subsidy:")); riceField = new JTextField(); form.add(riceField);
        form.add(new JLabel("Phone Allowance:")); phoneAllowanceField = new JTextField(); form.add(phoneAllowanceField);
        form.add(new JLabel("Clothing Allowance:")); clothingField = new JTextField(); form.add(clothingField);
        form.add(new JLabel("Hourly Rate:")); hourlyRateField = new JTextField(); form.add(hourlyRateField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(this::onSave);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(form, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onSave(ActionEvent e) {
        try {
            // Validate and parse each input field with helpful error messages
            int id = parseIntField(idField, "Employee ID");

            if (isDuplicateId(id)) {
                JOptionPane.showMessageDialog(this, "Employee ID " + id + " already exists. Please enter a unique ID.");
                return;
            }

            String firstName = getTrimmedText(firstNameField);
            String lastName = getTrimmedText(lastNameField);
            String phone = getTrimmedText(phoneField);
            String address = getTrimmedText(addressField);
            String position = getTrimmedText(positionField);
            String status = getTrimmedText(statusField);
            String supervisor = getTrimmedText(supervisorField);

            Date birthDate = parseDateField(birthDateField, "Birthdate (yyyy-MM-dd)");

            GovernmentDetails gov = new GovernmentDetails(
                getTrimmedText(sssField),
                getTrimmedText(philHealthField),
                getTrimmedText(tinField),
                getTrimmedText(pagIbigField)
            );

            double basic = parseDoubleField(salaryField, "Basic Salary");
            double rice = parseDoubleField(riceField, "Rice Subsidy");
            double phoneAllow = parseDoubleField(phoneAllowanceField, "Phone Allowance");
            double clothing = parseDoubleField(clothingField, "Clothing Allowance");

            double hourlyRate = parseDoubleField(hourlyRateField, "Hourly Rate");
            double grossSemiMonthlyRate = basic / 2.0;

            CompensationDetails comp = new CompensationDetails(
                basic, rice, phoneAllow, clothing, grossSemiMonthlyRate, hourlyRate
            );

            newEmployee = new EmployeeData(
                id, firstName, lastName, birthDate, address, phone,
                status, position, supervisor, comp, gov
            );

            appendEmployeeToCSV(newEmployee);

            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save employee data: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Helpers for parsing and trimming fields with detailed error messages

    private String getTrimmedText(JTextField field) {
        return field.getText() == null ? "" : field.getText().trim();
    }

    private int parseIntField(JTextField field, String fieldName) throws NumberFormatException {
        String text = getTrimmedText(field);
        if (text.isEmpty()) {
            throw new NumberFormatException(fieldName + " cannot be empty.");
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " must be a valid integer.");
        }
    }

    private double parseDoubleField(JTextField field, String fieldName) throws NumberFormatException {
        String text = getTrimmedText(field);
        if (text.isEmpty()) {
            throw new NumberFormatException(fieldName + " cannot be empty.");
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " must be a valid number.");
        }
    }

    private Date parseDateField(JTextField field, String fieldName) throws ParseException {
        String text = getTrimmedText(field);
        if (text.isEmpty()) {
            throw new ParseException(fieldName + " cannot be empty.", 0);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return sdf.parse(text);
    }

    private boolean isDuplicateId(int id) throws IOException {
        File file = new File(EMPLOYEE_CSV_FILE);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip header line if present
                    if (line.trim().startsWith("EmployeeID") || line.trim().startsWith("EmployeeId")) {
                        continue;
                    }
                }
                List<String> fields = parseCSVLine(line);
                if (fields.size() > 0) {
                    try {
                        int existingId = Integer.parseInt(fields.get(0));
                        if (existingId == id) {
                            return true;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return false;
    }

    private void appendEmployeeToCSV(EmployeeData emp) throws IOException {
        File file = new File(EMPLOYEE_CSV_FILE);
        boolean writeHeader = !file.exists() || file.length() == 0;

        try (FileWriter fw = new FileWriter(file, true)) {
            if (writeHeader) {
                String header = "EmployeeID,LastName,FirstName,BirthDate,Address,Phone,Position,Status,Supervisor,SSS,PhilHealth,TIN,PagIbig,BasicSalary,RiceSubsidy,PhoneAllowance,ClothingAllowance";
                fw.write(header + System.lineSeparator());
            }

            String birthDateStr = new SimpleDateFormat("yyyy-MM-dd").format(emp.getBirthDate());

            List<String> fields = new ArrayList<>();
            fields.add(String.valueOf(emp.getEmployeeId()));
            fields.add(emp.getLastName());
            fields.add(emp.getFirstName());
            fields.add(birthDateStr);
            fields.add(emp.getAddress());
            fields.add(emp.getPhoneNumber());
            fields.add(emp.getPosition());
            fields.add(emp.getStatus());
            fields.add(emp.getSupervisor());
            fields.add(emp.getGovernmentDetails().getSssNumber());
            fields.add(emp.getGovernmentDetails().getPhilHealthNumber());
            fields.add(emp.getGovernmentDetails().getTinNumber());
            fields.add(emp.getGovernmentDetails().getPagIbigNumber());
            fields.add(String.valueOf(emp.getCompensation().getBasicSalary()));
            fields.add(String.valueOf(emp.getCompensation().getRiceSubsidy()));
            fields.add(String.valueOf(emp.getCompensation().getPhoneAllowance()));
            fields.add(String.valueOf(emp.getCompensation().getClothingAllowance()));

            fw.write(toCSVLine(fields));
            fw.write(System.lineSeparator());
        }
    }

    private String toCSVLine(List<String> fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            sb.append(escapeCSVField(fields.get(i)));
            if (i < fields.size() - 1) sb.append(",");
        }
        return sb.toString();
    }

    private String escapeCSVField(String field) {
        if (field == null) return "";

        boolean containsSpecial = field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r");
        String escaped = field.replace("\"", "\"\""); // double quotes are escaped by doubling

        if (containsSpecial) {
            return "\"" + escaped + "\"";
        } else {
            return escaped;
        }
    }

    private List<String> parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        if (line == null || line.isEmpty()) return fields;

        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    current.append(c);
                }
            } else {
                if (c == ',') {
                    fields.add(current.toString());
                    current.setLength(0);
                } else if (c == '"') {
                    inQuotes = true;
                } else {
                    current.append(c);
                }
            }
        }
        fields.add(current.toString());

        return fields;
    }

    public EmployeeData getNewEmployee() {
        return newEmployee;
    }
}