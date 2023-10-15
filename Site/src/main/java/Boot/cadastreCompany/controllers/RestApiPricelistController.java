package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.PricelistDTO;
import Boot.cadastreCompany.service.ApiRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class RestApiPricelistController {
    private ApiRequestService apiRequestService;

    @Autowired
    public RestApiPricelistController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @GetMapping("/getPricelist")
    public ResponseEntity<?> getPricelist() {
        List<PricelistDTO> pricelistAllDTO = apiRequestService.getPricelist();
        return new ResponseEntity<>(pricelistAllDTO, HttpStatus.OK);
    }

    @PostMapping("/updatePricelistDeadline")
    public ResponseEntity<?> updatePricelistDeadline(@RequestBody @Valid PricelistDTO pricelistDTO) {
        String messageDB = apiRequestService.updateDeadline(pricelistDTO);
        return new ResponseEntity<>(messageDB, HttpStatus.OK);
    }
}
