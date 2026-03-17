package africa.semicolon.exceptions;

public class VisitorDoesNotExistException extends RuntimeException {
    public VisitorDoesNotExistException(String message) {
        super(message);
    }
}
