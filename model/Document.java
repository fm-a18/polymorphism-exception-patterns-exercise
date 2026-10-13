package model;

import strategy.DeliveryStrategy;

public class Document extends Parcel{
    public Document(String trackingNumber, String receiverName, double weight, DeliveryStrategy deliveryStrategy, ParcelStatus parcelStatus) {
        super(trackingNumber, receiverName, weight, deliveryStrategy, parcelStatus);
    }
}
