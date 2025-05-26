package com.mycompany.hw2;

/**
 * Represents the payroll information for an employee.
 * It includes gross wage, deductions, and net pay.
 * 
 * Author: gmmercullo
 */
public class Payroll {

    // Reference to the employee associated with this payroll
    private Employee employee;

    // Total gross wage before any deductions
    private double grossWage;

    // Total amount of deductions
    private double deductions;

    // Final salary after all deductions
    private double netPay;

    // The specific day or date of payroll processing
    private String payrollDay;

    /**
     * Constructor to initialize all payroll fields.
     *
     * @param employee     The employee whose payroll this is
     * @param grossWage    Gross wage before deductions
     * @param deductions   Total deductions
     * @param netPay       Net pay after deductions
     * @param payrollDay   The date or label of the payroll day
     */
    public Payroll(Employee employee, double grossWage, double deductions, double netPay, String payrollDay) {
        this.employee = employee;
        this.grossWage = grossWage;
        this.deductions = deductions;
        this.netPay = netPay;
        this.payrollDay = payrollDay;
    }

    /**
     * Default constructor.
     */
    public Payroll() {
        // No-arg constructor
    }

    // Getter and setter methods

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getGrossWage() {
        return grossWage;
    }

    public void setGrossWage(double grossWage) {
        this.grossWage = grossWage;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public String getPayrollDay() {
        return payrollDay;
    }

    public void setPayrollDay(String payrollDay) {
        this.payrollDay = payrollDay;
    }
}
