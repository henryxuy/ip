import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Event extends Task {

    protected String atTime;
    protected LocalDate atTimeDate;

    public Event(String description) {
        super(description);
    }

    public Event(String description, String atTime) throws DukeException {
        super(description);
        this.atTime = atTime;
        try {
            this.atTimeDate = LocalDate.parse(atTime);
            this.atTime = atTimeDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        }
        catch (DateTimeParseException ex){
            throw new DukeException(DukeExceptionType.WRONG_TIME_EVENT);
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + atTime + ")";
    }
}
