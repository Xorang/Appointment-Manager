package Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This manages the logging. All log files are placed in the
 * src/Logging folder. Fulfills REQUIREMENT J.
 */

public class AccessLogger {

    private static final Path LOG_FILE = Paths.get("src/Logging/" + LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ".txt");
    private static final String TIMESTAMP_PATTERN = "MM/dd/yyyy hh:mm:ss:AAAAAAAA a";

    public static void logAccessAttempt(String userName, boolean valid) {
        if (Files.exists(LOG_FILE)) {
            if (valid) {
                addToFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " logged in\n");
            } else {
                addToFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " attempted to log in with invalid credentials\n");
            }
        } else {
            if (valid) {
                createFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " logged in\n");
            } else {
                createFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " attempted to log in with invalid credentials\n");
            }
        }
    }

    public static void logSignOut(String userName) {
        if (Files.exists(LOG_FILE)) {
            addToFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " logged out\n");
        } else {
            createFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " logged out\n");
        }
    }

    public static void logAction(String userName, String action) {
        if (Files.exists(LOG_FILE)) {
            addToFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " " + action + "\n");
        } else {
            createFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + ": " + userName + " " + action + "\n");
        }
    }

    private static void addToFile(String message) {
        try (BufferedWriter writer = Files.newBufferedWriter(LOG_FILE, StandardOpenOption.APPEND)) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile(String message) {
        try (BufferedWriter writer = Files.newBufferedWriter(LOG_FILE, StandardOpenOption.CREATE)) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}