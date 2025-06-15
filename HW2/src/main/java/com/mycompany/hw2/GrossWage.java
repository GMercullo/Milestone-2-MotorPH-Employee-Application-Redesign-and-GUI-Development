package com.mycompany.hw2;

/**
 * Represents the gross wage computation for an employee
 * based on hours worked and hourly rate.
 *
 * Author: gmmercullo
 */
public class GrossWage {
    public static double calculateHourlyRate(double monthlySalary) {
        return (monthlySalary / 21) / 8;
    }

    public static double calculateGross(double hourlyRate, double regHours, double otHours) {
        double regular = hourlyRate * regHours;
        double overtime = hourlyRate * otHours * 1.25;
        return regular + overtime;
    }
}

