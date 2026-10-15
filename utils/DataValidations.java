package utils;

import service.ParcelManager;

import java.util.Scanner;


public class DataValidations {
    public static Scanner sc = new Scanner(System.in);

    public static String validateTrackingNumber(ParcelManager manager) {
        boolean isValidated = false;
        String input = "";

        while (!isValidated) {
            System.out.print("Tracking Number (ABC0001): ");
            try {
                input = sc.nextLine().trim().toUpperCase();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error: No input available. Please try again.");
                continue;
            }

            if (input.isEmpty()) {
                System.out.println("Error: Tracking Number cannot be empty.");
            } else if (!input.matches("^ABC\\d{4}$")) {
                System.out.println("Error: Tracking Number must follow the format ABC followed by any 4 digits.");
            } else if (manager.keyExists(input)) {
                System.out.println("Error: Tracking Number already exists.");
            } else {
                isValidated = true;
            }
        }
        return input;
    }

    public static String searchForTrackingNumber() {
        String input = "";

        while (input.isEmpty()) {
            System.out.print("Tracking Number (e.g., ABC0001): ");
            try {
                input = sc.nextLine().trim().toUpperCase();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error: No input available. Please try again.");
                continue;
            }

            if (input.isEmpty()) {
                System.out.println("Error: Tracking Number cannot be empty.");
            }
        }
        return input;
    }

    public static String validateName() {
        boolean isValidated = false;
        String input = "";

        while (!isValidated) {
            System.out.print("Receiver Name: ");
            try {
                input = sc.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error: No input available. Please try again.");
                continue;
            }

            if (input.isEmpty()) {
                System.out.println("Error: Receiver Name cannot be empty.");
            } else if (!input.matches("^\\p{L}[\\p{L} .'-]{0,49}$")) {
                System.out.println("Error: Receiver Name must be up to 50 characters (letters, spaces, . ' - only).");
            } else {
                isValidated = true;
            }
        }
        return input;
    }

    public static String validateText(String prompt) {
        String input = "";

        while (input.isEmpty()) {
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.printf("Error: %s cannot be empty.%n", prompt);
            } else if (input.length() > 100) {
                System.out.printf("Error: %s must be 100 characters or fewer.%n", prompt);
                input = "";
            }
        }
        return input;
    }

    public static double validateWeight(double maxWeight) {
        boolean isValidated = false;
        double value = 0;

        while (!isValidated) {
            System.out.printf("Weight (Max: %.2fkg): ", maxWeight);
            String input = sc.nextLine().trim();

            if (!input.matches("\\d+(\\.\\d+)?")) {
                System.out.println("Error: Please enter a valid number (e.g., 25.20, 50.59).");
                continue;
            }

            try {
                value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Error: Weight must be higher than 0kg.");
                } else if (value > maxWeight) {
                    System.out.printf("Error: Weight must not exceed %,.2fkg.\n", maxWeight);
                } else {
                    isValidated = true;
                }
            } catch (NumberFormatException e) {
                System.out.printf("Error: Please enter a valid number (e.g., 25.20, 50.59) from 0.1kg-%,.2fkg.\n", maxWeight);
            }
        }
        return value;
    }

    public static int validateInt(String prompt, int min, int max) {
        int value = 0;
        boolean isValidated = false;

        while (!isValidated) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.printf("Error: Please enter a whole number from %d to %d.%n", min, max);
                continue;
            }

            try {
                value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.printf("Error: Please enter a whole number from %d to %d.%n", min, max);
                } else {
                    isValidated = true;
                }
            } catch (NumberFormatException e) {
                System.out.printf("Error: Please enter a whole number from %d to %d.%n", min, max);
            }
        }
        return value;
    }

    public static int intChoiceValidation(String prompt, int... choices) {
        int value = 0;
        boolean isValidated = false;
        while (!isValidated) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine().trim();

            if (!input.matches("[1-9][0-9]*")) {
                printValidChoices(choices);
                continue;
            }

            try {
                value = Integer.parseInt(input);
                boolean found = false;
                for (int choice : choices) {
                    if (value == choice) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    isValidated = true;
                } else {
                    printValidChoices(choices);
                }
            } catch (NumberFormatException e) {
                printValidChoices(choices);
            }
        }
        return value;
    }

    public static void printValidChoices(int... choices) { //int values
        System.out.print("Error: Please select only from (");
        for (int i = 0; i < choices.length; i++) {
            System.out.print(choices[i]);
            if (i < choices.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(").");
    }


}
