package com.mycompany.hw2;

/**
 * Represents the gross wage computation for an employee
 * based on hours worked and hourly rate.
 *
 * Author: gmmercullo
 */
public class GrossWage {
    
    // Number of hours worked by the employee
    private double hoursWorked;
    
    // Rate of pay per hour
    private double hourlyRate;

    /**
     * Parameterized constructor to initialize hours worked and hourly rate.
     */
    public GrossWage(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Default constructor for flexibility when setting values later.
     */
    public GrossWage() {
        // Default constructor
    }

    // Getter and setter for hours worked
    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    // Getter and setter for hourly rate
    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Calculates the gross wage by multiplying hours worked by hourly rate.
     * 
     * @return the total gross wage
     */
    public double calculateGrossWage() {
        return hoursWorked * hourlyRate;
    }

    /**
     * Returns a formatted string displaying the calculated gross wage.
     */
    @Override
    public String toString() {
        return "Gross Wage: ₱" + calculateGrossWage();
    }
}
