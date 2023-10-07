package lastpencil;

import java.util.Random;
import java.util.Scanner;

public class Main {

    // Main function - entry point of the program
    public static void main(String[] args) {
        // Initialize Scanner and Random objects
        Scanner userInputScanner = new Scanner(System.in);
        Random random = new Random();

        // Ask the user for the number of pencils
        System.out.print("How many pencils would you like to use: ");
        String userInput = userInputScanner.nextLine();

        // Validate the number of pencils
        while (!isNumeric(userInput) || Integer.parseInt(userInput) <= 0) {
            if (!isNumeric(userInput)) {
                System.out.println("The number of pencils should be numeric");
            } else {
                System.out.println("The number of pencils should be positive");
            }
            userInput = userInputScanner.nextLine();
        }

        int totalPencils = Integer.parseInt(userInput);

        // Ask the user who will go first
        System.out.print("Who will be the first (John, Jack): ");
        String currentPlayer = userInputScanner.nextLine();

        // Validate the player's name
        while (!currentPlayer.equals("John") && !currentPlayer.equals("Jack")) {
            System.out.println("Choose between 'John' and 'Jack'");
            currentPlayer = userInputScanner.nextLine();
        }

        // Main game loop
        while (totalPencils > 0) {
            System.out.println("|".repeat(totalPencils));

            if ("John".equals(currentPlayer)) {
                System.out.println("John's turn!");
                userInput = userInputScanner.nextLine();

                // Validate the number of pencils John wants to take
                while (!isNumeric(userInput) || Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 3) {
                    System.out.println("Possible values: '1', '2', or '3'");
                    userInput = userInputScanner.nextLine();
                }

                int pencilsTaken = Integer.parseInt(userInput);

                // Make sure John doesn't take more pencils than available
                if (pencilsTaken > totalPencils) {
                    System.out.println("Too many pencils were taken");
                    continue;
                }

                totalPencils -= pencilsTaken;
            } else {
                // Bot's move
                int botMove = botStrategy(totalPencils, random);
                System.out.println("Jack's turn:");
                if (totalPencils == 1) {
                    botMove = totalPencils;
                }
                System.out.println(botMove);
                totalPencils -= botMove;
            }

            // Check for a winner
            if (totalPencils <= 0) {
                System.out.println(currentPlayer.equals("John") ? "Jack won!" : "John won!");
                break;
            }

            // Swap players
            currentPlayer = currentPlayer.equals("John") ? "Jack" : "John";
        }

        // Close the scanner to free resources
        userInputScanner.close();
    }

    // Bot's strategy to choose the number of pencils to take
    public static int botStrategy(int totalPencils, Random random) {
        if (totalPencils % 4 == 0) return 3;
        if (totalPencils % 4 == 1) return random.nextInt(3) + 1;
        if (totalPencils % 4 == 2) return 1;
        if (totalPencils % 4 == 3) return 2;

        return 1;  // Fallback, should never reach here
    }

    // Function to check if a string is numeric
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
