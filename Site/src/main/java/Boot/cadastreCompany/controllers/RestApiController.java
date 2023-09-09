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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rabitmq.RabbitQueue;


import java.util.List;


@RestController()
@RequestMapping("/api")
public class RestApiController {

    private ApiRequestService apiRequestService;
    private AuthenticationManager authenticationManager;
    private ClientProducer clientProducer;


    @Autowired
    public RestApiController(ApiRequestService apiRequestService, AuthenticationManager authenticationManager, ClientProducer clientProducer) {
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody EngineerDTO engineerDTO, BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
        }

        try {
            request.login(engineerDTO.getLogin(), engineerDTO.getPassword());
        } catch (ServletException e) {
            throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
        }

        var auth = (Authentication) request.getUserPrincipal();
        var engDetails = (EngDetails) auth.getPrincipal();
        var engDTO = engDetails.getEngineerDTO();

       return new ResponseEntity<>("Successfully! " + engDTO.getLogin() ,HttpStatus.OK);
    }

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


    @ExceptionHandler
    private ResponseEntity<?> handleException(UnknownException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(DBRequestException e) {
        System.err.println("Error: " + e.getStatusCode() + " " + e.getMessage());
        return new ResponseEntity<>("DBRequestException...\nError: " + e.getStatusCode() + " " + e.getMessage(), HttpStatusCode.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(AuthenticationError e) {
        System.err.println("Error authentication.." + e.getMessage() + "id: " + e.getId());
        return new ResponseEntity<>("Error authentication... " + e.getMessage() + "id: " + e.getId(), HttpStatus.UNAUTHORIZED);
    }
}
