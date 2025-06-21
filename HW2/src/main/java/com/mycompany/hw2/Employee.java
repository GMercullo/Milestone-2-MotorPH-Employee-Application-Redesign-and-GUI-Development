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
                    EmployeeData emp = CSVHandler.parseEmployeeRow(tokens);
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
            try {
                CSVHandler.saveEmployeeToCSV(filePath, getAllEmployees());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Employee ID not found: " + id);
        }
    }

    public void deleteEmployee(int id) {
        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            System.out.println("Deleted employee ID: " + id);
            try {
                CSVHandler.saveEmployeeToCSV(filePath, getAllEmployees());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Employee ID not found: " + id);
        }
    }
}