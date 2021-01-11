import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Deadline extends Task{

    // date type: yyyy-mm-dd
    protected String byTime;
    protected LocalDate byTimeDate;

    public Deadline(String description) {
        super(description);
    }

    public Deadline(String description, String byTime) throws DukeException {
        super(description);
        this.byTime = byTime;
        try {
            this.byTimeDate = LocalDate.parse(byTime);
            this.byTime = byTimeDate.format(DateTimeFormatter.ofPattern("MM d yyyy"));
        }
        catch (DateTimeParseException ex){
            throw new DukeException(DukeExceptionType.WRONG_TIME_DEADLINE);
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byTime + ")";
    }

}
