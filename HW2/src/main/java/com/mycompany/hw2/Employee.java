package com.mycompany.hw2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Employee {

    // A map to store employee data with employee ID as the key
    private Map<Integer, EmployeeData> employeeMap = new HashMap<>();

    // Default constructor
    public Employee() {
    }

    // Loads employee details from an Excel file (.xlsx format)
    public void loadEmployeesFromExcel(String filePath) {
        try (
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis)
        ) {
            // Access the specific sheet named "Employee Details"
            Sheet sheet = workbook.getSheet("Employee Details");

            // Loop through each row starting from row 1 (row 0 is usually headers)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // skip if row is empty

                try {
                    // Extract employee data from each cell in the row
                    int employeeId = (int) getNumericCellValue(row.getCell(0));
                    String lastName = getStringCellValue(row.getCell(1));
                    String firstName = getStringCellValue(row.getCell(2));
                    Date birthDate = row.getCell(3).getDateCellValue();
                    String address = getStringCellValue(row.getCell(4));
                    String phone = getStringCellValue(row.getCell(5));
                    String sssNumber = getStringCellValue(row.getCell(6));
                    String philHealthNumber = getStringCellValue(row.getCell(7));
                    String tinNumber = getStringCellValue(row.getCell(8));
                    String pagIbigNumber = getStringCellValue(row.getCell(9));
                    String status = getStringCellValue(row.getCell(10));
                    String position = getStringCellValue(row.getCell(11));
                    String departmentSupervisor = getStringCellValue(row.getCell(12));
                    double basicSalary = getNumericCellValue(row.getCell(13));
                    double riceSubsidy = getNumericCellValue(row.getCell(14));
                    double phoneAllowance = getNumericCellValue(row.getCell(15));
                    double clothingAllowance = getNumericCellValue(row.getCell(16));
                    double grossSemiMonthlyRate = getNumericCellValue(row.getCell(17));
                    double hourlyRate = getNumericCellValue(row.getCell(18));

                    // Combine first and last name for full name
                    String fullName = firstName + " " + lastName;

                    // Create an EmployeeData object with the extracted details
                    EmployeeData emp = new EmployeeData(
                        employeeId,
                        fullName,
                        birthDate,
                        address,
                        phone,
                        sssNumber,
                        philHealthNumber,
                        tinNumber,
                        pagIbigNumber,
                        status,
                        position,
                        departmentSupervisor,
                        basicSalary,
                        riceSubsidy,
                        phoneAllowance,
                        clothingAllowance,
                        grossSemiMonthlyRate,
                        hourlyRate
                    );

                    // Debug output to indicate the employee is successfully loaded
                    System.out.println("Loaded employee ID: " + employeeId);

                    // Add the employee data to the map for later access
                    employeeMap.put(employeeId, emp);

                } catch (Exception ex) {
                    // Catch any exception while processing a row and skip that row
                    System.out.println("Skipping row " + i + ": " + ex.getMessage());
                }
            }

        } catch (Exception e) {
            // Print stack trace if something goes wrong with file reading or workbook handling
            e.printStackTrace();
        }
    }

    // Converts a phone number string to an integer (removes non-digit characters)
    private int parsePhoneToInt(String phone) {
        try {
            return Integer.parseInt(phone.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            return 0; // return 0 if parsing fails
        }
    }

    // Retrieves employee data by ID
    public EmployeeData getEmployeeById(int id) {
        return employeeMap.get(id);
    }

    // Safely gets the string value from a cell
    private String getStringCellValue(Cell cell) {
        if (cell == null) return "";

        // Handle different types of cell content
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim(); // regular string
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue()); // convert number to string
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue()); // convert boolean to string
            case FORMULA -> cell.getCellFormula(); // return the formula itself
            default -> "";
        };
    }

    // Safely gets numeric value from a cell and handles edge cases like strings with symbols
    private double getNumericCellValue(Cell cell) {
        if (cell == null) return 0.0;

        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue(); // if already numeric
            case STRING -> {
                // Remove non-numeric characters like ₱ or commas
                String cleaned = cell.getStringCellValue().replaceAll("[^\\d.-]", "");
                try {
                    yield Double.parseDouble(cleaned);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numeric string: " + cell.getStringCellValue());
                    yield 0.0;
                }
            }
            case FORMULA -> {
                try {
                    yield cell.getNumericCellValue();
                } catch (IllegalStateException e) {
                    yield 0.0;
                }
            }
            default -> 0.0; // if it's a blank or unsupported type
        };
    }
}
