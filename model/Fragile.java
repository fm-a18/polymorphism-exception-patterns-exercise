package model;

import exception.InvalidInputException;

public class Fragile extends Box {
    private final String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight, int length, int width, int height, String handlingNote) throws InvalidInputException {
        super(trackingNumber, receiverName, weight, length, width, height);
        this.handlingNote = handlingNote;
    }

    public String getHandlingNote() {
        return handlingNote;
    }

    @Override
    protected double getMaxWeight() {
        return 30;
    }

    @Override
    public double getBaseFee(){
        return super.getBaseFee()+100;
    }

    @Override
    public String getParcelType(){
        return "Fragile";
    }
}