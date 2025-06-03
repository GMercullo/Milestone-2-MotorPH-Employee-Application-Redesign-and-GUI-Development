package com.mycompany.hw2;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Handles CSV import and export functionality for Employee and Payroll data.
 */
public final class CSVHandler {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Exports payroll data to a CSV file.
     * @param payrollList the list of payroll records
     * @param filePath the destination CSV file path
     */
    public void exportPayrollToCSV(List<Payroll> payrollList, String filePath) {
        // TODO: Write payrollList to CSV using OpenCSV or manual FileWriter
    }

    /**
     * Imports employee data from a CSV file.
     * @param filePath the source CSV file path
     * @return the list of Employee objects
     */
    public List<Employee> importEmployeesFromCSV(String filePath) {
        // TODO: Read CSV and parse into Employee list
        return null;
    }

    /**
     * Safely parses a string to a double.
     * @param str the input string
     * @return the parsed double or 0.0 if invalid
     */
    public static double parseDouble(String input) {
        try {
            return Double.parseDouble(input.replace(",", "").trim());
        } catch (NumberFormatException e) {
            System.out.println("Failed to parse double from: '" + input + "', defaulting to 0.0");
            return 0.0;
        }
    }

    /**
     * Safely parses a string to an integer.
     * @param str the input string
     * @return the parsed int or 0 if invalid
     */
    public static int parseInt(String str) {
        if (isEmpty(str)) {
            return logParseError("int", str, 0);
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return logParseError("int", str, 0);
        }
    }

    /**
     * Safely parses a string to a Date using MM/dd/yyyy format.
     * @param dateStr the input date string
     * @return the parsed Date or null if invalid
     */
    public static Date parseDate(String dateStr) {
        if (isEmpty(dateStr)) {
            System.out.println("Empty or null date string, returning null");
            return null;
        }
        try {
            return DATE_FORMAT.parse(dateStr.trim());
        } catch (ParseException e) {
            System.out.println("Failed to parse date from: '" + dateStr + "', returning null");
            return null;
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static <T> T logParseError(String type, String input, T defaultValue) {
        System.out.println("Failed to parse " + type + " from: '" + input + "', defaulting to " + defaultValue);
        return defaultValue;
    }
}