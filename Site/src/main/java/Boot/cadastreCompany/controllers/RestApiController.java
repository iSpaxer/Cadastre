package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.models.Client;

import java.util.List;

@RestController
public class RestApiController {
    private HomeService homeService;

    @Autowired
    public RestApiController(HomeService homeService) {
        this.homeService = homeService;
    }

    ///TODO дописать
//    @PostMapping("/postClient/")
//    public ResponseEntity<List<Client>>

    @GetMapping("/getClients")
    public ResponseEntity<List<Client>> getClients() {

        return new ResponseEntity<>(homeService.getAllClient(), HttpStatus.OK);
    }

}
