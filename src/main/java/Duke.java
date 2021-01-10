import java.util.Scanner;

public class Duke {
    // String[] to save what the user has entered
    private static Task[] taskList = new Task[100];
    private static int arrayCount = 0;

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
            tempDialogue = in.nextLine();
            if(tempDialogue.equals("list")){
                /* list out the previous tasks */
                printList();
            }
            else if(tempDialogue.equals("bye")){
                echoChat("Bye. Hope to see you again soon!");
                break;
            }
            else if(tempDialogue.startsWith("done")){
                doneTask(tempDialogue);
            }
            else {
                /* add to list and show */
                addTask(tempDialogue);
            }
        }

        return;
    }

    private static void doneTask(String tempDialogue) {
        String[] splitString = tempDialogue.split("done ");
        int taskIndex;

        try {
            taskIndex = Integer.parseInt(splitString[1]) - 1;
        }
        catch (NumberFormatException ex){
            // if the string cannot be parsed successfully
            System.out.println("Invalid done string.");
            return;
        }

        if(taskIndex < 0 || taskIndex >= arrayCount){
            System.out.println("Wrong done index.");
            return;
        }

        taskList[taskIndex].isDone = true;
        echoChat("Nice! I've marked this task as done: \n" +
                "[" + taskList[taskIndex].getStatusIcon() + "]" + taskList[taskIndex].description);
    }


    private static void addTask(String taskString){
        // parse the task
        try {
            String[] timeSplitString = taskString.split(" /");
            int spaceIndex = timeSplitString[0].indexOf(" ");
            String taskType = timeSplitString[0].substring(0, spaceIndex);
            String taskDescription = timeSplitString[0].substring(spaceIndex + 1);
            String time = "";

            // add the task
            switch (taskType) {
                case "todo":
                    taskList[arrayCount++] = new Todo(taskDescription);
                    break;
                case "deadline":
                    time = timeSplitString[1];
                    taskList[arrayCount++] = new Deadline(taskDescription, time);
                    break;
                case "event":
                    time = timeSplitString[1];
                    taskList[arrayCount++] = new Event(taskDescription, time);
                    break;
                default:
                    throw new Exception("Invalid task type");
            }
            echoChat("Got it. I've added this task:\n" + taskList[arrayCount-1] +
                    "\nNow you have " + arrayCount + " tasks in the list.");
        }

        catch (Exception ex){
            System.out.println("Invalid task input");
        }
    }




    private static void printList(){
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for(int i = 0; i < arrayCount; i++) {
            Task tempTask = taskList[i];
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





