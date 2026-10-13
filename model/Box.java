package model;

import strategy.DeliveryStrategy;

public class Box extends Parcel{
    public Box(String trackingNumber, String receiverName, double weight, DeliveryStrategy deliveryStrategy, ParcelStatus parcelStatus) {
        super(trackingNumber, receiverName, weight, deliveryStrategy, parcelStatus);
    }
}
