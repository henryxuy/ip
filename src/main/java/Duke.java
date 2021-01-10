import java.util.Scanner;

public class Duke {
    // String[] to save what the user has entered
    private static String[] textList = new String[100];
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
            else {
                /* add to list and show (happy path) */
                textList[arrayCount++] = tempDialogue;
                echoChat("added: " + tempDialogue);
            }
        }
        
        return;
    }

    private static void printList(){
        System.out.println("____________________________________________________________");
        for(int i = 0; i < arrayCount; i++) {
            System.out.println((i + 1) + ". " + textList[i]);
        }
        System.out.println("____________________________________________________________");
        return;
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
