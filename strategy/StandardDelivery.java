package strategy;

public class StandardDelivery implements DeliveryStrategy {
    @Override public double computeFee(double baseFee) { return baseFee; }
    @Override public boolean isAllowed(double chargeableWeight) { return true; }
    @Override public String getName() { return "Standard"; }
}