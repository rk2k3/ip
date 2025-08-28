package mip;

import mip.task.Task;
import mip.task.TaskList;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static Scanner input = new Scanner(System.in);

    public String getInput() {
        return input.nextLine();
    }

    public void showGreeting() {
        System.out.println("Hello! I'm Mip\nWhat can I do for you?");
    }

    public void showGoodbye() {
        System.out.println("Goodbye! Mip WILL see you again.");
    }

    public void showTaskAdded(Task task) {
        System.out.println("Understood, I've added this task:");
        System.out.println(task);
    }

    public void showTaskDeleted(Task task) {
        System.out.println("I have deleted this task:");
        System.out.println(task);
    }

    public void showTaskMarked(Task task) {
        System.out.println("Super amazing!!! I've marked this task as done:");
        System.out.println(task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("I've marked this task as not done:");
        System.out.println(task);
    }

    public void showTaskQuantity(TaskList tasks) {
        System.out.println("The number of your tasks is: " + tasks.size());
    }

    public void showTask(Task task) {
        System.out.println(task);
    }

    public void showTasks(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty.");
            return;
        }
        ArrayList<Task> list = tasks.getTasks();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i));
        }
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println("Loading error.");
    }
}