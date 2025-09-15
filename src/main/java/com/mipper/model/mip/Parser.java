package com.mipper.model.mip;

public class Parser {

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
     * Returns string array containing the description, from, and to of a command
     * that starts with "event "
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

        // returns: [task description, from, to]
        return new String[] { parts1[0].trim(), parts2[0].trim(), parts2[1].trim() };
    }

    /**
     * Returns description from command that starts with "event "
     *
     * @param command string command that starts with "event "
     * @return description of event task
     * @throws MipException if description is empty
     */
    public static String getEventDescription(String command) throws MipException {
        return  splitEventCommand(command)[0];
    }

    public static String getEventFrom(String command) throws MipException {
        return splitEventCommand(command)[1];
    }

    public static String getEventTo(String command) throws MipException {
        return splitEventCommand(command)[2];
    }
}
