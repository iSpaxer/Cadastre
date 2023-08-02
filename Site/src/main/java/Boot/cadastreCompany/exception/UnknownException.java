package Boot.cadastreCompany.exception;

import java.util.Date;

public class UnknownException extends RuntimeException{
    public UnknownException(String message, Date timestamp) {
        super("Timestamp: " + timestamp + "\n" + message);
    }
}
