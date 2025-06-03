/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hw2;

/**
 * Represents a department within a company, including the department name and its supervisor.
 * 
 * Author: gmmercullo
 */
public class Department {
    // The name of the department (e.g., "HR", "IT", "Finance")
    private String departmentName;

    // The name of the supervisor managing the department
    private String supervisorName;

    /**
     * Constructor to create a Department object with specified values.
     *
     * @param departmentName  the name of the department
     * @param supervisorName  the name of the supervisor of the department
     */
    public Department(String departmentName, String supervisorName) {
        this.departmentName = departmentName;
        this.supervisorName = supervisorName;
    }

    /**
     * Gets the department name.
     *
     * @return the name of the department
     */
    public String getDepartment() {
        return departmentName;
    }

    /**
     * Sets the department name.
     *
     * @param departmentName the new department name
     */
    public void setDepartment(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Gets the name of the supervisor.
     *
     * @return the supervisor's name
     */
    public String getSupervisorName() {
        return supervisorName;
    }

    /**
     * Sets the name of the supervisor.
     *
     * @param supervisorName the new supervisor's name
     */
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
}
