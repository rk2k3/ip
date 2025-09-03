package mip.task;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mipper.model.mip.task.Task;
import com.mipper.model.mip.task.TaskList;

public class TaskListTest {

    @Test
    public void addTask_task_arrayListIncrease() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.addTask(new Task("test"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_number_arrayListDecrease() {
        ArrayList<Task> al = new ArrayList<>();
        al.add(new Task("test"));
        TaskList taskList = new TaskList(al);
        taskList.deleteTask(1);
        assertEquals(0, taskList.size());
    }
}
