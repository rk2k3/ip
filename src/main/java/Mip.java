import java.util.Scanner;

public class Mip {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Mip\nWhat can I do for you?\n\n");

        String command;
        Scanner input = new Scanner(System.in);
        while (true){
            command = input.nextLine();
            if (command.equals("bye")) {
                System.out.println("Goodbye! Mip WILL see you again.");
                break;
            } else {
                System.out.println(command + "\n");
            }
        }
    }
}