import exception.*;
import model.*;
import service.ParcelManager;
import strategy.DeliveryStrategy;
import strategy.ExpressDelivery;
import strategy.SameDayDelivery;
import strategy.StandardDelivery;
import utils.DataValidations;
import utils.DisplayUtils;


public class Main {
    private static final ParcelManager manager = ParcelManager.getInstance();
    private static final int MAX_DIMENSION_CM = 100;

    private static DeliveryStrategy selectDeliveryStrategy() {
        DisplayUtils.printMenu("DELIVERY SERVICE",
                "Standard Delivery",
                "Express Delivery",
                "Same Day Delivery");
        int deliveryServiceChoice = DataValidations.intChoiceValidation("Select Option", 1, 2, 3);

        return switch (deliveryServiceChoice) {
            case 1 -> new StandardDelivery();
            case 2 -> new ExpressDelivery();
            case 3 -> new SameDayDelivery();
            default -> throw new IllegalStateException("Error: Unexpected Delivery Service choice: " + deliveryServiceChoice);
        };
    }

    private static Parcel inputDocument(String trackingNumber, String receiverName, double weight)
            throws InvalidInputException {
        int numOfPages = DataValidations.validateInt("Number of Pages (1-100)", 1, 100);
        return new Document(trackingNumber, receiverName, weight, numOfPages);
    }

    private static Parcel inputBox(String trackingNumber, String receiverName, double weight)
            throws InvalidInputException {
        int length = DataValidations.validateInt("Length (1-" + MAX_DIMENSION_CM + " cm)", 1, MAX_DIMENSION_CM);
        int width = DataValidations.validateInt("Width (1-" + MAX_DIMENSION_CM + " cm)", 1, MAX_DIMENSION_CM);
        int height = DataValidations.validateInt("Height (1-" + MAX_DIMENSION_CM + " cm)", 1, MAX_DIMENSION_CM);
        return new Box(trackingNumber, receiverName, weight, length, width, height);
    }

    private static Parcel inputFragile(String trackingNumber, String receiverName, double weight)
            throws InvalidInputException {
        int length = DataValidations.validateInt("Length (1-" + MAX_DIMENSION_CM + " cm)", 1, MAX_DIMENSION_CM);
        int width = DataValidations.validateInt("Width (1-" + MAX_DIMENSION_CM + " cm)", 1, MAX_DIMENSION_CM);
        int height = DataValidations.validateInt("Height (1-" + MAX_DIMENSION_CM + " cm)", 1, MAX_DIMENSION_CM);
        String handlingNote = DataValidations.validateText("Handling Note");
        return new Fragile(trackingNumber, receiverName, weight, length, width, height, handlingNote);
    }

    private static void addParcel() {
        DisplayUtils.printMenu("ADD PARCEL",
                "Document",
                "Box",
                "Fragile");
        int parcelTypeChoice = DataValidations.intChoiceValidation("Select Option", 1, 2, 3);


        double maxWeight = switch (parcelTypeChoice) {
            case 1 -> 0.5;
            case 2 -> 50;
            case 3 -> 30;
            default -> throw new IllegalStateException("Cannot determine maximum weight for parcel type: " + parcelTypeChoice);
        };

        String trackingNumber = DataValidations.validateTrackingNumber(manager);
        String receiverName = DataValidations.validateName();
        double weight = DataValidations.validateWeight(maxWeight);

        Parcel parcel;
        try {
            parcel = switch (parcelTypeChoice) {
                case 1 -> inputDocument(trackingNumber, receiverName, weight);
                case 2 -> inputBox(trackingNumber, receiverName, weight);
                case 3 -> inputFragile(trackingNumber, receiverName, weight);
                default -> throw new IllegalStateException("Unexpected parcel type choice: " + parcelTypeChoice);
            };
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        boolean isServiceSet = false;
        while (!isServiceSet) {
            try {
                parcel.setDeliveryStrategy(selectDeliveryStrategy());
                isServiceSet = true;
            } catch (SameDayNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            manager.addParcel(parcel);
        } catch (DuplicateTrackingException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Parcel added successfully!");
        DisplayUtils.printSummaryBox("PARCEL ADDED", new String[][]{
                {"Parcel Type", parcel.getParcelType()},
                {"Tracking Number", parcel.getTrackingNumber()},
                {"Receiver Name", parcel.getReceiverName()},
                {"Weight", String.format("%.2f kg", parcel.getWeight())},
                {"Additional Information:", parcel.getAdditionalInfo()},
                {"Delivery Service", parcel.getDeliveryStrategy().getName()},
                {"Delivery Status", String.valueOf(parcel.getParcelStatus())},
                {"Delivery Fee", String.format("Php %,.2f", parcel.getDeliveryFee())}
        });
    }

    private static void viewAllParcels() {
        DisplayUtils.tableHeader("ALL PARCELS");
        DisplayUtils.displayTable(manager.getAll());
    }

    private static void changeDeliveryService() {
        if (manager.getAll().isEmpty()) {
            System.out.println("No packages available!");
            return;
        }
        String searchTrackingNum = DataValidations.searchForTrackingNumber();

        try {
            Parcel parcel = manager.findParcel(searchTrackingNum);

            if (parcel.getParcelStatus() == ParcelStatus.DELIVERED) {
                System.out.println("Parcel already delivered!");
                return;
            }

            String oldStrategy = parcel.getDeliveryStrategy().getName();

            DeliveryStrategy newStrategy = selectDeliveryStrategy();
            if (newStrategy.getName().equals(oldStrategy)) {
                System.out.printf("Delivery Service is already %s. No changes have been made.\n", newStrategy.getName());
                return;
            }

            try {
                parcel.setDeliveryStrategy(newStrategy);
                System.out.println("Delivery service changed!");
                DisplayUtils.printSummaryBox("DELIVERY SERVICE CHANGED", new String[][]{
                        {"Tracking Number", parcel.getTrackingNumber()},
                        {"Old Deliver Service", oldStrategy},
                        {"New Deliver Service", parcel.getDeliveryStrategy().getName()},
                        {"Delivery Fee", String.format("Php %,.2f", parcel.getDeliveryFee())}
                });
            } catch (SameDayNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } catch (ParcelNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void markAsDelivered() {
        if (manager.getAll().isEmpty()) {
            System.out.println("No packages available!");
            return;
        }

        String searchTrackingNum = DataValidations.searchForTrackingNumber();
        try {
            Parcel parcel = manager.findParcel(searchTrackingNum);
            parcel.setStatusAsDelivered();
            System.out.println("Parcel delivered successfully!");
        } catch (ParcelNotFoundException | AlreadyDeliveredException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        boolean isDone = false;

        while (!isDone) {
            DisplayUtils.printMenu("WELCOME",
                    "Add Parcel",
                    "View All Parcels",
                    "Change Delivery Service",
                    "Mark as Delivered",
                    "Exit");

            int selectedOption = DataValidations.intChoiceValidation("Select Option", 1, 2, 3, 4, 5);

            switch (selectedOption) {
                case 1 -> addParcel();
                case 2 -> viewAllParcels();
                case 3 -> changeDeliveryService();
                case 4 -> markAsDelivered();
                case 5 -> isDone = true;
            }

        }
    }
}
