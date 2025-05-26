package com.mycompany.hw2;

import java.util.List;

/**
 * Represents a detailed payroll report for an employee,
 * including salary breakdown, allowances, deductions, hours worked, and payroll date.
 * 
 * Author: gmmercullo
 */
public class PayrollReport {
    
    // Basic employee info
    private String employeeID;
    private String employeeName;
    private String position;
    private String department;

    // Compensation details
    private double basicSalary;
    private double riceAllowance;
    private double phoneAllowance;
    private double clothingAllowance;

    // Calculated wage and deductions
    private double grossWage;
    private double sss;
    private double pagIbig;
    private double philHealth;
    private double withholdingTax;

    // Final net pay
    private double netPay;

    // Payroll date and attendance info
    private String payrollDate;
    private double hoursWorked;
    private int timeIn;
    private int timeOut;

    /**
     * Full constructor to initialize all payroll report fields.
     */
    public PayrollReport(String employeeID, String employeeName, String position, String department,
                         double basicSalary, double riceAllowance, double phoneAllowance, double clothingAllowance,
                         double grossWage, double sss, double pagIbig, double philHealth, double withholdingTax,
                         double netPay, String payrollDate, double hoursWorked, int timeIn, int timeOut) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.position = position;
        this.department = department;
        this.basicSalary = basicSalary;
        this.riceAllowance = riceAllowance;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossWage = grossWage;
        this.sss = sss;
        this.pagIbig = pagIbig;
        this.philHealth = philHealth;
        this.withholdingTax = withholdingTax;
        this.netPay = netPay;
        this.payrollDate = payrollDate;
        this.hoursWorked = hoursWorked;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    /**
     * Default constructor for flexibility (e.g., object initialization without data).
     */
    public PayrollReport() {
        // Default constructor
    }

    // Getters and Setters for all fields

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getRiceAllowance() {
        return riceAllowance;
    }

    public void setRiceAllowance(double riceAllowance) {
        this.riceAllowance = riceAllowance;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public void setPhoneAllowance(double phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public void setClothingAllowance(double clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public double getGrossWage() {
        return grossWage;
    }

    public void setGrossWage(double grossWage) {
        this.grossWage = grossWage;
    }

    public double getSss() {
        return sss;
    }

    public void setSss(double sss) {
        this.sss = sss;
    }

    public double getPagIbig() {
        return pagIbig;
    }

    public void setPagIbig(double pagIbig) {
        this.pagIbig = pagIbig;
    }

    public double getPhilHealth() {
        return philHealth;
    }

    public void setPhilHealth(double philHealth) {
        this.philHealth = philHealth;
    }

    public double getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(double withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public String getPayrollDate() {
        return payrollDate;
    }

    public void setPayrollDate(String payrollDate) {
        this.payrollDate = payrollDate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public int getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(int timeIn) {
        this.timeIn = timeIn;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
