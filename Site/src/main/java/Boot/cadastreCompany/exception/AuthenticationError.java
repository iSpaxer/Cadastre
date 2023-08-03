package Boot.cadastreCompany.exception;

import lombok.Data;

import java.util.Date;

@Data
public class AuthenticationError extends RuntimeException {
    private String message;
    private int id;
    private Date timestamp;

    public AuthenticationError(String message, int id) {
        this.message = message;
        this.id = id;
        timestamp = new Date();
    }
}
