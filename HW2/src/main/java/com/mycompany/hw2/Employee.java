package com.mycompany.hw2;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The Employee class manages employee records loaded from a CSV file.
 * It allows operations such as loading, adding, updating, deleting, and retrieving employees.
 *  Author: gmmercullo
 */
public class Employee {

    // Stores employee data mapped by employee ID
    private Map<Integer, EmployeeData> employeeMap = new HashMap<>();
    private String filePath;

    /**
     * Loads employee records from a specified CSV file and populates the employeeMap.
     * Skips the header row and any lines with incomplete or invalid data.
     *
     * @param filePath the path to the employee CSV file
     */
    public void loadEmployeesFromCSV(String filePath) {
        this.filePath = filePath;
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] tokens;
            reader.readNext(); // Skip header row

            while ((tokens = reader.readNext()) != null) {
                // Skip rows with fewer than 19 columns
                if (tokens.length < 19) {
                    System.out.println("Skipping line (too few columns): " + Arrays.toString(tokens));
                    continue;
                }

                try {
                    // Parse the employee data and add to the map
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

    /**
     * Parses a CSV line into an EmployeeData object using the given token array.
     *
     * @param tokens a single row from the CSV file split into an array
     * @return the constructed EmployeeData object
     */
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

    /**
     * Retrieves an employee by ID.
     *
     * @param id the employee ID
     * @return the EmployeeData object, or null if not found
     */
    public EmployeeData getEmployeeById(int id) {
        return employeeMap.get(id);
    }

    /**
     * Alias for getEmployeeById(). Redundant but kept for compatibility.
     *
     * @param id the employee ID
     * @return the EmployeeData object, or null if not found
     */
    public EmployeeData findById(int id) {
        return employeeMap.get(id);
    }

    /**
     * Returns a list of all employees currently loaded.
     *
     * @return a list of EmployeeData objects
     */
    public List<EmployeeData> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    /**
     * Adds a new employee to the system. If the ID already exists, the employee is not added.
     *
     * @param newEmp the EmployeeData object to add
     */
    public void addEmployee(EmployeeData newEmp) {
        if (employeeMap.containsKey(newEmp.getEmployeeId())) {
            System.out.println("Employee ID already exists: " + newEmp.getEmployeeId());
        } else {
            employeeMap.put(newEmp.getEmployeeId(), newEmp);
            System.out.println("Added new employee: " + newEmp.getEmployeeId());
        }
    }

    /**
     * Updates an existing employee's data and saves the updated list to the CSV file.
     *
     * @param id         the employee ID to update
     * @param updatedEmp the updated EmployeeData object
     */
    public void updateEmployee(int id, EmployeeData updatedEmp) {
        if (employeeMap.containsKey(id)) {
            employeeMap.put(id, updatedEmp);
            System.out.println("Updated employee ID: " + id);
            CSVHandler.saveEmployeesToCSV(filePath, getAllEmployees());
        } else {
            System.out.println("Employee ID not found: " + id);
        }
    }

    /**
     * Deletes an employee from the system by ID and updates the CSV file.
     *
     * @param id the employee ID to delete
     */
    public void deleteEmployee(int id) {
        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            System.out.println("Deleted employee ID: " + id);
            CSVHandler.saveEmployeesToCSV(filePath, getAllEmployees());
        } else {
            System.out.println("Employee ID not found: " + id);
        }
    }
}