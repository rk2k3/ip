package mip.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void markTask(int number) {
        tasks.get(number - 1).setCompleted(true);
    }

    public void unmarkTask(int number) {
        tasks.get(number - 1).setCompleted(false);
    }

    public Task deleteTask(int number) {
        return tasks.remove(number - 1);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int number) {
        return tasks.get(number - 1);
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }

}
