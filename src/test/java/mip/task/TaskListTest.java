package mip.task;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.mipper.model.mip.MipException;
import com.mipper.model.mip.task.Task;
import com.mipper.model.mip.task.TaskList;
import com.mipper.model.mip.task.TodoTask;

public class TaskListTest {

    @Test
    public void addTask_task_arrayListIncrease() {
        TaskList taskList = new TaskList(new ArrayList<>());
        try {
            taskList.addTask(new TodoTask("test"));
        } catch (MipException e) {
            fail();
        }
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_number_arrayListDecrease() {
        ArrayList<Task> al = new ArrayList<>();
        al.add(new TodoTask("test"));
        TaskList taskList = new TaskList(al);
        taskList.deleteTask(1);
        assertEquals(0, taskList.size());
    }
}
