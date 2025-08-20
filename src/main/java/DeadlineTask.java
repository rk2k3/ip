public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String task, String deadline) {
        super(task);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", deadline);
    }
}
