package model;

import strategy.DeliveryStrategy;

public class Document extends Parcel{
    private int numOfPages;
    public Document(String trackingNumber, String receiverName, double weight, DeliveryStrategy deliveryStrategy, ParcelStatus parcelStatus) {
        super(trackingNumber, receiverName, weight, deliveryStrategy, parcelStatus);
        this.numOfPages = numOfPages;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }




}
