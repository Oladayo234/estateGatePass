package exceptions;

public class GatePassOtpNotExistException extends RuntimeException {
    public GatePassOtpNotExistException(String message) {
        super(message);
    }
}
