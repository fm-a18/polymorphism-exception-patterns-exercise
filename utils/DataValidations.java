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
            } else if (!input.matches("^ABC\\d{4}$")){
                System.out.println("Error: Tracking Number must only contain alphanumeric"); //Fix, clarify if 3 alpha(a-z) or abc
            }
            else if (manager.keyExists(input)) {
                System.out.println("Error: Tracking Number already exists.");
            } else {
                isValidated = true;
            }
        }
        return input;
    }

    public static String searchForTrackingNumber() {
        boolean isValidated = false;
        String input = "";

        while (!isValidated) {
            System.out.print("Tracking Number: ");
            try {
                input = sc.nextLine().trim().toUpperCase();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error: No input available. Please try again.");
                continue;
            }

            if (input.isEmpty()) {
                System.out.println("Error: Item ID cannot be empty.");
            } else if (!input.matches("^(CL|EL|EN)\\d{3}$")) {
                System.out.println("Error: Invalid Item ID format. Please follow the specified Item ID format.");
            } else {
                isValidated = true;
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
                System.out.println("Error: Item Name cannot be empty.");
            } else if (!input.matches("^[\\p{L}][\\p{L} .'-]{0,49}$")) { //double check if appropriate
                System.out.println("Error: Name must be up to 50 characters (letters, spaces, . ' - only).");
            } else {
                isValidated = true;
            }
        }
        return input;
    }

    public static double validateWeight(double maxWeight) {
        boolean isValidated = false;
        double value = 0;

        while (!isValidated) {
            System.out.print("Weight (kg): ");
            String input = sc.nextLine().trim();

            if (!input.matches("\\d+(\\.\\d+)?")) {
                System.out.println("Error: Please enter a valid number (e.g., 25.20, 50.59).");
                continue;
            }

            try {
                value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Error: Weight must be higher than 0kg.");
                } else if (value > maxWeight){
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

    public static int validateInt(String prompt) {
        int value = 0;
        boolean isValidated = false;

        while (!isValidated) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine().trim();

            int MAX_QUANTITY = 500;
            if (!input.matches("[1-9][0-9]*")) {
                System.out.printf("Error: Please enter whole numbers only from 1-%,d.\n", MAX_QUANTITY);
                continue;
            }

            try {
                value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.printf("Error: %s must be greater than 0.\n", prompt);
                } else if (value > MAX_QUANTITY) {
                    System.out.printf("Error: %s must not exceed %,d.%n", prompt, MAX_QUANTITY);
                } else {
                    isValidated = true;
                }
            } catch (NumberFormatException e) {
                System.out.printf("Error: Please enter a valid number from 1-%,d.\n", MAX_QUANTITY);
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

    public static char charChoiceValidation(String prompt, char... choices) {
        boolean isValidated = false;
        char input = ' ';
        while (!isValidated) {
            try {
                System.out.print(prompt + ": ");
                String line = sc.nextLine().trim().toUpperCase();
                if (line.length() != 1) {
                    printValidChoices(choices);
                    continue;
                }
                input = line.charAt(0);
                boolean found = false;
                for (char choice : choices) {
                    if (input == Character.toUpperCase(choice)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    isValidated = true;
                } else {
                    printValidChoices(choices);
                }
            } catch (Exception e) {
                printValidChoices(choices);
            }
        }
        return input;
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

    public static void printValidChoices(char... choices) { //char values
        System.out.print("Error: Please select only from (");
        for (int i = 0; i < choices.length; i++) {
            System.out.print(Character.toUpperCase(choices[i]));
            if (i < choices.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(").");
    }

}
