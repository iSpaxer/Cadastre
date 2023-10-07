package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.ClientDTO;
import Boot.cadastreCompany.dto.ClientDbDTO;
import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.AuthenticationError;
import Boot.cadastreCompany.exception.DBRequestException;
import Boot.cadastreCompany.exception.UnknownException;
import Boot.cadastreCompany.security.EngDetails;
import Boot.cadastreCompany.service.ApiRequestService;
import Boot.cadastreCompany.service.rabbit_2_0.ClientProducer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rabitmq.RabbitQueue;


import java.util.List;


@RestController()
@RequestMapping("/api")
//@DependsOn("securityFilterChain")
public class RestApiController {

    private ApiRequestService apiRequestService;
    private AuthenticationManager authenticationManager;
    private ClientProducer clientProducer;
//    private final RememberMeServices rememberMeServices;


    @Autowired
    public RestApiController(ApiRequestService apiRequestService, AuthenticationManager authenticationManager,
                             ClientProducer clientProducer) {
        this.apiRequestService = apiRequestService;
        this.authenticationManager = authenticationManager;
        this.clientProducer = clientProducer;
    }


///TODO дописать
//    @PostMapping("/postClient/")
//    public ResponseEntity<List<Client>>

    ///TODO not exception
    @GetMapping("/getClients")
    public ResponseEntity<?> getClients() {
        List<ClientDbDTO> allClients = apiRequestService.getAllClients();
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }

    ///TODO not exception
    @GetMapping("/lastClient")
    public ResponseEntity<?> getLastClient() {

        ClientDbDTO lastClient = apiRequestService.getLastClient();
        return new ResponseEntity<>(lastClient, HttpStatus.OK);
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

    @PostMapping("/saveClient")
    public ResponseEntity<?> saveClient(@RequestBody @Valid ClientDTO clientDTO) {

        clientProducer.clientSaveProduce(RabbitQueue.GET_CLIENT, clientDTO);

        ///TODO
        return new ResponseEntity<>("Successfully!", HttpStatus.OK);
    }

    @PostMapping("/takeClient")
    public ResponseEntity<?> takeClient(@RequestBody @Valid ClientDbDTO clientDbDTO, HttpServletRequest request) {

        var auth = (Authentication) request.getUserPrincipal();
        var engDetails = (EngDetails) auth.getPrincipal();
        var engDTO = engDetails.getEngineerDTO();

        apiRequestService.takeClient(engDTO, clientDbDTO);

        //TODO передаём в сервис, там делаем http запрос на БД

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getEngineerLogin")
    public ResponseEntity<?> getEngineerLogin( HttpServletRequest request) {

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

//    @GetMapping("/csrf")
//    public CsrfResponse csrf(HttpServletRequest request) {
//        var csrf = (CsrfToken) request.getAttribute("_csrf");
////        return new CsrfResponse(csrf.getToken());
//    }

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
    private ResponseEntity<?> handleException(UnknownException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    public record CsrfResponse(String token) {}
}
