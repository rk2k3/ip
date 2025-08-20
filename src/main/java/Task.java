public class Task {
    private boolean completed = false;
    private String task;

    public Task(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
