import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Duke {
    // String[] to save what the user has entered
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static int arrayCount = 0;
    // relative path to save the file
    private final static String LIST_PATH = "./data/taskList.txt";

    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);

        // Rm: Single Level of Abstraction Per method

        printGreeting();
        addListAndExit();

    }

    private static void addListAndExit(){
        Scanner in = new Scanner(System.in);
        String tempDialogue = "";
        while(true){
            try {
                tempDialogue = in.nextLine();
                if (tempDialogue.equals("list")) {
                    /* list out the previous tasks */
                    printList();
                } else if (tempDialogue.equals("bye")) {
                    /* save the task list when we exit */
                    saveTaskList();
                    echoChat("Bye. Hope to see you again soon!");
                    break;
                } else if (tempDialogue.startsWith("done")) {
                    doneTask(tempDialogue);
                } else if (tempDialogue.startsWith("delete")) {
                    deleteTask(tempDialogue);
                } else {
                    /* add to list and show */
                    addTask(tempDialogue);
                }
            }
            catch (DukeException ex){
                echoChat(ex.toString());
            }
        }

        return;
    }

    private static void doneTask(String tempDialogue) throws DukeException {
        String[] splitString = tempDialogue.split("done ");
        int taskIndex;

        try {
            taskIndex = Integer.parseInt(splitString[1]) - 1;
        }
        catch (NumberFormatException ex){
            // if the string cannot be parsed successfully
            throw new DukeException(DukeExceptionType.WRONG_DONE_INDEX);
        }

        if(taskIndex < 0 || taskIndex >= arrayCount){
            throw new DukeException(DukeExceptionType.WRONG_DONE_INDEX);
        }

        taskList.get(taskIndex).isDone = true;
        echoChat("Nice! I've marked this task as done: \n" +
                taskList.get(taskIndex));
    }


    private static void addTask(String taskString){
        try {
            if (taskString.startsWith("todo")) {
                addTodo(taskString);
            }
            else if (taskString.startsWith("deadline")) {
                addDeadline(taskString);
            }
            else if (taskString.startsWith("event")) {
                addEvent(taskString);
            }
            else {
                throw new DukeException(DukeExceptionType.MEANINGLESS_INPUT);
            }
        }
        catch (DukeException ex){
            echoChat(ex.toString());
        }
    }

    private static void saveTaskList() {
        File file = new File(LIST_PATH);
        File fileDirectory = new File(file.getParent());
        boolean directoryCreated = false;
        boolean fileDeleted = true;
        if(file.isDirectory()){
            // directory is not valid to write into
            System.out.println("The file path is not valid.");
            System.exit(1);
        }
        if(!fileDirectory.exists()){
            // make the directory if it does not exist
            directoryCreated = fileDirectory.mkdirs();
        }

        if(file.exists()){
            // delete the file if it exists
            fileDeleted = file.delete();
        }

        try(java.io.PrintWriter output = new java.io.PrintWriter(file);)
        {
            // write to the file
            for(Task task: taskList){
                // save format: todo | 1 | read book
                // deadline | 0 | return book | June 6th
                if(task instanceof Todo) {
                    Todo todoTask = (Todo) task;
                    int isDone = todoTask.isDone ? 1 : 0;
                    String taskString = String.format("todo | %d | %s", isDone, todoTask.description);
                    output.println(taskString);
                }
                else if(task instanceof Deadline){
                    Deadline deadlineTask = (Deadline) task;
                    int isDone = deadlineTask.isDone ? 1 : 0;
                    String taskString = String.format("deadline | %d | %s | %s", isDone, deadlineTask.description,
                            deadlineTask.byTime);
                    output.println(taskString);
                }
                else if(task instanceof Event){
                    Event eventTask = (Event) task;
                    int isDone = eventTask.isDone ? 1 : 0;
                    String taskString = String.format("event | %d | %s | %s", isDone, eventTask.description,
                            eventTask.atTime);
                    output.println(taskString);
                }
                else{
                    // merely task
                    int isDone = task.isDone ? 1 : 0;
                    String taskString = String.format("task | %d | %s", isDone, task.description);
                    output.println(taskString);
                }
            }
            output.println("EOF");
        }
        catch (FileNotFoundException ex){
            System.out.println("The file path is not valid.");
            System.exit(1);
        }
    }



    private static void addTodo(String todoString) throws DukeException{
        if(todoString.endsWith("todo")){
            throw new DukeException(DukeExceptionType.EMPTY_TODO);
        }

        int spaceIndex = todoString.indexOf(" ");
//        String taskType = todoString.substring(0, spaceIndex);
        String taskDescription = todoString.substring(spaceIndex + 1);

        taskList.add(new Todo(taskDescription));
        arrayCount++;

        echoChat("Got it. I've added this task:\n" + taskList.get(arrayCount-1) +
                    "\nNow you have " + arrayCount + " tasks in the list.");
    }

    private static void addDeadline(String deadlineString) throws DukeException{
        if(deadlineString.endsWith("deadline")){
            throw new DukeException(DukeExceptionType.EMPTY_DEADLINE);
        }
        if(!deadlineString.contains(" /")){
            throw new DukeException(DukeExceptionType.NO_TIME_DEADLINE);
        }

        String[] timeSplitString = deadlineString.split(" /");
        int spaceIndex = timeSplitString[0].indexOf(" ");
//        String taskType = timeSplitString[0].substring(0, spaceIndex);
        String taskDescription = timeSplitString[0].substring(spaceIndex + 1);
        String time = "";

        time = timeSplitString[1];
        taskList.add(new Deadline(taskDescription, time));
        arrayCount++;

        echoChat("Got it. I've added this task:\n" + taskList.get(arrayCount-1) +
                "\nNow you have " + arrayCount + " tasks in the list.");
    }

    private static void addEvent(String eventString) throws DukeException{
        if(eventString.endsWith("event")){
            throw new DukeException(DukeExceptionType.EMPTY_EVENT);
        }
        if(!eventString.contains(" /")){
            throw new DukeException(DukeExceptionType.NO_TIME_EVENT);
        }

        String[] timeSplitString = eventString.split(" /");
        int spaceIndex = timeSplitString[0].indexOf(" ");
//        String taskType = timeSplitString[0].substring(0, spaceIndex);
        String taskDescription = timeSplitString[0].substring(spaceIndex + 1);
        String time = "";

        time = timeSplitString[1];
        taskList.add(new Event(taskDescription, time));
        arrayCount++;

        echoChat("Got it. I've added this task:\n" + taskList.get(arrayCount-1) +
                "\nNow you have " + arrayCount + " tasks in the list.");
    }



//    private static void addTaskOld(String taskString){
//        // parse the task
//        try {
//            String[] timeSplitString = taskString.split(" /");
//            int spaceIndex = timeSplitString[0].indexOf(" ");
//            String taskType = timeSplitString[0].substring(0, spaceIndex);
//            String taskDescription = timeSplitString[0].substring(spaceIndex + 1);
//            String time = "";
//
//            // add the task
//            switch (taskType) {
//                case "todo":
//                    taskList.add(new Todo(taskDescription));
//                    arrayCount++;
//                    break;
//                case "deadline":
//                    time = timeSplitString[1];
//                    taskList.add(new Deadline(taskDescription, time));
//                    arrayCount++;
//                    break;
//                case "event":
//                    time = timeSplitString[1];
//                    taskList.add(new Event(taskDescription, time));
//                    arrayCount++;
//                    break;
//                default:
//                    throw new Exception("Invalid task type");
//            }
//            echoChat("Got it. I've added this task:\n" + taskList.get(arrayCount-1) +
//                    "\nNow you have " + arrayCount + " tasks in the list.");
//        }
//
//        catch (Exception ex){
//            System.out.println("Invalid task input");
//        }
//    }


    private static void deleteTask(String tempDialogue) throws DukeException {
        String[] splitString = tempDialogue.split("delete ");
        int taskIndex;

        try {
            taskIndex = Integer.parseInt(splitString[1]) - 1;
        }
        catch (NumberFormatException ex){
            // if the string cannot be parsed successfully
            throw new DukeException(DukeExceptionType.WRONG_DELETE_INDEX);
        }

        if(taskIndex < 0 || taskIndex >= arrayCount){
            throw new DukeException(DukeExceptionType.WRONG_DELETE_INDEX);
        }
        Task tempTask = taskList.get(taskIndex);
        taskList.remove(taskIndex);
        arrayCount--;
        echoChat("Noted. I've removed this task: \n" +
                tempTask +
                "\n Now you have " + arrayCount + " tasks in the list.");

    }


    private static void printList(){
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for(int i = 0; i < arrayCount; i++) {
            Task tempTask = taskList.get(i);
            System.out.println((i + 1) + ". " + tempTask);
//            System.out.println("\u2713");
//            System.out.println((i + 1) + " [" + "\u2713" +"] " + tempTask.description);
        }
        System.out.println("____________________________________________________________");
        return;
    }


//    private static void echoAndExit(){
//        Scanner in = new Scanner(System.in);
//        String tempDialogue = in.nextLine();
//        while(!tempDialogue.equals("bye")){
//            echoChat(tempDialogue);
//            tempDialogue = in.nextLine();
//        }
//        echoChat("Bye. Hope to see you again soon!");
//
//        return;
//    }

    private static void printGreeting(){
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    private static void echoChat(String string){
        System.out.println("____________________________________________________________");
        System.out.println(string);
        System.out.println("____________________________________________________________");
    }


}





