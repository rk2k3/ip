package com.mipper.model.mip.task;

public abstract class Task {
    private boolean completed = false;
    private String task;

    public Task(String task) {
        this.task = task;
    }

    /**
     * Returns a visual representation of the task's completion status.
     *
     * @return "[X]" if the task is completed, "[ ]" otherwise
     */
    public String getCompletionStatusIcon() {
        return (completed ? "[X]" : "[ ]");
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getTask() {
        return task;
    }

    /**
     * Returns a string representation of the task including
     * its completion status.
     *
     * @return formatted string representing the task
     */
    @Override
    public String toString() {
        String completionSymbol = this.getCompletionStatusIcon();
        return completionSymbol + " " + task;
    }

    /**
     * Checks whether the task is marked as completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed true to mark the task as completed, false to mark it as not completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Checks equality between this task and another object.
     * Two tasks are considered equal if they are of the same class
     * and their string representations are equal.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (this.toString().equals(o.toString())) {
            return true;
        }

        return false;
    }
}
