package com.mipper.model.mip.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {

    private String rawDeadline;      // stores the original input deadline

    /**
     * Stores the 'deadline' portion of the command input as LocalDateTime type if it matches proper date-time format.
     * Set to null if format requirements are not met
     */
    private LocalDateTime deadline;

    /**
     * Formatter for parsing user input as full date and time (e.g., "2/12/2019 1800").
     */
    private static final DateTimeFormatter INPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Formatter for parsing user input as date only (e.g., "2/12/2019").
     */
    private static final DateTimeFormatter INPUT_DATE =
            DateTimeFormatter.ofPattern("d/M/yyyy");    // e.g. 2/12/2019

    /**
     * Formatter used to display dates and times in a readable format
     * (e.g., "Dec 02 2019, 6:00PM").
     */
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructs a DeadlineTask with the given description and deadline string.
     * Attempts to parse the deadline as a full datetime first, then as a date.
     * If parsing fails, only the original raw dateline string is stored.
     *
     * @param task the description of the task
     * @param deadlineStr the raw deadline string entered by the user
     */
    public DeadlineTask(String task, String deadlineStr) {
        super(task);
        this.rawDeadline = deadlineStr;
        this.deadline = parseDeadline(deadlineStr);
    }

    /**
     * Tries to parse a string into LocalDateTime. First attempts full datetime format,
     * then date-only format with a default time of 23:59. Returns null if parsing fails.
     *
     * @param input the string to parse
     * @return parsed LocalDateTime or null if parsing fails
     */
    private LocalDateTime parseDeadline(String input) {
        if (input == null) {
            return null;
        }

        input = input.trim();

        // Try full datetime
        try {
            return LocalDateTime.parse(input, INPUT_DATE_TIME);
        } catch (DateTimeParseException e) {
            // Ignore and try date only
        }

        // Try date only
        try {
            LocalDate date = LocalDate.parse(input, INPUT_DATE);
            return date.atTime(23, 59); // default end of day
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Returns a string representation of the deadline task.
     * If the deadline was successfully parsed, it is formatted nicely;
     * otherwise, the raw input string is shown.
     *
     * @return formatted string representing the task
     */
    @Override
    public String toString() {
        if (deadline != null) {
            return "[D]" + super.toString() +
                    String.format(" (by: %s)", deadline.format(OUTPUT_FORMAT));
        } else {
            return "[D]" + super.toString() +
                    String.format(" (by: %s)", rawDeadline);
        }
    }

    /**
     * Returns the original raw deadline string entered by the user.
     *
     * @return the raw deadline string
     */
    public String getDeadline() {
        return rawDeadline;
    }

}
