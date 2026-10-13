package model;

import strategy.DeliveryStrategy;

public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private DeliveryStrategy deliveryStrategy;
    private ParcelStatus parcelStatus;

    public Parcel(String trackingNumber, String receiverName, double weight,
                  DeliveryStrategy deliveryStrategy, ParcelStatus parcelStatus){
        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName;
        this.weight = weight;
        this.deliveryStrategy = deliveryStrategy;
        this.parcelStatus = ParcelStatus.PENDING;
    }

    public String getTrackingNumber(){
        return trackingNumber;
    }

    public String getReceiverName(){
        return receiverName;
    }

    public double getWeight(){
        return weight;
    }

    public String getDeliveryStrategy(){
        return deliveryStrategy.toString();
    }

    public String getParcelStatus(){
        return parcelStatus.toString();
    }

    public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy){
        if (deliveryStrategy == null){
            throw new IllegalArgumentException(""); //Add error msg
        }
        this.deliveryStrategy = deliveryStrategy;
    }

    public void setParcelStatus(ParcelStatus parcelStatus){
        if (parcelStatus == null){
            throw new IllegalArgumentException(""); //Add error msg
        }
        this.parcelStatus = parcelStatus;
    }

}
