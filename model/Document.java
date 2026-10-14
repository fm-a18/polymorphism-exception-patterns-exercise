package model;

import exception.InvalidInputException;

public class Document extends Parcel {
    private int numOfPages;

    public Document(String trackingNumber, String receiverName, double weight, int numOfPages)
            throws InvalidInputException {
        super(trackingNumber, receiverName, weight);
        setNumOfPages(numOfPages);
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) throws InvalidInputException {
        if (numOfPages < 1 || numOfPages > 100)
            throw new InvalidInputException("The number of pages must be from 1-100.");
        this.numOfPages = numOfPages;
    }

    @Override
    protected double getMaxWeight() {
        return 0.5;
    }

    @Override
    public double getChargeableWeight() {
        return getWeight();
    }

    @Override
    public double getBaseFee() {
        return 50;
    }

    @Override
    public String getParcelType() {
        return "Document";
    }

    @Override
    public String getAdditionalInfo() {
        return getNumOfPages() + " pages";
    }
}
