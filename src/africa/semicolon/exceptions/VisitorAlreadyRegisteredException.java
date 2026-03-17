package africa.semicolon.exceptions;

public class VisitorAlreadyRegisteredException extends RuntimeException {
    public VisitorAlreadyRegisteredException(String message) {
        super(message);
    }
}
