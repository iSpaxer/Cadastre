package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.exception.AuthenticationError;
import Boot.cadastreCompany.exception.DBRequestException;
import Boot.cadastreCompany.exception.EngineerNotAuthentication;
import Boot.cadastreCompany.exception.UnknownException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice()
public class RestApiExceptionController {

    @ExceptionHandler
    private ResponseEntity<?> handleException(DBRequestException e) {
        String responseMessage = "DBRequestException...\n Error: " + e.getStatusCode() + " " + e.getMessage();
        System.err.println(responseMessage);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(AuthenticationError e) {
        System.err.println("Error authentication.." + e.getMessage() + "id: " + e.getId());
        return new ResponseEntity<>("Error authentication... " + e.getMessage() + "id: " + e.getId(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(EngineerNotAuthentication e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(UnknownException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
