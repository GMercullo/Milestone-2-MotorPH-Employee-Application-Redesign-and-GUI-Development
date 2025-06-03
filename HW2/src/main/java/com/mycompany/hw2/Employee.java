package com.mycompany.hw2;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Employee {

    private Map<Integer, EmployeeData> employeeMap = new HashMap<>();

    public void loadEmployeesFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] tokens;
            reader.readNext(); // Skip header

            while ((tokens = reader.readNext()) != null) {
                if (tokens.length < 19) {
                    System.out.println("Skipping line (too few columns): " + Arrays.toString(tokens));
                    continue;
                }

                try {
                    EmployeeData emp = parseEmployee(tokens);
                    employeeMap.put(emp.getEmployeeId(), emp);
                    System.out.println("Loaded employee ID: " + emp.getEmployeeId());
                } catch (Exception ex) {
                    System.out.println("Skipping line due to parsing error: " + ex.getMessage());
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private EmployeeData parseEmployee(String[] tokens) {
        int employeeId = CSVHandler.parseInt(tokens[0]);
        String lastName = tokens[1].trim();
        String firstName = tokens[2].trim();
        Date birthDate = CSVHandler.parseDate(tokens[3]);
        String address = tokens[4].trim();
        String phone = tokens[5].trim();
        String sssNumber = tokens[6].trim();
        String philHealthNumber = tokens[7].trim();
        String tinNumber = tokens[8].trim();
        String pagIbigNumber = tokens[9].trim();
        String status = tokens[10].trim();
        String position = tokens[11].trim();
        String departmentSupervisor = tokens[12].trim();

        double basicSalary = CSVHandler.parseDouble(tokens[13]);
        double riceSubsidy = CSVHandler.parseDouble(tokens[14]);
        double phoneAllowance = CSVHandler.parseDouble(tokens[15]);
        double clothingAllowance = CSVHandler.parseDouble(tokens[16]);
        double grossSemiMonthlyRate = CSVHandler.parseDouble(tokens[17]);
        double hourlyRate = CSVHandler.parseDouble(tokens[18]);

        CompensationDetails compensation = new CompensationDetails(
            basicSalary,
            riceSubsidy,
            phoneAllowance,
            clothingAllowance,
            grossSemiMonthlyRate,
            hourlyRate
        );

        GovernmentDetails govDetails = new GovernmentDetails(
            sssNumber,
            philHealthNumber,
            tinNumber,
            pagIbigNumber
        );

        return new EmployeeData(
            employeeId,
            firstName,
            lastName,
            birthDate,
            address,
            phone,
            status,
            position,
            departmentSupervisor,
            compensation,
            govDetails
        );
    }

    public EmployeeData getEmployeeById(int id) {
        return employeeMap.get(id);
    }

    public List<EmployeeData> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    public void addEmployee(EmployeeData newEmp) {
        if (employeeMap.containsKey(newEmp.getEmployeeId())) {
            System.out.println("Employee ID already exists: " + newEmp.getEmployeeId());
        } else {
            employeeMap.put(newEmp.getEmployeeId(), newEmp);
            System.out.println("Added new employee: " + newEmp.getEmployeeId());
        }
    }
}