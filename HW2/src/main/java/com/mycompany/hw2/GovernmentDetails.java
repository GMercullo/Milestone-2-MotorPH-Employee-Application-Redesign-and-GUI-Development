/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hw2;

/**
 * Represents an employee's government identification details, such as SSS, PhilHealth,
 * Pag-IBIG, and TIN numbers.
 * 
 * Author: gmmercullo
 */
public class GovernmentDetails {
    
    // Social Security System number
    private String sssNumber;
    
    // PhilHealth insurance number
    private String philHealthNumber;
    
    // Pag-IBIG Fund membership number
    private String pagIbigNumber;
    
    // Taxpayer Identification Number
    private String tinNumber;

    /**
     * Constructor to create a GovernmentDetails object with all government IDs.
     *
     * @param sssNumber         SSS number of the employee
     * @param philHealthNumber  PhilHealth number of the employee
     * @param pagIbigNumber     Pag-IBIG number of the employee
     * @param tinNumber         TIN number of the employee
     */
    public GovernmentDetails(String sssNumber, String philHealthNumber, String pagIbigNumber, String tinNumber) {
        this.sssNumber = sssNumber;
        this.philHealthNumber = philHealthNumber;
        this.pagIbigNumber = pagIbigNumber;
        this.tinNumber = tinNumber;
    }

    /**
     * Gets the SSS number.
     * 
     * @return SSS number
     */
    public String getSssNumber() {
        return sssNumber;
    }

    /**
     * Sets the SSS number.
     * 
     * @param sssNumber new SSS number
     */
    public void setSssNumber(String sssNumber) {
        this.sssNumber = sssNumber;
    }

    /**
     * Gets the PhilHealth number.
     * 
     * @return PhilHealth number
     */
    public String getPhilHealthNumber() {
        return philHealthNumber;
    }

    /**
     * Sets the PhilHealth number.
     * 
     * @param philHealthNumber new PhilHealth number
     */
    public void setPhilHealthNumber(String philHealthNumber) {
        this.philHealthNumber = philHealthNumber;
    }

    /**
     * Gets the Pag-IBIG number.
     * 
     * @return Pag-IBIG number
     */
    public String getPagIbigNumber() {
        return pagIbigNumber;
    }

    /**
     * Sets the Pag-IBIG number.
     * 
     * @param pagIbigNumber new Pag-IBIG number
     */
    public void setPagIbigNumber(String pagIbigNumber) {
        this.pagIbigNumber = pagIbigNumber;
    }

    /**
     * Gets the TIN number.
     * 
     * @return TIN number
     */
    public String getTinNumber() {
        return tinNumber;
    }

    /**
     * Sets the TIN number.
     * 
     * @param tinNumber new TIN number
     */
    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }
}
