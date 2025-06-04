package com.mycompany.hw2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

/**
 * Calculates attendance from a CSV file: total regular and overtime hours for a given employee and date range.
 */
public class Attendance {
    private static final String ATTENDANCE_CSV_FILE = "src/MotorPH Employee Data - Attendance Record.csv";
    private static final Logger logger = Logger.getLogger(Attendance.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    public double getTotalRegularHours(int employeeId, LocalDate start, LocalDate end) {
        HoursWorked hours = calculateHours(employeeId, start, end);
        return hours.regularHours;
    }

    public double getTotalOvertimeHours(int employeeId, LocalDate start, LocalDate end) {
        HoursWorked hours = calculateHours(employeeId, start, end);
        return hours.overtimeHours;
    }

    HoursWorked calculateHours(int employeeId, LocalDate start, LocalDate end) {
        double totalRegular = 0;
        double totalOvertime = 0;

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
                String lastName = parts[1].trim();
                String firstName = parts[2].trim();
                LocalDate date = parseDate(parts[3].trim());
                LocalTime login = parseTime(parts[4].trim());
                LocalTime logout = parseTime(parts[5].trim());

                if (id != employeeId || date == null || login == null || logout == null) continue;
                if (date.isBefore(start) || date.isAfter(end)) continue;

                double dailyHours = ChronoUnit.MINUTES.between(login, logout) / 60.0;
                dailyHours = Math.round(dailyHours * 10.0) / 10.0;
                dailyHours -= 1; // Deduct 1 hour for lunch

                if (dailyHours <= 0) continue;

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

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            logger.warning("Invalid date: " + dateStr);
            return null;
        }
    }

    private LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (Exception e) {
            logger.warning("Invalid time: " + timeStr);
            return null;
        }
    }

    public static class HoursWorked {
        public final double regularHours;
        public final double overtimeHours;

        public HoursWorked(double regularHours, double overtimeHours) {
            this.regularHours = regularHours;
            this.overtimeHours = overtimeHours;
        }
    }
}