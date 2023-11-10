package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.ClientDTO;
import Boot.cadastreCompany.dto.ClientDbDTO;
import Boot.cadastreCompany.security.EngDetails;
import Boot.cadastreCompany.service.ApiRequestService;
import Boot.cadastreCompany.service.rabbit_2_0.ClientProducer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rabitmq.RabbitQueue;


import java.util.List;


@RestController()
@RequestMapping("/api")
public class RestApiClientController {

    private ApiRequestService apiRequestService;
    private ClientProducer clientProducer;

    @Autowired
    public RestApiClientController(ApiRequestService apiRequestService,
                                   ClientProducer clientProducer) {
        this.apiRequestService = apiRequestService;
        this.clientProducer = clientProducer;
    }


    ///TODO not exception
    @GetMapping("/getClients")
    public ResponseEntity<?> getClients( @RequestParam(required = false) String size, @RequestParam(required = false) String page) {

        Page<ClientDbDTO> clientDbDToPage = apiRequestService.getAllClients(page, size);
        return new ResponseEntity<>(clientDbDToPage, HttpStatus.OK);
    }

    ///TODO not exception
    @GetMapping("/lastClient")
    public ResponseEntity<?> getLastClient() {

        ClientDbDTO lastClient = apiRequestService.getLastClient();
        return new ResponseEntity<>(lastClient, HttpStatus.OK);
    }


    @PostMapping("/saveClient")
    public ResponseEntity<?> saveClient(@RequestBody @Valid ClientDTO clientDTO) {

        clientProducer.clientSaveProduce(RabbitQueue.GET_CLIENT, clientDTO);

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
}
