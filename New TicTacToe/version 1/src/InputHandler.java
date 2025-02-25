import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getValidNumber(String message, int min, int max) {
        int number;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number >= min && number <= max) return number;
            } else {
                scanner.next();
            }
            System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
        }
    }

    public boolean getValidChoice(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.next().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) return true;
            if (input.equals("no") || input.equals("n")) return false;
            System.out.println("Invalid input! Please enter 'yes' or 'no'.");
        }
    }

    public String getValidName(int playerNumber) {
        while (true) {
            System.out.print("Enter name for Player " + playerNumber + " (Max 8 characters): ");
            String name = scanner.next().trim();
            if (!name.isEmpty() && name.length() <= 8) return name;
            System.out.println("Invalid name! Try again.");
        }
    }

    public char getValidSymbol(int playerNumber) {
        while (true) {
            System.out.print("Enter symbol for Player " + playerNumber + " (A-Z): ");
            String input = scanner.next().trim().toUpperCase();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) return input.charAt(0);
            System.out.println("Invalid symbol! Try again.");
        }
    }

    //v2
    public String getValidDifficulty() {
        Scanner scanner = new Scanner(System.in);
        String difficulty;

        while (true) {
            System.out.print("Select AI difficulty (Normal/Hard): ");
            difficulty = scanner.nextLine().trim().toLowerCase();

            if (difficulty.equals("normal") || difficulty.equals("hard")) {
                return difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1); // Capitalize first letter
            } else {
                System.out.println("Invalid choice! Please enter 'Normal' or 'Hard'.");
            }
        }
    }

}


/* v1
import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getValidNumber(String message, int min, int max) {
        int number;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number >= min && number <= max) return number;
            } else {
                scanner.next();
            }
            System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
        }
    }

    public String getValidName(int playerNumber) {
        while (true) {
            System.out.print("Enter name for Player " + playerNumber + " (Max 8 characters): ");
            String name = scanner.next().trim();
            if (!name.isEmpty() && name.length() <= 8) return name;
            System.out.println("Invalid name! Try again.");
        }
    }

    public char getValidSymbol(int playerNumber) {
        while (true) {
            System.out.print("Enter symbol for Player " + playerNumber + " (A-Z): ");
            String input = scanner.next().trim().toUpperCase();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) return input.charAt(0);
            System.out.println("Invalid symbol! Try again.");
        }
    }
}
*/
