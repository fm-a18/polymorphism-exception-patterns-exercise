package strategy;

public class ExpressDelivery implements DeliveryStrategy {
    @Override public double computeFee(double baseFee) {
        return baseFee * 1.5;
    }

    @Override public boolean isAllowed(double chargeableWeight) {
        return true;
    }

    @Override public String getName() {
        return "Express";
    }
}