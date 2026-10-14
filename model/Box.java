package model;

import exception.InvalidInputException;

public class Box extends Parcel {
    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight, int length, int width, int height)
            throws InvalidInputException {
        super(trackingNumber, receiverName, weight);
        if (length <= 0 || width <= 0 || height <= 0)
            throw new InvalidInputException("Length, width, and height must be greater than 0!");
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
    public double getChargeableWeight() {
        double volumetric = getVolumetricWeight();
        double max = Math.max(getWeight(), volumetric);
        return Math.ceil(max);
    }

    @Override
    public double getBaseFee() {
        return 40 * getChargeableWeight();
    }

    @Override
    public String getParcelType() {
        return "Box";
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("%d x %d x %dcm", getLength(), getWidth(), getHeight());
    }
}
