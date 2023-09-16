package DBPostgres.exception;

public class BodyEmptyException extends RuntimeException {
    public BodyEmptyException(String message) {
        super(message);
    }
}
