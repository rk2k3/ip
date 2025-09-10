package com.mipper.model.mip.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mipper.model.mip.MipException;

public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * constructor that initialises tasklist with an arraylist of tasks
     *
     * @param tasks arraylist of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * empty constructor that initialises tasklist empty arraylist of tasks
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * marks the task at the specified index as completed
     *
     * @param number index of task to mark as completed
     */
    public void markTask(int number) {
        tasks.get(number - 1).setCompleted(true);
    }

    /**
     * marks the task at the specified index as not completed
     *
     * @param number index of task to mark as not completed
     */
    public void unmarkTask(int number) {
        tasks.get(number - 1).setCompleted(false);
    }

    /**
     * deletes the task from the list at the specified index
     *
     * @param number index of task to delete
     */
    public Task deleteTask(int number) {
        return tasks.remove(number - 1);
    }

    /**
     * adds a task to the list
     *
     * @param task to add to list
     * @return returns true if task is successfully added
     */
    public void addTask(Task task) throws MipException {
        for (Task t : tasks) {
            if (task.equals(t)) {
                throw new MipException("Task already exists, aborting...");
            }
        }

        tasks.add(task);
    }

    public List<Task> filterTasksBy(String desc) {
        List<Task> filtered = tasks.stream()
                .filter(task -> task.getTask().contains(desc))
                .collect(Collectors.toList());

        return filtered;
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
