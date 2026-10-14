package model;

import exception.InvalidInputException;

public class Fragile extends Box {
    private final String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight, int length, int width, int height, String handlingNote) throws InvalidInputException {
        super(trackingNumber, receiverName, weight, length, width, height);
        if (handlingNote == null || handlingNote.trim().isEmpty())
            throw new InvalidInputException("Handling note cannot be empty!");
        this.handlingNote = handlingNote.trim();
    }

    public String getHandlingNote() {
        return handlingNote;
    }

    @Override
    protected double getMaxWeight() {
        return 30;
    }

    @Override
    public double getBaseFee() {
        return super.getBaseFee() + 100;
    }

    @Override
    public String getParcelType() {
        return "Fragile";
    }

    @Override
    public String getAdditionalInfo() {
        return getHandlingNote();
    }
}