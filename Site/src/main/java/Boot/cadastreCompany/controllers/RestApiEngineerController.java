package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.dto.EngineerUpdatePasswordDTO;
import Boot.cadastreCompany.exception.AuthenticationError;
import Boot.cadastreCompany.exception.DBRequestException;
import Boot.cadastreCompany.exception.EngineerNotAuthentication;
import Boot.cadastreCompany.exception.UnknownException;
import Boot.cadastreCompany.security.EngDetails;
import Boot.cadastreCompany.service.ApiRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class RestApiEngineerController {
    private ApiRequestService apiRequestService;

    @Autowired
    public RestApiEngineerController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @GetMapping("/getEngineerLogin")
    public ResponseEntity<?> getEngineerLogin(HttpServletRequest request) {

        var auth = (Authentication) request.getUserPrincipal();
        var engDetails = (EngDetails) auth.getPrincipal();
        var engDTO = engDetails.getEngineerDTO();
        class EngName {
            public String login;
            EngName(String login) {
                this.login = login;
            }
        }
        EngName engName = new EngName(engDTO.getLogin());
        return new ResponseEntity<>(engName, HttpStatus.OK);
    }

    @PostMapping("/updateEngineerPassword")
    public ResponseEntity<?> updateEngineerPassword(@RequestBody @Valid EngineerUpdatePasswordDTO engineerUpdatePasswordDTO, HttpServletRequest request) {

        var messageFromDB = apiRequestService.updatePasswordByEngineer(engineerUpdatePasswordDTO, request);


        return new ResponseEntity<>(messageFromDB, HttpStatus.OK);
    }

    //TODO for Rest API
//    @PostMapping("/login")

//    public ResponseEntity<?> login(@Valid @RequestBody EngineerDTO engineerDTO, BindingResult bindingResult,
//                             HttpServletRequest request, HttpServletResponse response) {
//        if (bindingResult.hasErrors()) {
//            throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
//        }
//        try {
//            request.login(engineerDTO.getLogin(), engineerDTO.getPassword());
//        } catch (ServletException e) {
//            throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
//        }
//        var auth = (Authentication) request.getUserPrincipal();
//        var engDetails = (EngDetails) auth.getPrincipal();
//        var engDTO = engDetails.getEngineerDTO();
//       return new ResponseEntity<>("Successfully! " + engDTO.getLogin() ,HttpStatus.OK);
//    }

    @PostMapping("/test/auth")
    public ResponseEntity<?> test_auth(@RequestBody @Valid EngineerDTO engineerDTO) {
        apiRequestService.findByEngineer(engineerDTO.getLogin());

        return new ResponseEntity<>(engineerDTO.getLogin() + " " + engineerDTO.getPassword(), HttpStatus.OK);
    }
}
