public class Parser {
    public static int getNumber(String command) {
        String numberPart = command.replaceAll("\\D+", "");
        int number = Integer.parseInt(numberPart);
        return number;
    }

    public static String getTodoDescription(String command) {
        String task = command.substring(5);
        return task;
    }

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
