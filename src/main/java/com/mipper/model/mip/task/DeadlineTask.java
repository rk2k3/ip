package com.mipper.model.mip.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {
    private LocalDateTime deadline;  // null if parsing fails
    private String rawDeadline;      // stores the original input
    private static final DateTimeFormatter INPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");   // e.g. 2/12/2019 1800
    private static final DateTimeFormatter INPUT_DATE =
            DateTimeFormatter.ofPattern("d/M/yyyy");        // e.g. 2/12/2019
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public DeadlineTask(String task, String deadlineStr) {
        super(task);
        this.rawDeadline = deadlineStr;

        // Try datetime first
        try {
            this.deadline = LocalDateTime.parse(deadlineStr.trim(), INPUT_DATE_TIME);
        } catch (DateTimeParseException e1) {
            // Try date only
            try {
                LocalDate date = LocalDate.parse(deadlineStr.trim(), INPUT_DATE);
                this.deadline = date.atTime(23, 59); // 11:59 PM
            } catch (DateTimeParseException e2) {
                this.deadline = null; // fallback to raw string
            }
        }
    }

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

    public String getDeadline() {
        return rawDeadline;
    }

}
