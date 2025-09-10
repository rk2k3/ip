package com.mipper.model.mip;

import com.mipper.model.mip.task.DeadlineTask;
import com.mipper.model.mip.task.EventTask;
import com.mipper.model.mip.task.TaskList;
import com.mipper.model.mip.task.TodoTask;
import com.mipper.model.mip.task.Task;

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

    public Mip() {
        this("data/tasks.txt"); // Default filepath for empty constructor
    }

    public String getResponse(String input) {
        try {
            if (input.equals("bye")) {
                return ui.farewell();
            } else if (input.equals("list")) { // List out all previous commands
                return ui.listTasksMessage(tasks);
            } else if (input.matches("mark \\d+")) {
                int number = Parser.getNumber(input);
                tasks.markTask(number);
                return ui.taskMarkedMessage(tasks.getTask(number));
            } else if (input.matches("unmark \\d+")) {
                int number = Parser.getNumber(input);
                tasks.unmarkTask(number);
                return ui.taskUnmarkedMessage(tasks.getTask(number));
            } else if (input.isEmpty()) {
                return ui.emptyInputMessage();
            } else if (input.startsWith("todo")) {
                String description = Parser.getTodoDescription(input);

                if (description.isEmpty()) {
                    throw new MipException("The description of a todo cannot be empty.");
                }

                TodoTask todoTask = new TodoTask(description);
                tasks.addTask(todoTask);

                return ui.taskAddedMessage(todoTask) + "\n" + ui.taskQuantityMessage(tasks);
            } else if (input.startsWith("deadline")) {
                DeadlineTask deadlineTask = new DeadlineTask(Parser.getDeadlineDescription(input),
                        Parser.getDeadlineDue(input));
                tasks.addTask(deadlineTask);

                return ui.taskAddedMessage(deadlineTask) + "\n" + ui.taskQuantityMessage(tasks);
            } else if (input.startsWith("event")) {
                String description = Parser.getEventDescription(input);
                String from = Parser.getEventFrom(input);
                String to = Parser.getEventTo(input);
                EventTask eventTask = new EventTask(description, from, to);

                tasks.addTask(eventTask);
                return ui.taskAddedMessage(eventTask) + "\n" + ui.taskQuantityMessage(tasks);
            } else if (input.matches("delete \\d+")) {
                int number = Parser.getNumber(input);
                Task deletedTask = tasks.deleteTask(number);

                return ui.taskDeletedMessage(deletedTask);
            } else if (input.startsWith("find")) {
                String filter = Parser.getFindFilter(input);
                return ui.listFilteredTasksMessage(tasks.filterTasksBy(filter));
            } else {
                throw new MipException("I have no idea what you are talking about...");
            }
        } catch (MipException e) {
            return e.getMessage();
        } finally {
            storage.saveTasks(tasks);
        }
    }
}