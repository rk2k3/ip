package com.mipper.model.mip;

public class Parser {
    /**
     * Represents the different types of commands that can be issued by the user.
     * Each value corresponds to a specific operation that Mip can handle.
     */
    public enum CommandType {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        FIND,
        EMPTY,
        UNKNOWN
    }

    /**
     * Determines the type of command from a raw user input string.
     * Matches the input against known command patterns and returns the
     * corresponding CommandType. If no match is found, returns UNKNOWN.
     *
     * @param input the raw user input string
     * @return the CommandType corresponding to the input
     */
    public static CommandType getCommandType(String input) {
        if (input == null || input.isEmpty()) {
            return CommandType.EMPTY;
        } else if (input.equals("bye")) {
            return CommandType.BYE;
        } else if (input.equals("list")) {
            return CommandType.LIST;
        } else if (input.matches("mark \\d+")) {
            return CommandType.MARK;
        } else if (input.matches("unmark \\d+")) {
            return CommandType.UNMARK;
        } else if (input.startsWith("todo")) {
            return CommandType.TODO;
        } else if (input.startsWith("deadline")) {
            return CommandType.DEADLINE;
        } else if (input.startsWith("event")) {
            return CommandType.EVENT;
        } else if (input.matches("delete \\d+")) {
            return CommandType.DELETE;
        } else if (input.startsWith("find")) {
            return CommandType.FIND;
        }
        return CommandType.UNKNOWN;
    }
    
    /**
     * Extracts singular number from a command and returns the int value.
     *
     * @param command string command that starts with "deadline "
     * @return single number contained within command
     */
    public static int getNumber(String command) {
        assert command != null && !command.isEmpty() : "Command should not be null or empty";
        String numberPart = command.replaceAll("\\D+", "");
        assert !numberPart.isEmpty() : "Command should contain at least one number";
        int number = Integer.parseInt(numberPart);
        assert number >= 0 : "Parsed number should be non-negative";
        return number;
    }

    /**
     * Extracts the filter keyword from a find command
     *
     * @param command string command that starts with "find "
     * @return filter keyword
     */
    public static String getFindFilter(String command) {
        String rest = command.substring(5);
        return rest;
    }
    
    /**
     * Returns description from command that starts with "todo "
     *
     * @param command string command that starts with "todo "
     * @return description of todo task
     */
    public static String getTodoDescription(String command) {
        String task = command.substring(5);
        return task;
    }

    /**
     * Returns description from command that starts with "deadline "
     *
     * @param command string command that starts with "deadline "
     * @return description of deadline task
     * @throws MipException if description is empty or command does not include "/by"
     */
    public static String getDeadlineDescription(String command) throws MipException {
        String rest = command.substring(9); // skip "deadline "

        if (rest.isEmpty()) {
            throw new MipException("The description of a deadline cannot be empty.");
        }

        // Split by " /by "
        String[] parts = rest.split(" /by ", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MipException("A deadline must have '/by' followed by a time.");
        }

        String description = parts[0];
        return description;
    }

    /**
     * Returns deadline deu from command that starts with "deadline "
     *
     * @param command string command that starts with "deadline "
     * @return deadline due of deadline task
     * @throws MipException if description is empty or command does not include "/by"
     */
    public static String getDeadlineDue(String command) throws MipException {
        String rest = command.substring(9); // skip "deadline "

        if (rest.isEmpty()) {
            throw new MipException("The description of a deadline cannot be empty.");
        }

        // Split by " /by "
        String[] parts = rest.split(" /by ", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MipException("A deadline must have '/by' followed by a time.");
        }

        String due = parts[1];
        return due;
    }

    /**
     * Returns string array containing the description, start, end of a create EventTask command
     *
     * @param command string command that starts with "event "
     * @return string array of components of command: description, from, and to
     * @throws MipException if description is empty, no "/from" or "/to" in command
     */
    private static String[] splitEventCommand(String command) throws MipException {
        String rest = command.substring(6).trim(); // skip "event "

        if (rest.isEmpty()) {
            throw new MipException("The description of an event cannot be empty.");
        }

        String[] parts1 = rest.split(" /from ", 2);
        if (parts1.length < 2) {
            throw new MipException("An event must have '/from' followed by start time.");
        }

        String[] parts2 = parts1[1].split(" /to ", 2);
        if (parts2.length < 2) {
            throw new MipException("An event must have '/to' followed by end time.");
        }

        // returns: [description, start, end]
        return new String[] { parts1[0].trim(), parts2[0].trim(), parts2[1].trim() };
    }

    /**
     * Returns "description" portion of a create EventTask command
     *
     * @param command string command that starts with "event "
     * @return description of event task
     * @throws MipException if description is empty
     */
    public static String getEventDescription(String command) throws MipException {
        return  splitEventCommand(command)[0];
    }

    /**
     * Returns the start portion of a create EventTask command.
     *
     * @param command the string command that starts with "event "
     * @return the start time or date of the event task
     * @throws MipException if the description is empty or invalid
     */
    public static String getEventStart(String command) throws MipException {
        return splitEventCommand(command)[1];
    }

    /**
     * Returns the end portion of a create EventTask command.
     *
     * @param command the string command that starts with "event "
     * @return the end time or date of the event task
     * @throws MipException if the description is empty or invalid
     */
    public static String getEventEnd(String command) throws MipException {
        return splitEventCommand(command)[2];
    }
}
