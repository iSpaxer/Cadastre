package TgBot.controller;

import TgBot.dto.ClientDbDTO;
import TgBot.web.service.impl.ApiRequestService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/jwt")
@CrossOrigin
public class TjwtController {
    private final ApiRequestService apiRequestService;

    @Autowired
    public TjwtController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @GetMapping("/getClients")
    public ResponseEntity<?> getClients(@RequestParam(required = false) String size, @RequestParam(required = false) String page) {
        Page<ClientDbDTO> clientDbDToPage = apiRequestService.getAllClients(page, size);
        return new ResponseEntity<>(clientDbDToPage, HttpStatus.OK);
    }
}
