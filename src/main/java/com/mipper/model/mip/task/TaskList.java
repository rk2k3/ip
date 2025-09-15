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

    /**
     * Returns a list of tasks whose descriptions contain the given string.
     *
     * @param desc the substring to filter tasks by
     * @return a list of tasks matching the filter
     */
    public List<Task> filterTasksBy(String desc) {
        List<Task> filtered = tasks.stream()
                .filter(task -> task.getTask().contains(desc))
                .collect(Collectors.toList());
        return filtered;
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return the list of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the task at the given 1-based index.
     *
     * @param number the 1-based index of the task
     * @return the task at the specified index
     */
    public Task getTask(int number) {
        return tasks.get(number - 1);
    }

    /**
     * Checks whether the task list is empty.
     *
     * @return true if the list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

}
