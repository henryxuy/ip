public class Deadline extends Task{

    protected String byTime;

    public Deadline(String description) {
        super(description);
    }

    public Deadline(String description, String byTime){
        super(description);
        this.byTime = byTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byTime + ")";
    }

}
