package Boot.cadastreCompany.exception;

public class DBRequestException extends RuntimeException {
    private static final long serialVersionUID = -7661881974219233311L;

    private int statusCode;

    public DBRequestException (String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
