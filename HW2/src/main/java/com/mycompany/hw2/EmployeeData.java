package com.mycompany.hw2;

// Author: gmmercullo

import java.util.Date;

public class EmployeeData {

    // Employee's unique ID
    private int employeeId;

    // Basic personal details
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String phoneNumber;

    // Employment-related details
    private String status;           // Employment status (e.g., Regular, Probationary)
    private String position;         // Job position or title
    private String supervisor;       // Immediate supervisor's name

    // Grouped details for compensation and government info
    private CompensationDetails compensation;
    private GovernmentDetails governmentDetails;


    // Constructor to initialize all employee data
    public EmployeeData(
            int employeeId, String firstName, String lastName, Date birthDate, String address, String phoneNumber, String status, String position, String departmentSupervisor, CompensationDetails compensation, GovernmentDetails governmentDetails) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.position = position;
        this.supervisor = departmentSupervisor;
        this.compensation = compensation;
        this.governmentDetails = governmentDetails;
    }

    // Getters for accessing employee information
    public int getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    // Returns the full name by combining first and last names
    public String getFullName() { return firstName + " " + lastName; }

    public Date getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getStatus() { return status; }
    public String getPosition() { return position; }
    public String getSupervisor() { return supervisor; }

    // Returns the employee's compensation information
    public CompensationDetails getCompensation() { return compensation; }

    // Returns the employee's government identification numbers
    public GovernmentDetails getGovernmentDetails() { return governmentDetails; }

}