package strategy;

public class SameDayDelivery implements DeliveryStrategy {
    @Override
    public double computeFee(double baseFee) {
        return (baseFee * 2) + 100;
    }

    @Override
    public boolean isAllowed(double chargeableWeight) {
        return chargeableWeight <= 10;
    }

    @Override
    public String getName() {
        return "Same Day";
    }
}