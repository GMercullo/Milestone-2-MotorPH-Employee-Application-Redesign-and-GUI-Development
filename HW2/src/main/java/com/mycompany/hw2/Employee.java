package com.mycompany.hw2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Employee {

    private Map<Integer, EmployeeData> employeeMap = new HashMap<>();

    public Employee() {
    }

    public void loadEmployeesFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Employee Details");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
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

                    String fullName = firstName + " " + lastName;

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

                    System.out.println("Loaded employee ID: " + employeeId);
                    employeeMap.put(employeeId, emp);

                } catch (Exception ex) {
                    System.out.println("Skipping row " + i + ": " + ex.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int parsePhoneToInt(String phone) {
        try {
            return Integer.parseInt(phone.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public EmployeeData getEmployeeById(int id) {
        return employeeMap.get(id);
    }
    private String getStringCellValue(Cell cell) {
    if (cell == null) return "";
    return switch (cell.getCellType()) {
        case STRING -> cell.getStringCellValue().trim();
        case NUMERIC -> String.valueOf((long) cell.getNumericCellValue()); // handles ID/phone as integers
        case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
        case FORMULA -> cell.getCellFormula();
        default -> "";
    };
}

    private double getNumericCellValue(Cell cell) {
        if (cell == null) return 0.0;

        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> {
                String cleaned = cell.getStringCellValue().replaceAll("[^\\d.-]", "");  // remove ₱, commas, etc.
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
            default -> 0.0;
        };
}


}
