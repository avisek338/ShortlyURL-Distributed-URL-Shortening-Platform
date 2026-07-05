package avisek.example.urlservice.exception;


public class ShortCodeAlreadyExistException extends RuntimeException {
    public ShortCodeAlreadyExistException(String message) {
        super(message);
    }
}
