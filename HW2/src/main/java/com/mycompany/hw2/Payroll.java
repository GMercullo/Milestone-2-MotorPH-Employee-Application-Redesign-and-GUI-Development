package com.mycompany.hw2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class Payroll {
    private static final String EMPLOYEE_CSV_FILE = "src/MotorPH Employee Data - Employee Details.csv";
    private static final Logger logger = Logger.getLogger(Payroll.class.getName());

    public static void processMonthlyPayroll(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_CSV_FILE))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;  // skip header
                    continue;
                }

                String[] cols = line.split(",", -1); // -1 to include trailing empty fields
                if (cols.length < 17) {
                    logger.warning("Invalid employee data row (expected 17 cols): " + line);
                    continue;
                }

                // Parse employee data from columns
                String empNo = cols[0].trim();
                String lastName = cols[1].trim();
                String firstName = cols[2].trim();
                String birthDate = cols[3].trim();
                String address = cols[4].trim();
                String phone = cols[5].trim();
                String position = cols[6].trim();
                String status = cols[7].trim();
                String supervisor = cols[8].trim();

                String sssNumber = cols[9].trim();
                String philHealthNumber = cols[10].trim();
                String tinNumber = cols[11].trim();
                String pagIbigNumber = cols[12].trim();

                double monthlySalary = CSVHandler.parseDouble(cols[13]);
                double riceSubsidy = CSVHandler.parseDouble(cols[14]);
                double phoneSubsidy = CSVHandler.parseDouble(cols[15]);
                double clothingAllowance = CSVHandler.parseDouble(cols[16]);

                double semiMonthly = monthlySalary / 2;
                double hourlyRate = GrossWage.calculateHourlyRate(monthlySalary);

                int empId;
                try {
                    empId = Integer.parseInt(empNo);
                } catch (NumberFormatException e) {
                    logger.warning("Invalid employee ID: " + empNo);
                    continue;
                }

                // Calculate attendance hours using your Attendance class (CSV based)
                Attendance attendance = new Attendance();
                Attendance.HoursWorked hours = attendance.calculateHours(empId, start, end);

                // Calculate gross wage
                double gross = GrossWage.calculateGross(hourlyRate, hours.regularHours, hours.overtimeHours);

                // Calculate deductions
                double sss = Deductions.calculateSSS(gross);
                double phil = Deductions.calculatePhilHealth(gross);
                double pagibig = Deductions.calculatePagIbig(gross);
                double tax = Deductions.calculateWithholdingTax(monthlySalary);

                double net = gross - Deductions.getTotalDeduction(sss, phil, pagibig, tax)
                             + riceSubsidy + phoneSubsidy + clothingAllowance;

                // Populate payroll report GUI
                PayrollReport reportPanel = new PayrollReport();
                reportPanel.setValue("Employee #:", empNo);
                reportPanel.setValue("Last Name:", lastName);
                reportPanel.setValue("First Name:", firstName);
                reportPanel.setValue("Birth Date:", birthDate);
                reportPanel.setValue("Address:", address);
                reportPanel.setValue("Phone #:", phone);
                reportPanel.setValue("Position:", position);
                reportPanel.setValue("Status:", status);
                reportPanel.setValue("Supervisor:", supervisor);

                reportPanel.setValue("SSS #:", sssNumber);
                reportPanel.setValue("PhilHealth #:", philHealthNumber);
                reportPanel.setValue("TIN #:", tinNumber);
                reportPanel.setValue("Pag-IBIG #:", pagIbigNumber);

                reportPanel.setValue("Basic Salary:", String.format("%.2f", monthlySalary));
                reportPanel.setValue("Rice Subsidy:", String.format("%.2f", riceSubsidy));
                reportPanel.setValue("Phone Subsidy:", String.format("%.2f", phoneSubsidy));
                reportPanel.setValue("Clothing Allowance:", String.format("%.2f", clothingAllowance));

                reportPanel.setValue("Gross Semi-Monthly Salary:", String.format("%.2f", semiMonthly));
                reportPanel.setValue("Hourly Rate:", String.format("%.2f", hourlyRate));
                reportPanel.setValue("Month:", start.getMonth().toString() + " " + year);

                reportPanel.setValue("Regular Hours:", String.format("%.2f", hours.regularHours));
                reportPanel.setValue("Overtime Hours:", String.format("%.2f", hours.overtimeHours));
                reportPanel.setValue("Gross Salary:", String.format("%.2f", gross));

                reportPanel.setValue("SSS Deduction:", String.format("%.2f", sss));
                reportPanel.setValue("PhilHealth Deduction:", String.format("%.2f", phil));
                reportPanel.setValue("Pag-IBIG Deduction:", String.format("%.2f", pagibig));
                reportPanel.setValue("Withholding Tax:", String.format("%.2f", tax));

                reportPanel.setValue("Net Salary:", String.format("%.2f", net));

                // Display the payroll report GUI (your HW2 class must implement this method)
                HW2.displayPayrollReport(reportPanel);
            }
        } catch (IOException e) {
            logger.warning("Error reading employee CSV file: " + e.getMessage());
        }
    }
}