public class DukeException extends Exception{
//    private int exceptionType = 0;
    private DukeExceptionType exceptionType;
    private String description = "";

//    // use const to denote exception types
//    public static final int MEANINGLESS_INPUT = 1;
//    public static final int EMPTY_TODO = 2;
//    public static final int EMPTY_DEADLINE = 3;
//    public static final int EMPTY_EVENT = 4;
//    public static final int NO_TIME_DEADLINE = 5;
//    public static final int NO_TIME_EVENT = 6;
//    public static final int WRONG_DONE_INDEX = 7;

    public DukeException(DukeExceptionType type){
        this.exceptionType = type;
        switch (this.exceptionType){
            case MEANINGLESS_INPUT:
                this.description = "OOPS!!! I'm sorry, but I don't know what that means :-(";
                break;
            case EMPTY_TODO:
                this.description = "OOPS!!! The description of a todo cannot be empty.";
                break;
            case EMPTY_DEADLINE:
                this.description = "OOPS!!! The description of a deadline cannot be empty.";
                break;
            case EMPTY_EVENT:
                this.description = "OOPS!!! The description of a event cannot be empty.";
                break;
            case NO_TIME_DEADLINE:
                this.description = "OOPS!!! The time of a deadline cannot be empty.";
                break;
            case NO_TIME_EVENT:
                this.description = "OOPS!!! The time of a event cannot be empty.";
                break;
            case WRONG_DONE_INDEX:
                this.description = "OOPS!!! The done index is wrong.";
                break;
            case WRONG_DELETE_INDEX:
                this.description = "OOPS!!! The delete index is wrong.";
                break;
            case WRONG_TIME_DEADLINE:
                this.description = "OOPS!!! The time format of deadline is wrong.";
            case WRONG_TIME_EVENT:
                this.description = "OOPS!!! The time format of event is wrong.";
            default:
                break;
        }
    }

    @Override
    public String toString(){
        return this.description;
    }

}

enum DukeExceptionType{
    MEANINGLESS_INPUT, EMPTY_TODO, EMPTY_DEADLINE, EMPTY_EVENT,
    NO_TIME_DEADLINE, NO_TIME_EVENT, WRONG_DONE_INDEX, WRONG_DELETE_INDEX,
    WRONG_TIME_DEADLINE, WRONG_TIME_EVENT
}

