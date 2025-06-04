package com.mycompany.hw2;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A Swing panel that displays a detailed payroll report for an employee,
 * including personal info, salary breakdown, hours worked, and deductions.
 *
 * Used in conjunction with PayrollService() in HW2.
 *
 * Author: gmmercullo
 */
public class PayrollReport extends JPanel {
    private final Map<String, JLabel> labelMap = new HashMap<>();

    public PayrollReport() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        String[] labels = {
            "Employee #:", "Last Name:", "First Name:", "Birth Date:", "Address:",
            "Phone #:", "Position:", "Status:", "Supervisor:",
            "SSS #:", "PhilHealth #:", "TIN #:", "Pag-IBIG #:",
            "Basic Salary:", "Rice Subsidy:", "Phone Subsidy:", "Clothing Allowance:",
            "Gross Semi-Monthly Salary:", "Hourly Rate:", "Month:",
            "Regular Hours:", "Overtime Hours:", "Gross Salary:",
            "SSS Deduction:", "PhilHealth Deduction:", "Pag-IBIG Deduction:", "Withholding Tax:",
            "Net Salary:"
        };

        int row = 0;
        for (String label : labels) {
            gbc.gridx = 0;
            gbc.gridy = row;
            JLabel nameLabel = new JLabel(label);
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            add(nameLabel, gbc);

            gbc.gridx = 1;
            JLabel valueLabel = new JLabel(" ");
            valueLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            labelMap.put(label, valueLabel);
            add(valueLabel, gbc);

            row++;
        }
    }

    /**
     * Sets the value of a field by label.
     * @param label The label used to identify the field.
     * @param value The value to display next to the label.
     */
    public void setValue(String label, String value) {
        JLabel field = labelMap.get(label);
        if (field != null) {
            field.setText(value);
        }
    }
}
