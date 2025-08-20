import java.util.Scanner;

public class Mip {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Mip\nWhat can I do for you?\n");

        Task[] tasks = new Task[100];
        String command;
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                command = input.nextLine();
                if (command.equals("bye")) {
                    System.out.println("Goodbye! Mip WILL see you again.");
                    break;
                } else if (command.equals("list")) { // List out all previous commands
                    if (Task.getCount() == 0) {
                        throw new MipException("Your task list is empty.");
                    }
                    for (int i = 1; i <= Task.getCount(); i++) {
                        System.out.println(i + "." + tasks[i]);
                    }
                    System.out.println(); // Add a new line at the end
                } else if (command.matches("mark \\d+")) {
                    String numberPart = command.replaceAll("\\D+", "");
                    int number = Integer.parseInt(numberPart);
                    tasks[number].setCompleted(true);
                    System.out.println("Super amazing!!! I've marked this task as done:");
                    System.out.println(tasks[number] + "\n");
                } else if (command.matches("unmark \\d+")) {
                    String numberPart = command.replaceAll("\\D+", "");
                    int number = Integer.parseInt(numberPart);
                    tasks[number].setCompleted(false);
                    System.out.println("I've marked this task as not done:");
                    System.out.println(tasks[number] + "\n");
                } else if (command.isEmpty()) {
                    continue;
                } else if (command.startsWith("todo")) {
                    String task = command.substring(5); // skip "todo "

                    if (task.isEmpty()) {
                        throw new MipException("The description of a todo cannot be empty.");
                    }

                    TodoTask todoTask = new TodoTask(task);
                    tasks[Task.getCount()] = todoTask;

                    System.out.println("Understood, I've added this task:");
                    System.out.println(todoTask);
                    System.out.println("The number of your tasks is: " + Task.getCount() + "\n");
                } else if (command.startsWith("deadline")) {
                    String rest = command.substring(9); // skip "deadline "

                    if (rest.isEmpty()) {
                        throw new MipException("The description of a deadline cannot be empty.");
                    }

                    // Split by " /by "
                    String[] parts = rest.split(" /by ", 2);

                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new MipException("A deadline must have '/by' followed by a time.");
                    }
                    String task = parts[0];
                    String deadline = parts.length > 1 ? parts[1] : "";

                    DeadlineTask deadlineTask = new DeadlineTask(task, deadline);
                    tasks[Task.getCount()] = deadlineTask;

                    System.out.println("Understood, I've added this task:");
                    System.out.println(deadlineTask);
                    System.out.println("The number of your tasks is: " + Task.getCount() + "\n");
                } else if (command.startsWith("event")) {
                    String rest = command.substring(6); // skip "event "
                    if (rest.isEmpty()) {
                        throw new MipException("The description of an event cannot be empty.");
                    }

                    // Split by " /from " first
                    String[] parts1 = rest.split(" /from ", 2);

                    if (parts1.length < 2) {
                        throw new MipException("An event must have '/from' followed by start time.");
                    }

                    String task = parts1[0];

                    // Split the second part by " /to "
                    String[] parts2 = parts1[1].split(" /to ", 2);

                    if (parts2.length < 2) {
                        throw new MipException("An event must have '/to' followed by end time.");
                    }

                    String from = parts2[0];
                    String to = parts2[1];
                    EventTask eventTask = new EventTask(task, from, to);

                    tasks[Task.getCount()] = eventTask;
                    System.out.println("Understood, I've added this task:");
                    System.out.println(eventTask);
                    System.out.println("The number of your tasks is: " + Task.getCount() + "\n");
                } else {
                    throw new MipException("I have no idea what you are talking about...");
                }
            } catch (MipException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }
}