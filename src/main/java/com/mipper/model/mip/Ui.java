package com.mipper.model.mip;

import com.mipper.model.mip.task.Task;
import com.mipper.model.mip.task.TaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    public String greeting() {
        return "Hello! I'm Mip\nWhat can I do for you?";
    }

    public String farewell() {
        return "Goodbye! Mip WILL see you again.";
    }

    public String taskAddedMessage(Task task) {
        return "Understood, I've added this task:" + "\n" + task;
    }

    public String taskDeletedMessage(Task task) {
        return "Understood, I've deleted this task:" + "\n" + task;
    }

    public String taskMarkedMessage(Task task) {
        return "Understood, I've marked this task as done" + "\n" + task;
    }

    public String taskUnmarkedMessage(Task task) {
        return "Understood, I've unmarked this task"  + "\n" + task;
    }

    public String taskQuantityMessage(TaskList tasks) {
        return "The number of your tasks is: " + tasks.size();
    }

    public String listTasksMessage(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "Your task list is empty. Congrats!";
        }

        String tasksString = "";
        ArrayList<Task> list = tasks.getTasks();
        for (int i = 0; i < list.size(); i++) {
            tasksString = tasksString + (i + 1) + "." + list.get(i) + "\n";
        }
        return tasksString;
    }

    public String listFilteredTasksMessage(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "no matches found.";
        } else {
            String tasksString = "";
            for (int i = 0; i < tasks.size(); i++) {
                tasksString = tasksString + (i + 1) + "." + tasks.get(i) + "\n";
            }
            return tasksString;
        }
    }

    public String errorMessage(String message) {
        return message;
    }

    public void showLoadingError() {
        System.out.println("Loading error.");
    }

    public String emptyInputMessage() {
        return "Your message is empty. why u not saying anything";
    }
}