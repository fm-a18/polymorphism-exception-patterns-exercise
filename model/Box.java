package model;

import exception.InvalidInputException;
import strategy.DeliveryStrategy;

public class Box extends Parcel{
    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight, int length, int width, int height)
            throws InvalidInputException {
        super(trackingNumber, receiverName, weight);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getVolumetricWeight() {
        return (double) (getLength() * getWidth() * getHeight()) / 3500;
    }

    @Override
    protected double getMaxWeight() {
        return 50;
    }

    @Override
    public double getChargeableWeight(){
        return Math.ceil(getVolumetricWeight());
    }

    @Override
    public double getBaseFee(){
        return 40*getChargeableWeight();
    }

    @Override
    public String getParcelType(){
        return "Box";
    }
}
