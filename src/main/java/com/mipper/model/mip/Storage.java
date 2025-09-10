package com.mipper.model.mip;

import com.mipper.model.mip.task.DeadlineTask;
import com.mipper.model.mip.task.EventTask;
import com.mipper.model.mip.task.Task;
import com.mipper.model.mip.task.TaskList;
import com.mipper.model.mip.task.TodoTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private File file;

    /**
     * constructor to initialise Storage object with a file specified by its path
     *
     * @param path path to intended storage file
     */
    public Storage(String path) {
        this.file = new File(path);
        File parentDir = this.file.getParentFile();

        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                System.out.println("Failed to create directory: " + parentDir);
            }
        }

        // Ensure the file exists
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }

    /**
     * Returns arraylist of task contained within storage file
     *
     * @return stored arraylist of tasks
     * @throws MipException file is not found
     */
    public ArrayList<Task> load() throws MipException {
        try (Scanner sc = new Scanner(file)) {
            ArrayList<Task> tasks = new ArrayList<>();
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 3) {
                    continue; // skip invalid lines
                }

                // Extract different components of the line
                String type = parts[0].trim();
                boolean isDone = parts[1].trim().equals("1");
                String description = parts[2].trim();
                Task task = null;

                // Creates different task objects depending on type
                switch (type) {
                case "T":
                    task = new TodoTask(description);
                    break;
                case "D":
                    String deadline = parts.length > 3 ? parts[3].trim() : "";
                    task = new DeadlineTask(description, deadline);
                    break;
                case "E":
                    String from = parts.length > 3 ? parts[3].trim() : "";
                    String to = parts.length > 4 ? parts[4].trim() : "";
                    task = new EventTask(description, from, to);
                    break;
                default:
                    System.out.println("Unknown task type: " + type);
                }

                if (task != null) {
                    task.setCompleted(isDone);
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (FileNotFoundException e) {
            throw new MipException("Storage file is not found.");
        }
    }

    /**
     * Rewrites storage file to contain entirely new set of tasks specified by tasklist parameter
     *
     * @param tasks from running mip instance
     */
    public void saveTasks(TaskList tasks) {
        if (this.file == null) {
            System.out.println("Storage file not initialized!");
            return;
        }
        ArrayList<Task> taskList = tasks.getTasks();

        try (FileWriter writer = new FileWriter(this.file, false)) { // overwrite file
            for (Task t : taskList) {
                String line = "";

                if (t instanceof TodoTask) {
                    line = String.format("T | %d | %s", t.isCompleted() ? 1 : 0, t.getTask());
                } else if (t instanceof DeadlineTask) {
                    DeadlineTask dt = (DeadlineTask) t;
                    line = String.format("D | %d | %s | %s", dt.isCompleted() ? 1 : 0, dt.getTask(), dt.getDeadline());
                } else if (t instanceof EventTask) {
                    EventTask et = (EventTask) t;
                    line = String.format("E | %d | %s | %s | %s", et.isCompleted() ? 1 : 0,
                            et.getTask(), et.getFrom(), et.getTo());
                }

                // Writes the line into the file and add a line separator
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
