package DBPostgres.controller;

import DBPostgres.dto.ClientDTO;
import DBPostgres.dto.EngineerDTO;
import DBPostgres.dto.EngineerLoginDTO;
import DBPostgres.exception.GetJSONException;
import DBPostgres.exception.UnknownException;
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
import java.util.Optional;

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

    @GetMapping("/lastClient")
    public Client getLastClients() {
        return homeService.getLastClient();
    }

//    @PostMapping("/saveClient")
//    public ResponseEntity<HttpStatus> gettingClient(@RequestBody @Valid ClientDTO clientDTO,
//                                                    BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            StringBuilder errorMsg = new StringBuilder();
//
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//
//            for (FieldError error : fieldErrors) {
//                errorMsg
//                        .append(error.getField())           // на каком поле была ошибка
//                        .append(" — ")                      // —
//                        .append(error.getDefaultMessage())  // выведем какая была ошибка
//                        .append(";");
//            }
//            throw new GetJSONException(errorMsg.toString());
//        }
//
//        homeService.save(modelMapper.map(clientDTO, Client.class));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/saveEngineer")
    public ResponseEntity<?> gettingEngineer(@RequestBody @Valid EngineerDTO engineerDTO, BindingResult bindingResult) {
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

    @PostMapping("/findByEngineer")
    public ResponseEntity<?> findByEngineer(@RequestBody String engineerLoginDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new UnknownException();
//        }
        Optional<EngineerDTO> engineerDTO = engService.findByEngineerLogin(engineerLoginDTO);
        if (engineerDTO.isEmpty()) {
            return  new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(engineerDTO.get(), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(GetJSONException e) {
        ClientErrorResponse  clientErrorResponse = new ClientErrorResponse(
                "Error JSON format... Please reading instruction for API DB'\n" + e.getMessage()
        );
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(UnknownException e) {
        return new ResponseEntity<>("UnknownException in DB... ", HttpStatus.BAD_REQUEST);
    }
}
