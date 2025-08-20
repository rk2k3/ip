import java.util.Scanner;

public class Mip {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Mip\nWhat can I do for you?\n");

        String[] commandList = new String[100];
        int commandIndex = 0;
        String command;
        Scanner input = new Scanner(System.in);
        while (true){
            command = input.nextLine();
            if (command.equals("bye")) {
                System.out.println("Goodbye! Mip WILL see you again.");
                break;
            } else if (command.equals("list")) { // List out all  previous commands
                for (int i = 0; i < commandIndex; i++) {
                    System.out.println((i + 1) + ". " + commandList[i]);
                }
                System.out.println(); // Add a new line at the end
            } else {
                commandList[commandIndex] = command;
                commandIndex++;
                System.out.println("Added: " + command + "\n");
            }
        }
    }
}