package com.mycompany.hw2;

import java.util.Date;

public class EmployeeData {

    private int employeeId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String phoneNumber;

    private String status;
    private String position;
    private String supervisor;

    private CompensationDetails compensation;
    private GovernmentDetails governmentDetails; 
    
    
    public EmployeeData(
        int employeeId,
        String firstName,
        String lastName,
        Date birthDate,
        String address,
        String phoneNumber,
        String status,
        String position,
        String departmentSupervisor,
        CompensationDetails compensation,
        GovernmentDetails governmentDetails
    ) {
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

    // Getters
    public int getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public Date getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getStatus() { return status; }
    public String getPosition() { return position; }
    public String getSupervisor() { return supervisor; }
    public CompensationDetails getCompensation() { return compensation; }
    public GovernmentDetails getGovernmentDetails() { return governmentDetails; }

}
