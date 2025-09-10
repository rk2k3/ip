package mip.task;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.mipper.model.mip.MipException;
import com.mipper.model.mip.task.Task;
import com.mipper.model.mip.task.TaskList;

public class TaskListTest {

    @Test
    public void addTask_task_arrayListIncrease() {
        TaskList taskList = new TaskList(new ArrayList<>());
        try {
            taskList.addTask(new Task("test"));
        } catch (MipException e) {
            fail();
        }
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
