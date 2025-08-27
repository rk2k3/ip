import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class Mip {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static String command;
    private static File storage;

    private static void greet() {
        System.out.println("Hello! I'm Mip\nWhat can I do for you?\n");
    }

    private static void sayGoodbye() {
        System.out.println("Goodbye! Mip WILL see you again.");
    }

    private static void markTask(int number) {
        tasks.get(number - 1).setCompleted(true);
    }

    private static void unmarkTask(int number) {
        tasks.get(number - 1).setCompleted(false);
    }

    private static Task deleteTask(int number) {
        return tasks.remove(number - 1);
    }

    private static void printTask(int number) {
        System.out.println(tasks.get(number - 1));
    }

    public static void initStorage() {
        File f = new File("data/Mip.txt");

        // Ensure the parent directory exists
        File parentDir = f.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Created directory: " + parentDir);
            } else {
                System.out.println("Failed to create directory: " + parentDir);
            }
        }

        // Ensure the file exists
        if (!f.exists()) {
            try {
                if (f.createNewFile()) {
                    System.out.println("Created file: " + f);
                } else {
                    System.out.println("Failed to create file: " + f);
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        } else {
            System.out.println("File already exists: " + f);
        }

        storage = f;
    }

    private static void initTasks() {
        if (storage == null) {
            initStorage();
        }

        try (Scanner sc = new Scanner(storage)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 3) continue; // skip invalid lines

                String type = parts[0].trim();
                boolean isDone = parts[1].trim().equals("1");
                String description = parts[2].trim();

                Task task = null;

                switch (type) {
                case "T":
                    task = new TodoTask(description);
                    break;
                case "D":
                    String deadline = parts.length > 3 ? parts[3].trim() : "";
                    task = new DeadlineTask(description, deadline);
                    break;
                case "E":
                    String from = parts.length > 3 ? parts[3].trim() : "";
                    String to = parts.length > 4 ? parts[4].trim() : "";
                    task = new EventTask(description, from, to);
                    break;
                default:
                    System.out.println("Unknown task type: " + type);
                }

                if (task != null) {
                    task.setCompleted(isDone);
                    tasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Failed to read storage file: " + e.getMessage());
        }
    }

    private static void saveTasks() {
        if (storage == null) {
            System.out.println("Storage file not initialized!");
            return;
        }

        try (FileWriter writer = new FileWriter(storage, false)) { // overwrite file
            for (Task t : tasks) {
                String line = "";

                if (t instanceof TodoTask) {
                    line = String.format("T | %d | %s", t.isCompleted() ? 1 : 0, t.getTask());
                } else if (t instanceof DeadlineTask) {
                    DeadlineTask dt = (DeadlineTask) t;
                    line = String.format("D | %d | %s | %s", dt.isCompleted() ? 1 : 0, dt.getTask(), dt.getDeadline());
                } else if (t instanceof EventTask) {
                    EventTask et = (EventTask) t;
                    line = String.format("E | %d | %s | %s | %s", et.isCompleted() ? 1 : 0,
                            et.getTask(), et.getFrom(), et.getTo());
                }

                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initTasks();
        greet();

        while (true) {
            try {
                command = input.nextLine();
                if (command.equals("bye")) {
                    sayGoodbye();
                    break;
                } else if (command.equals("list")) { // List out all previous commands
                    if (tasks.isEmpty()) {
                        throw new MipException("Your task list is empty.");
                    }
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    System.out.println(); // Add a new line at the end
                } else if (command.matches("mark \\d+")) {
                    String numberPart = command.replaceAll("\\D+", "");
                    int number = Integer.parseInt(numberPart);
                    markTask(number);
                    System.out.println("Super amazing!!! I've marked this task as done:");
                    printTask(number);
                    System.out.println();
                } else if (command.matches("unmark \\d+")) {
                    String numberPart = command.replaceAll("\\D+", "");
                    int number = Integer.parseInt(numberPart);
                    unmarkTask(number);
                    System.out.println("I've marked this task as not done:");
                    printTask(number);
                    System.out.println();
                } else if (command.isEmpty()) {
                    continue;
                } else if (command.startsWith("todo")) {
                    String task = command.substring(5); // skip "todo "

                    if (task.isEmpty()) {
                        throw new MipException("The description of a todo cannot be empty.");
                    }

                    TodoTask todoTask = new TodoTask(task);
                    tasks.add(todoTask);

                    System.out.println("Understood, I've added this task:");
                    System.out.println(todoTask);
                    System.out.println("The number of your tasks is: " + tasks.size() + "\n");
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
                    String deadline = parts[1];

                    DeadlineTask deadlineTask = new DeadlineTask(task, deadline);
                    tasks.add(deadlineTask);

                    System.out.println("Understood, I've added this task:");
                    System.out.println(deadlineTask);
                    System.out.println("The number of your tasks is: " + tasks.size() + "\n");
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

                    tasks.add(eventTask);
                    System.out.println("Understood, I've added this task:");
                    System.out.println(eventTask);
                    System.out.println("The number of your tasks is: " + tasks.size() + "\n");
                } else if (command.matches("delete \\d+")) {
                    String numberPart = command.replaceAll("\\D+", "");
                    int number = Integer.parseInt(numberPart);
                    System.out.println("I have deleted this task:");
                    System.out.println(deleteTask(number) + "\n");
                } else {
                    throw new MipException("I have no idea what you are talking about...");
                }
            } catch (MipException e) {
                System.out.println(e.getMessage() + "\n");
            } finally {
                saveTasks();
            }
        }
    }
}