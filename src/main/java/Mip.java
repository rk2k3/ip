import java.util.Scanner;

public class Mip {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Mip\nWhat can I do for you?\n");

        Task[] tasks = new Task[100];
        String command;
        Scanner input = new Scanner(System.in);

        while (true){
            command = input.nextLine();
            if (command.equals("bye")) {
                System.out.println("Goodbye! Mip WILL see you again.");
                break;
            } else if (command.equals("list")) { // List out all  previous commands
                for (int i = 0; i < Task.getCount(); i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println(); // Add a new line at the end
            } else if (command.matches("mark \\d+")) {
                String numberPart = command.replaceAll("\\D+", "");
                int number = Integer.parseInt(numberPart);
                tasks[number - 1].setCompleted(true);
                System.out.println("Super amazing!!! I've marked this task as done:");
                System.out.println(tasks[number - 1] + "\n");
            } else if (command.matches("unmark \\d+")) {
                String numberPart = command.replaceAll("\\D+", "");
                int number = Integer.parseInt(numberPart);
                tasks[number - 1].setCompleted(false);
                System.out.println("I've marked this task as not done:");
                System.out.println(tasks[number - 1] + "\n");
            } else if (command.isEmpty()) {
                continue;
            } else {
                tasks[Task.getCount()] = new Task(command);
                System.out.println("Added: " + command + "\n");
            }
        }
    }
}