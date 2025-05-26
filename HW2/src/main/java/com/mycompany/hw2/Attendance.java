package com.mycompany.hw2;

import java.util.Date;

/**
 * Represents a daily attendance record for an employee,
 * including time in and time out on a specific date.
 *
 * Author: gmmercullo
 */
public class Attendance {

    // The unique identifier of the employee
    private String employeeID;

    // The date of the attendance record
    private Date date;

    // The time the employee clocked in
    private String timeIn;

    // The time the employee clocked out
    private String timeOut;

    /**
     * Constructor to initialize the attendance record.
     *
     * @param employeeID The ID of the employee
     * @param date       The date of attendance
     * @param timeIn     The time the employee clocked in
     * @param timeOut    The time the employee clocked out
     */
    public Attendance(String employeeID, Date date, String timeIn, String timeOut) {
        this.employeeID = employeeID;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    // Getter for employee ID
    public String getEmployeeID() {
        return employeeID;
    }

    // Setter for employee ID
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    // Getter for date
    public Date getDate() {
        return date;
    }

    // Setter for date
    public void setDate(Date date) {
        this.date = date;
    }

    // Getter for time in
    public String getTimeIn() {
        return timeIn;
    }

    // Setter for time in
    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    // Getter for time out
    public String getTimeOut() {
        return timeOut;
    }

    // Setter for time out
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
}
