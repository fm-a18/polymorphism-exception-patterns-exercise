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
            default -> throw new IllegalStateException("Error: "); //Error msg
        };
    }

    private static Parcel inputDocument(String trackingNumber, String receiverName, double weight)
            throws InvalidInputException {
        int numOfPages = DataValidations.validateInt("Number of Pages (1-100)");
        return new Document(trackingNumber, receiverName, weight, numOfPages);
    }

    private static Parcel inputBox(String trackingNumber, String receiverName, double weight)
            throws InvalidInputException {
        int length = DataValidations.validateInt("Length (cm)");
        int width = DataValidations.validateInt("Width (cm)");
        int height = DataValidations.validateInt("Height (cm)");
        return new Box(trackingNumber, receiverName, weight, length, width, height);
    }

    private static Parcel inputFragile(String trackingNumber, String receiverName, double weight)
            throws InvalidInputException {
        int length = DataValidations.validateInt("Length (cm)");
        int width = DataValidations.validateInt("Width (cm)");
        int height = DataValidations.validateInt("Height (cm)");
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
            default -> throw new IllegalStateException("Error: "); //Error msg
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
            System.out.printf("Parcel added successfully! Delivery fee: Php %,.2f%n", parcel.getDeliveryFee());
        } catch (DuplicateTrackingException e) {
            System.out.println(e.getMessage());
        }
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

            try {
                parcel.setDeliveryStrategy(selectDeliveryStrategy()); // old service stays if this throws
                System.out.printf("Delivery service changed! New fee: Php %,.2f%n", parcel.getDeliveryFee());
            } catch (SameDayNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } catch (ParcelNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void markAsDelivered() throws ParcelNotFoundException {
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

    public static void main(String[] args) throws ParcelNotFoundException {
        boolean isDone = false;

        while (!isDone) {
            DisplayUtils.printMenu("WELCOME",
                    "Add Parcel",
                    "View All Parcels",
                    "Change Delivery Service",
                    "Mark as Delivered",
                    "Exit");

            int selectedOption = DataValidations.intChoiceValidation("Select Option:", 1, 2, 3, 4, 5);

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
