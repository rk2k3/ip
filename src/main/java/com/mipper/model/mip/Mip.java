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

    public String greeting() {
        return ui.greeting();
    }

    // Used Chatgpt to refactor to use switch statements instead of if-else
    public String getResponse(String input) {
        Parser.CommandType command = Parser.getCommandType(input);
        try {
            switch (command) {
            case BYE:
                return ui.farewell();

            case LIST:
                return ui.listTasksMessage(tasks);

            case MARK: {
                int number = Parser.getNumber(input);
                tasks.markTask(number);
                return ui.taskMarkedMessage(tasks.getTask(number));
            }

            case UNMARK: {
                int number = Parser.getNumber(input);
                tasks.unmarkTask(number);
                return ui.taskUnmarkedMessage(tasks.getTask(number));
            }

            case EMPTY:
                return ui.emptyInputMessage();

            case TODO: {
                String description = Parser.getTodoDescription(input);
                if (description.isEmpty()) {
                    throw new MipException("The description of a todo cannot be empty.");
                }
                TodoTask todoTask = new TodoTask(description);
                tasks.addTask(todoTask);
                return ui.taskAddedMessage(todoTask) + "\n" + ui.taskQuantityMessage(tasks);
            }

            case DEADLINE: {
                DeadlineTask deadlineTask = new DeadlineTask(
                        Parser.getDeadlineDescription(input),
                        Parser.getDeadlineDue(input));
                tasks.addTask(deadlineTask);
                return ui.taskAddedMessage(deadlineTask) + "\n" + ui.taskQuantityMessage(tasks);
            }

            case EVENT: {
                EventTask eventTask = new EventTask(
                        Parser.getEventDescription(input),
                        Parser.getEventFrom(input),
                        Parser.getEventTo(input));
                tasks.addTask(eventTask);
                return ui.taskAddedMessage(eventTask) + "\n" + ui.taskQuantityMessage(tasks);
            }

            case DELETE: {
                int number = Parser.getNumber(input);
                Task deletedTask = tasks.deleteTask(number);
                return ui.taskDeletedMessage(deletedTask);
            }

            case FIND: {
                String filter = Parser.getFindFilter(input);
                return ui.listFilteredTasksMessage(tasks.filterTasksBy(filter));
            }

            default:
                throw new MipException("I have no idea what you are talking about...");
            }
        } catch (MipException e) {
            return e.getMessage();
        } finally {
            storage.saveTasks(tasks);
        }
    }

}