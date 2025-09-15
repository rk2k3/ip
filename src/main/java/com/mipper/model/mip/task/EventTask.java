package com.mipper.model.mip.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventTask extends Task {
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private String rawFrom;
    private String rawTo;

    private static final DateTimeFormatter INPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");   // e.g. 2/12/2019 1800
    private static final DateTimeFormatter INPUT_DATE =
            DateTimeFormatter.ofPattern("d/M/yyyy");        // e.g. 2/12/2019
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructs an EventTask with the given description, start, and end times.
     * Attempts to parse the from and to strings as full datetimes first, then
     * as dates. If both fail, the original raw input strings are stored.
     *
     * @param task the description of the task
     * @param from the raw start time string entered by the user
     * @param to the raw end time string entered by the user
     */
    public EventTask(String task, String from, String to) {
        super(task);

        this.rawFrom = from;
        this.rawTo = to;

        // Parse 'from'
        try {
            this.fromDateTime = LocalDateTime.parse(from.trim(), INPUT_DATE_TIME);
        } catch (DateTimeParseException e1) {
            try {
                LocalDate date = LocalDate.parse(from.trim(), INPUT_DATE);
                this.fromDateTime = date.atStartOfDay(); // start of day
            } catch (DateTimeParseException e2) {
                this.fromDateTime = null;
            }
        }

        // Parse 'to'
        try {
            this.toDateTime = LocalDateTime.parse(to.trim(), INPUT_DATE_TIME);
        } catch (DateTimeParseException e1) {
            try {
                LocalDate date = LocalDate.parse(to.trim(), INPUT_DATE);
                this.toDateTime = date.atTime(23, 59); // end of day
            } catch (DateTimeParseException e2) {
                this.toDateTime = null;
            }
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
        String fromStr = (fromDateTime != null) ? fromDateTime.format(OUTPUT_FORMAT) : rawFrom;
        String toStr = (toDateTime != null) ? toDateTime.format(OUTPUT_FORMAT) : rawTo;
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }

    /**
     * Returns the original raw start time string entered by the user.
     *
     * @return the raw from string
     */
    public String getFrom() {
        return rawFrom;
    }

    /**
     * Returns the original raw end time string entered by the user.
     *
     * @return the raw to string
     */
    public String getTo() {
        return rawTo;
    }
}
