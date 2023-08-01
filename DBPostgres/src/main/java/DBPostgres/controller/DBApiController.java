package DBPostgres.controller;

import DBPostgres.dto.ClientDTO;
import DBPostgres.dto.EngineerDTO;
import DBPostgres.exception.GetJSONException;
import DBPostgres.models.Client;
import DBPostgres.models.Engineer;
import DBPostgres.service.EngService;
import DBPostgres.service.HomeService;
import DBPostgres.util.ClientErrorResponse;
import DBPostgres.util.validate.EngineerValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("DB/")
public class DBApiController {
    private HomeService homeService;
    private EngService engService;
    private EngineerValidator engineerValidator;
    private ModelMapper modelMapper;

    @Autowired
    public DBApiController(HomeService homeService, EngService engService, EngineerValidator engineerValidator, ModelMapper modelMapper) {
        this.homeService = homeService;
        this.engService = engService;
        this.engineerValidator = engineerValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/test")
    public String testFunction() {
        return "is working!";
    }

    @GetMapping("/allClients")
    public List<Client> clientList() {
        return homeService.getAllClient();
    }

    @GetMapping("/lastClients")
    public Client getLastClients() {
        return homeService.getLastClient();
    }

    @PostMapping("/postClients")
    public ResponseEntity<HttpStatus> gettingClient(@RequestBody @Valid ClientDTO clientDTO,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError error : fieldErrors) {
                errorMsg
                        .append(error.getField())           // на каком поле была ошибка
                        .append(" — ")                      // —
                        .append(error.getDefaultMessage())  // выведем какая была ошибка
                        .append(";");
            }
            throw new GetJSONException(errorMsg.toString());
        }

        homeService.save(modelMapper.map(clientDTO, Client.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/postEngineer")
    public ResponseEntity<HttpStatus> gettingEngineer(@RequestBody @Valid EngineerDTO engineerDTO, BindingResult bindingResult) {
        engineerValidator.validate(engineerDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError error : fieldErrors) {
                errorMsg
                        .append(error.getField())           // на каком поле была ошибка
                        .append(" — ")                      // —
                        .append(error.getDefaultMessage())  // выведем какая была ошибка
                        .append(";");
            }
            throw new GetJSONException(errorMsg.toString());
        }
        ///TODO add security config. Стоп, а мб сюда должен придти уже зашифрованный пароль ?
        engService.save(modelMapper.map(engineerDTO, Engineer.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(GetJSONException e) {
        ClientErrorResponse  clientErrorResponse = new ClientErrorResponse(
                "Error JSON format... Please reading instruction for API DB'\n" + e.getMessage()
        );
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
