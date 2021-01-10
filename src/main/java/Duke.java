import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);

        printGreeting();
        echoAndExit();

    }

    private static void echoAndExit(){
        Scanner in = new Scanner(System.in);
        String tempDialogue = in.nextLine();
        while(!tempDialogue.equals("bye")){
            echoChat(tempDialogue);
            tempDialogue = in.nextLine();
        }
        echoChat("Bye. Hope to see you again soon!");

        return;
    }

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
