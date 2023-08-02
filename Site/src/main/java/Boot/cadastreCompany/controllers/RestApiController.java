package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.ClientDTO;
import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.DBRequestException;
import Boot.cadastreCompany.exception.UnknownException;
import Boot.cadastreCompany.service.ApiRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;


@RestController
public class RestApiController {

    private ApiRequestService apiRequestService;

    @Autowired
    public RestApiController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

///TODO дописать
//    @PostMapping("/postClient/")
//    public ResponseEntity<List<Client>>

    @GetMapping("/getClients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clientDTOList = apiRequestService.getAllClients();
        return new ResponseEntity<>(clientDTOList, HttpStatus.OK);
    }

    @GetMapping("/lastClient")
    public ResponseEntity<ClientDTO> getLastClient() {

        ClientDTO clientDTO = apiRequestService.getLastClient();
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody @Valid EngineerDTO engineerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            /// TODO
            return new ResponseEntity<>(
                    "Some mistake.. " + bindingResult.getFieldErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        ///TODO
        return null;

    }

    @PostMapping("/saveClient_2")
    public ResponseEntity<?> saveClient(@RequestBody @Valid ClientDTO clientDTO) {
//        if (bindingResult.hasErrors()) {
//            throw new UnknownException("UnknownException...\n Error save client", new Date());
//        }

        apiRequestService.saveClient(clientDTO);

        ///TODO
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    public String hidden() {

        return "Hidden method";
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
}
