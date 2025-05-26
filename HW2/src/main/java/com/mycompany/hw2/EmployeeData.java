package com.mycompany.hw2;

import java.util.Date;

public class EmployeeData {

    // Fields to store basic employee information and government IDs
    private int employeeId;
    private String fullName;
    private Date birthDate;
    private String address;
    private String phoneNumber;
    private String sssNumber;
    private String philHealthNumber;
    private String tinNumber;
    private String pagIbigNumber;

    // Fields for employment-related details
    private String status;
    private String position;
    private String departmentSupervisor;

    // Fields for salary and allowance information
    private double basicSalary;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double grossSemiMonthlyRate;
    private double hourlyRate;

    // Constructor that initializes all the fields
    public EmployeeData(
        int employeeId,
        String fullName,
        Date birthDate,
        String address,
        String phoneNumber,
        String sssNumber,
        String philHealthNumber,
        String tinNumber,
        String pagIbigNumber,
        String status,
        String position,
        String departmentSupervisor,
        double basicSalary,
        double riceSubsidy,
        double phoneAllowance,
        double clothingAllowance,
        double grossSemiMonthlyRate,
        double hourlyRate
    ) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philHealthNumber = philHealthNumber;
        this.tinNumber = tinNumber;
        this.pagIbigNumber = pagIbigNumber;
        this.status = status;
        this.position = position;
        this.departmentSupervisor = departmentSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
    }

    // Getter methods for accessing private fields
    public int getEmployeeId() { return employeeId; }
    public String getFullName() { return fullName; }
    public Date getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getSSSNumber() { return sssNumber; }
    public String getPhilHealthNumber() { return philHealthNumber; }
    public String getTinNumber() { return tinNumber; }
    public String getPagIbigNumber() { return pagIbigNumber; }
    public String getStatus() { return status; }
    public String getPosition() { return position; }
    public String getDepartmentSupervisor() { return departmentSupervisor; }
    public double getBasicSalary() { return basicSalary; }
    public double getRiceSubsidy() { return riceSubsidy; }
    public double getPhoneAllowance() { return phoneAllowance; }
    public double getClothingAllowance() { return clothingAllowance; }
    public double getGrossSemiMonthlyRate() { return grossSemiMonthlyRate; }
    public double getHourlyRate() { return hourlyRate; }
}
