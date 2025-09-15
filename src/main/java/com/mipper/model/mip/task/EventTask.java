package com.mipper.model.mip.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventTask extends Task {
    private String rawStart;
    private String rawEnd;

    /**
     * Stores the 'from' portion of the command input as LocalDateTime type if it matches proper date-time format.
     * Set to null if format requirements are not met
     */
    private LocalDateTime dateTimeStart;

    /**
     * Stores the 'to' portion of the command input as LocalDateTime type if it matches proper date-time format.
     * Set to null if format requirements are not met
     */
    private LocalDateTime dateTimeEnd;

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
     * Constructs an EventTask with the given description, start, and end times.
     * Attempts to parse the from and to strings as full datetimes first, then
     * as dates. If parsing fails, the original raw input strings are stored.
     *
     * @param task the description of the task
     * @param from the raw start time string entered by the user
     * @param to the raw end time string entered by the user
     */
    public EventTask(String task, String from, String to) {
        super(task);

        this.rawStart = from;
        this.rawEnd = to;

        this.dateTimeStart = parseDateTime(from, true);  // start of day fallback
        this.dateTimeEnd = parseDateTime(to, false);     // end of day fallback
    }

    /**
     * Tries to parse a string into LocalDateTime. First attempts datetime format,
     * then date-only format. Returns null if parsing fails.
     *
     * @param input the string to parse
     * @param startOfDay if true, sets date-only parsing to start of day; otherwise, end of day
     * @return parsed LocalDateTime or null if parsing fails
     */
    private LocalDateTime parseDateTime(String input, boolean startOfDay) {
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
            return startOfDay ? date.atStartOfDay() : date.atTime(23, 59);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Returns a string representation of the event task. If the start and end
     * times were successfully parsed, they are formatted nicely; otherwise, the
     * raw input strings are shown.
     *
     * @return formatted string representing the task
     */
    @Override
    public String toString() {
        String fromStr = (dateTimeStart != null) ? dateTimeStart.format(OUTPUT_FORMAT) : rawStart;
        String toStr = (dateTimeEnd != null) ? dateTimeEnd.format(OUTPUT_FORMAT) : rawEnd;
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }

    /**
     * Returns the original raw start time string entered by the user.
     *
     * @return the raw from string
     */
    public String getRawStart() {
        return rawStart;
    }

    /**
     * Returns the original raw end time string entered by the user.
     *
     * @return the raw to string
     */
    public String getRawEnd() {
        return rawEnd;
    }
}
