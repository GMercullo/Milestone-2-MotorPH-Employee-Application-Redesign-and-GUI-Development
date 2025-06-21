package com.mycompany.hw2;

// Author: gmmercullo

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Calculates attendance from a CSV file: total regular and overtime hours for a given employee and date range.
 */
public class Attendance {
    private static final String ATTENDANCE_CSV_FILE = "src/MotorPH Employee Data - Attendance Record.csv";
    private static final Logger logger = Logger.getLogger(Attendance.class.getName());

    // Formatter for parsing dates in format M/d/yyyy
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    
    // Formatter for parsing time in 24-hour format H:mm
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    // Returns total regular hours worked by the employee within a date range
    public double getTotalRegularHours(int employeeId, LocalDate start, LocalDate end) {
        HoursWorked hours = calculateHours(employeeId, start, end);
        return hours.regularHours;
    }

    // Returns total overtime hours worked by the employee within a date range
    public double getTotalOvertimeHours(int employeeId, LocalDate start, LocalDate end) {
        HoursWorked hours = calculateHours(employeeId, start, end);
        return hours.overtimeHours;
    }

    // Main method for computing total regular and overtime hours
    HoursWorked calculateHours(int employeeId, LocalDate start, LocalDate end) {
        double totalRegular = 0;
        double totalOvertime = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_CSV_FILE))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header row
                }

                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                // Parse fields from CSV
                int id = CSVHandler.parseInt(parts[0]);
                String lastName = parts[1].trim();
                String firstName = parts[2].trim();
                LocalDate date = parseDate(parts[3].trim());
                LocalTime login = parseTime(parts[4].trim());
                LocalTime logout = parseTime(parts[5].trim());

                // Skip records that do not match employee or are out of range
                if (id != employeeId || date == null || login == null || logout == null) continue;
                if (date.isBefore(start) || date.isAfter(end)) continue;

                // Calculate total daily hours
                double dailyHours = ChronoUnit.MINUTES.between(login, logout) / 60.0;
                dailyHours = Math.round(dailyHours * 10.0) / 10.0;
                dailyHours -= 1; // Deduct 1 hour for lunch

                if (dailyHours <= 0) continue;

                // Separate regular and overtime hours
                if (dailyHours <= 8) {
                    totalRegular += dailyHours;
                } else {
                    totalRegular += 8;
                    totalOvertime += (dailyHours - 8);
                }
            }

        } catch (IOException e) {
            logger.warning("Error reading attendance CSV file: " + e.getMessage());
        }

        return new HoursWorked(totalRegular, totalOvertime);
    }

    // Parses a date string using the defined DATE_FORMATTER
    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            logger.warning("Invalid date: " + dateStr);
            return null;
        }
    }

    // Parses a time string using the defined TIME_FORMATTER
    private LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (Exception e) {
            logger.warning("Invalid time: " + timeStr);
            return null;
        }
    }

    // Inner class for holding total regular and overtime hours
    public static class HoursWorked {
        public final double regularHours;
        public final double overtimeHours;

        public HoursWorked(double regularHours, double overtimeHours) {
            this.regularHours = regularHours;
            this.overtimeHours = overtimeHours;
        }
    }

    /**
     * Checks if attendance data exists for the given employee and month (format: MM-yyyy).
     */
    public boolean hasAttendanceForMonth(int employeeId, String targetMonth) {
       DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

       try {
           // Convert input string to YearMonth
           YearMonth targetYearMonth = YearMonth.parse(targetMonth, inputFormatter);

           try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_CSV_FILE))) {
               String line;
               boolean isFirstLine = true;

               while ((line = br.readLine()) != null) {
                   if (isFirstLine) {
                       isFirstLine = false;
                       continue; // Skip header
                   }

                   String[] parts = line.split(",");
                   if (parts.length < 6) continue;

                   int id = CSVHandler.parseInt(parts[0]);
                   LocalDate date = parseDate(parts[3].trim());

                   if (id != employeeId || date == null) continue;

                   YearMonth recordMonth = YearMonth.from(date);
                   if (recordMonth.equals(targetYearMonth)) {
                       return true; // Found attendance in target month
                   }
               }
           }
       } catch (Exception e) {
           logger.warning("Error parsing or reading attendance data: " + e.getMessage());
       }

       return false;
    }
    
    public List<String> getAvailableMonths(int employeeId) {
        Set<String> uniqueMonths = new LinkedHashSet<>();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_CSV_FILE))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                int id = CSVHandler.parseInt(parts[0]);
                LocalDate date = parseDate(parts[3].trim());

                if (id != employeeId || date == null) continue;

                String formattedMonth = date.format(monthFormatter);
                uniqueMonths.add(formattedMonth);
            }
        } catch (IOException e) {
            logger.warning("Error reading attendance file for available months: " + e.getMessage());
        }

        return new ArrayList<>(uniqueMonths);
    }
}
