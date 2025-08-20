public class TodoTask extends Task {
    public TodoTask(String task) {
        super(task);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
