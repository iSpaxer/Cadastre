package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;


@RestController
public class RestApiController {

    private WebClient.Builder webClientBuilder;

    @Autowired
    public RestApiController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


///TODO дописать
//    @PostMapping("/postClient/")
//    public ResponseEntity<List<Client>>

    @GetMapping("/getClients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        String URL = "http://localhost:8088/DB/allClients";
        List<ClientDTO> clientDTOList = webClientBuilder.build()
                .get()
                .uri(URL)
                .retrieve()
                .bodyToMono(List.class)
                .block();
        return new ResponseEntity<>(clientDTOList, HttpStatus.OK);

    }

}
