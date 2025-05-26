package com.mycompany.hw2;

/**
 * Represents the government and mandatory deductions from an employee's salary.
 * Includes methods to get/set each deduction and calculate the total.
 *
 * Author: gmmercullo
 */
public class Deductions {
    
    // Fields for different types of deductions
    private double sss;              // Social Security System
    private double philHealth;       // PhilHealth contribution
    private double pagIbig;          // Pag-IBIG contribution
    private double withholdingTax;   // Government withholding tax

    /**
     * Parameterized constructor to initialize all deduction fields.
     */
    public Deductions(double sss, double philHealth, double pagIbig, double withholdingTax) {
        this.sss = sss;
        this.philHealth = philHealth;
        this.pagIbig = pagIbig;
        this.withholdingTax = withholdingTax;
    }

    /**
     * Default constructor for flexibility (allows creating an empty Deductions object).
     */
    public Deductions() {
        // Default constructor
    }

    // Getters and setters for all deduction fields

    public double getSss() {
        return sss;
    }

    public void setSss(double sss) {
        this.sss = sss;
    }

    public double getPhilHealth() {
        return philHealth;
    }

    public void setPhilHealth(double philHealth) {
        this.philHealth = philHealth;
    }

    public double getPagIbig() {
        return pagIbig;
    }

    public void setPagIbig(double pagIbig) {
        this.pagIbig = pagIbig;
    }

    public double getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(double withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    /**
     * Calculates the total of all deductions.
     * 
     * @return the sum of SSS, PhilHealth, Pag-IBIG, and withholding tax
     */
    public double calculateTotalDeductions() {
        return sss + philHealth + pagIbig + withholdingTax;
    }

    /**
     * Returns a summary string showing the total deductions in peso format.
     */
    @Override
    public String toString() {
        return "Total Deductions: ₱" + calculateTotalDeductions();
    }
}
