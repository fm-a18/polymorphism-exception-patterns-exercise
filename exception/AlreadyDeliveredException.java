package exception;

public class AlreadyDeliveredException extends Exception {
    public AlreadyDeliveredException(String message) {
        super(message);
    }
}