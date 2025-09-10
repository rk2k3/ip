package com.mipper.model.mip.task;

public class Task {
    private boolean completed = false;
    private String task;
    private static int count = 0;


    public Task(String task) {
        this.task = task;
        count++;
    }

    public String getCompletionStatusIcon() {
        return (completed ? "[X]" : "[ ]");
    }

    public String getTask() {
        return task;
    }

    @Override
    public String toString() {
        String completionSymbol = this.getCompletionStatusIcon();
        return completionSymbol + " " + task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public static int getCount() {
        return count;
    }

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
