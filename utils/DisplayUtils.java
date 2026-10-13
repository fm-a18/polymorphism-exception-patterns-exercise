package utils;

import model.Parcel;

import java.util.Collection;

public class DisplayUtils {
    public static final int TABLE_WIDTH = 100;
    public static final int MENU_WIDTH = 40;
    private static final String PARCEL_FORMAT = "%-11s | %-8s | %-16s | %-12s | %-9s | %-14s | %-9s%n";

    private static void centeredTitle(String tableName, int width) {
        System.out.println("=".repeat(width));
        int padding = (width - tableName.length()) / 2;
        System.out.println(" ".repeat(Math.max(0, padding)) + tableName);
        System.out.println("=".repeat(width));
    }

    public static void tableHeader(String tableName) {
        centeredTitle(tableName, TABLE_WIDTH);

        System.out.printf(
                PARCEL_FORMAT,
                "Tracking No", "Type", "Receiver",
                "Chg. Weight", "Service",
                "Delivery Fee", "Status"
        );

        System.out.println("-".repeat(TABLE_WIDTH));
    }

    public static void displayTable(Collection<Parcel> parcels) {
        if (parcels.isEmpty()) {
            System.out.println("No items found.");
            System.out.println("-".repeat(TABLE_WIDTH));
            return;
        }

        for (Parcel parcel : parcels) {
            System.out.printf(
                    PARCEL_FORMAT,
                    parcel.getTrackingNumber(),
                    parcel.getParcelType(),
                    parcel.getReceiverName(),
                    String.format("%d kg", (int) Math.ceil(parcel.getChargeableWeight())),
                    parcel.getDeliveryStrategy().getName(),
                    String.format("Php %,.2f", parcel.getDeliveryFee()),
                    parcel.getParcelStatus()
            );

            System.out.println("-".repeat(TABLE_WIDTH));
        }
    }

    public static void printSummaryBox(String tableName, String[][] rows) {
        int labelWidth = 0;
        for (String[] row : rows) {
            labelWidth = Math.max(labelWidth, row[0].length());
        }

        centeredTitle(tableName, MENU_WIDTH);
        for (String[] row : rows) {
            System.out.printf(" %-" + labelWidth + "s : %s%n", row[0], row[1]);
        }
        System.out.println("=".repeat(MENU_WIDTH));
    }

    public static void printMenu(String tableName, String... options) {
        centeredTitle(tableName, MENU_WIDTH);

        for (int i = 0; i < options.length; i++) {
            System.out.printf(" [%d] %s%n", i + 1, options[i]);
        }
        System.out.println("=".repeat(MENU_WIDTH));
    }

    public static void printMenuInput(String tableName, String... options) {
        centeredTitle(tableName, MENU_WIDTH);

        for (String option : options) {
            System.out.printf(" • %s%n", option);
        }
        System.out.println("=".repeat(MENU_WIDTH));
    }
}
