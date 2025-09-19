package com.mipper.model.mip;

import com.mipper.model.mip.task.Task;
import com.mipper.model.mip.task.TaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    /**
     * Returns the greeting message when the application starts.
     *
     * @return greeting message string
     */
    public String greeting() {
        return "Hello! I'm Mip\nWhat can I do for you?";
    }

    /**
     * Returns the farewell message when the application exits.
     *
     * @return farewell message string
     */
    public String farewell() {
        return "Goodbye! Mip WILL see you again.";
    }

    /**
     * Returns a message indicating a task was added.
     *
     * @param task the task that was added
     * @return formatted task-added message
     */
    public String taskAddedMessage(Task task) {
        return "Understood, I've added this task:" + "\n" + task;
    }

    /**
     * Returns a message indicating a task was deleted.
     *
     * @param task the task that was deleted
     * @return formatted task-deleted message
     */
    public String taskDeletedMessage(Task task) {
        return "Understood, I've deleted this task:" + "\n" + task;
    }

    /**
     * Returns a message indicating a task was marked as completed.
     *
     * @param task the task that was marked
     * @return formatted task-marked message
     */
    public String taskMarkedMessage(Task task) {
        return "Understood, I've marked this task as done" + "\n" + task;
    }

    /**
     * Returns a message indicating a task was unmarked as completed.
     *
     * @param task the task that was unmarked
     * @return formatted task-unmarked message
     */
    public String taskUnmarkedMessage(Task task) {
        return "Understood, I've unmarked this task"  + "\n" + task;
    }

    /**
     * Returns a message showing the number of tasks in the list.
     *
     * @param tasks the TaskList to count
     * @return formatted task quantity message
     */
    public String taskQuantityMessage(TaskList tasks) {
        return "The number of your tasks is: " + tasks.size();
    }

    /**
     * Returns a formatted string of all tasks in the list.
     *
     * @param tasks the TaskList to display
     * @return string representation of all tasks, or a message if empty
     */
    public String listTasksMessage(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "Your task list is empty. Congrats!";
        }

        String tasksString = "";
        ArrayList<Task> list = tasks.getTasks();
        for (int i = 0; i < list.size(); i++) {
            tasksString = tasksString + (i + 1) + ". " + list.get(i) + "\n";
        }
        return tasksString;
    }

    /**
     * Returns a formatted string of filtered tasks matching a criteria.
     *
     * @param tasks the list of filtered tasks
     * @return string representation of filtered tasks, or a message if none found
     */
    public String listFilteredTasksMessage(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "no matches found.";
        } else {
            String tasksString = "";
            for (int i = 0; i < tasks.size(); i++) {
                tasksString = tasksString + (i + 1) + ". " + tasks.get(i) + "\n";
            }
            return tasksString;
        }
    }

    /**
     * Returns an error message to display to the user.
     *
     * @param message the error message
     * @return formatted error message
     */
    public String errorMessage(String message) {
        return message;
    }

    /**
     * Prints a loading error message to the console.
     */
    public void showLoadingError() {
        System.out.println("Loading error.");
    }

    /**
     * Returns a message indicating that the user input was empty.
     *
     * @return empty input warning message
     */
    public String emptyInputMessage() {
        return "Your message is empty. why u not saying anything";
    }
}