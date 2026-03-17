package africa.semicolon.exceptions;

public class ResidentDoesNotExistException extends RuntimeException {
    public ResidentDoesNotExistException(String message) {
        super(message);
    }
}
