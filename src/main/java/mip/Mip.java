package mip;

import mip.task.DeadlineTask;
import mip.task.EventTask;
import mip.task.TaskList;
import mip.task.TodoTask;
import mip.task.Task;

public class Mip {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructor for Mip, initialises with a string filepath to storage.
     *
     * @param filePath filepath to storage file location.
     */
    public Mip(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MipException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showGreeting();
        String command;
        while (true) {
            try {
                command = ui.getInput();
                if (command.equals("bye")) {
                    ui.showGoodbye();
                    break;
                } else if (command.equals("list")) { // List out all previous commands
                    ui.showTasks(tasks);
                    System.out.println(); // Add a new line at the end
                } else if (command.matches("mark \\d+")) {
                    int number = Parser.getNumber(command);
                    tasks.markTask(number);
                    ui.showTaskMarked(tasks.getTask(number));
                    System.out.println();
                } else if (command.matches("unmark \\d+")) {
                    int number = Parser.getNumber(command);
                    tasks.unmarkTask(number);
                    ui.showTaskUnmarked(tasks.getTask(number));
                    System.out.println();
                } else if (command.isEmpty()) {
                    continue;
                } else if (command.startsWith("todo")) {
                    String description = Parser.getTodoDescription(command);

                    if (description.isEmpty()) {
                        throw new MipException("The description of a todo cannot be empty.");
                    }

                    TodoTask todoTask = new TodoTask(description);
                    tasks.addTask(todoTask);

                    ui.showTaskAdded(todoTask);
                    ui.showTaskQuantity(tasks);
                    System.out.println();
                } else if (command.startsWith("deadline")) {
                    DeadlineTask deadlineTask = new DeadlineTask(Parser.getDeadlineDescription(command),
                            Parser.getDeadlineDue(command));
                    tasks.addTask(deadlineTask);

                    ui.showTaskAdded(deadlineTask);
                    ui.showTaskQuantity(tasks);
                    System.out.println();
                } else if (command.startsWith("event")) {
                    String description = Parser.getEventDescription(command);
                    String from = Parser.getEventFrom(command);
                    String to = Parser.getEventTo(command);
                    EventTask eventTask = new EventTask(description, from, to);

                    tasks.addTask(eventTask);
                    ui.showTaskAdded(eventTask);
                    ui.showTaskQuantity(tasks);
                    System.out.println();
                } else if (command.matches("delete \\d+")) {
                    int number = Parser.getNumber(command);
                    Task deletedTask = tasks.deleteTask(number);
                    ui.showTaskDeleted(deletedTask);
                    System.out.println();
                } else {
                    throw new MipException("I have no idea what you are talking about...");
                }
            } catch (MipException e) {
                System.out.println(e.getMessage() + "\n");
            } finally {
                storage.saveTasks(tasks);
            }
        }
    }

    public static void main(String[] args) {
        new Mip("data/tasks.txt").run();
    }
}