package com.mycompany.hw2;

/**
 *
 * @author gmmercullo
 */
// CompensationDetails.java
public class CompensationDetails {

    // Basic monthly salary of the employee
    private double basicSalary;

    // Monthly rice subsidy allowance
    private double riceSubsidy;

    // Monthly phone allowance provided to the employee
    private double phoneAllowance;

    // Monthly clothing allowance provided to the employee
    private double clothingAllowance;

    // Gross salary paid every half month (usually twice a month)
    private double grossSemiMonthlySalary;

    // Hourly rate derived from monthly salary and working hours
    private double hourlyRate;

    // Constructor to initialize all compensation-related fields
    public CompensationDetails(double basicSalary, double riceSubsidy, double phoneAllowance,
                               double clothingAllowance, double grossSemiMonthlySalary, double hourlyRate) {
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlySalary = grossSemiMonthlySalary;
        this.hourlyRate = hourlyRate;
    }

    // Getter for basic salary
    public double getBasicSalary() {
        return basicSalary;
    }

    // Setter for basic salary
    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    // Getter for rice subsidy
    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    // Setter for rice subsidy
    public void setRiceSubsidy(double riceSubsidy) {
        this.riceSubsidy = riceSubsidy;
    }

    // Getter for phone allowance
    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    // Setter for phone allowance
    public void setPhoneAllowance(double phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    // Getter for clothing allowance
    public double getClothingAllowance() {
        return clothingAllowance;
    }

    // Setter for clothing allowance
    public void setClothingAllowance(double clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    // Getter for gross semi-monthly salary
    public double getGrossSemiMonthlySalary() {
        return grossSemiMonthlySalary;
    }

    // Setter for gross semi-monthly salary
    public void setGrossSemiMonthlySalary(double grossSemiMonthlySalary) {
        this.grossSemiMonthlySalary = grossSemiMonthlySalary;
    }

    // Getter for hourly rate
    public double getHourlyRate() {
        return hourlyRate;
    }

    // Setter for hourly rate
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
