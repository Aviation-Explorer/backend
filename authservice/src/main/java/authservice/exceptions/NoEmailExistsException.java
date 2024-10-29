package authservice.exceptions;

public class NoEmailExistsException extends RuntimeException {
    public NoEmailExistsException(String message) {
        super(message);
    }
}
