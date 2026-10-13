package strategy;

public interface DeliveryStrategy {
    double computeFee(double baseFee);
    boolean isAllowed(double chargeableWeight);
    String getName();
}