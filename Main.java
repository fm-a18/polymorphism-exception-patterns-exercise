import utils.DataValidations;
import utils.DisplayUtils;

public class Main {
    private static void addParcel() {
    }

    private static void viewAllParcels() {
    }

    private static void changeDeliveryService() {
    }

    private static void markAsDelivered() {
    }

    public static void main(String[] args) {
        boolean isDone = false;

        while(!isDone){
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
                case 5 -> {
                    isDone = true;
                    break;
                }
            }

        }
    }
}
