package model;

import exception.AlreadyDeliveredException;
import exception.InvalidInputException;
import exception.SameDayNotAllowedException;
import strategy.DeliveryStrategy;

public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private DeliveryStrategy deliveryStrategy;
    private ParcelStatus parcelStatus;

    public Parcel(String trackingNumber, String receiverName, double weight)
            throws InvalidInputException {
        if (trackingNumber == null || !trackingNumber.matches("ABC\\d{4}"))
            throw new InvalidInputException("Tracking number must be ABC followed by 4 digits!");
        if (receiverName == null || receiverName.trim().isEmpty())
            throw new InvalidInputException("Receiver name cannot be empty!");
        if (weight <= 0)
            throw new InvalidInputException("Weight must be greater than 0!");
        if (weight > getMaxWeight())
            throw new InvalidInputException(
                    "Weight exceeds the maximum of " + getMaxWeight() + " kg for this parcel type!");

        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName.trim();
        this.weight = weight;
        this.parcelStatus = ParcelStatus.PENDING;
    }

    protected abstract double getMaxWeight();

    public abstract double getChargeableWeight();

    public abstract double getBaseFee();

    public abstract String getParcelType();

    public void setDeliveryStrategy(DeliveryStrategy newStrategy) throws SameDayNotAllowedException {
        if (!newStrategy.isAllowed(getChargeableWeight()))
            throw new SameDayNotAllowedException("Same Day delivery is only for parcels up to 10 kg!");
        this.deliveryStrategy = newStrategy;
    }

    public double getDeliveryFee() {
        if (deliveryStrategy == null) throw new IllegalStateException("Delivery service not set");
        return deliveryStrategy.computeFee(getBaseFee());
    }

    public void setStatusAsDelivered() throws AlreadyDeliveredException {
        if (parcelStatus == ParcelStatus.DELIVERED) {
            throw new AlreadyDeliveredException("Parcel has already been delivered.");
        }
        this.parcelStatus = ParcelStatus.DELIVERED;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public double getWeight() {
        return weight;
    }

    public strategy.DeliveryStrategy getDeliveryStrategy() {
        return deliveryStrategy;
    }

    public model.ParcelStatus getParcelStatus() {
        return parcelStatus;
    }

    public abstract String getAdditionalInfo();

}
