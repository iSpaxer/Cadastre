package DBPostgres.controller;

import DBPostgres.dto.ClientDTO;
import DBPostgres.dto.ClientDbDTO;
import DBPostgres.dto.EngineerDTO;
import DBPostgres.dto.EngineerLoginDTO;
import DBPostgres.exception.GetJSONException;
import DBPostgres.exception.UnknownException;
import DBPostgres.models.Client;
import DBPostgres.models.Engineer;
import DBPostgres.service.ClientService;
import DBPostgres.service.CommonService;
import DBPostgres.service.impl.CommonServiceImpl;
import DBPostgres.service.EngService;
import DBPostgres.service.impl.ClientServiceImpl;
import DBPostgres.util.ClientErrorResponse;
import DBPostgres.util.validate.EngineerValidator;
import DBPostgres.util.wrapper.EngineerAndClient;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("DB/")
@Slf4j
public class DBApiController {
    private ClientService clientService;
    private EngService engService;
    private CommonService commonService;
    private EngineerValidator engineerValidator;
    private ModelMapper modelMapper;


    @Autowired
    public DBApiController(ClientService clientService, EngService engService, CommonService commonService, EngineerValidator engineerValidator, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.engService = engService;
        this.commonService = commonService;
        this.engineerValidator = engineerValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/test")
    public String testFunction() {
        return "is working!";
    }

    @GetMapping("/allClients")
    public List<ClientDbDTO> clientList() {
        List<ClientDbDTO> clientList = clientService.getAllClient();
        return clientList;
    }

    @GetMapping("/lastClient")
    public Client getLastClients() {
        return clientService.getLastClient();
    }

    @PostMapping("/saveClient")
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

        clientService.save(modelMapper.map(clientDTO, Client.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveEngineer")
    public ResponseEntity<?> gettingEngineer(@RequestBody @Valid EngineerLoginDTO engineerLoginDTO, BindingResult bindingResult) {
        engineerValidator.validate(engineerLoginDTO, bindingResult);
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
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                errorMsg
                        .append(error.getCode());
            }
            throw new GetJSONException(errorMsg.toString());
        }

        ///TODO add security config. Стоп, а мб сюда должен придти уже зашифрованный пароль ? //Приходит незащифрованный пароль
        engService.save(modelMapper.map(engineerLoginDTO, Engineer.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authenticationEngineer")
    public ResponseEntity<?> authenticationEngineer(@RequestBody @Valid EngineerDTO engineerDTO, BindingResult bindingResult) {

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

        Boolean checkAuth = engService.authenticationEngineer(engineerDTO);
        log.info(" " + checkAuth);
        return new ResponseEntity<>(checkAuth, HttpStatus.OK);
    }

    @PostMapping("/findByEngineer")
    public ResponseEntity<?> findByEngineer(@RequestBody String engineerLoginDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new UnknownException();
//        }
        Optional<EngineerDTO> engineerDTO = engService.findByEngineerDTOLogin(engineerLoginDTO);
        if (engineerDTO.isEmpty()) {
            return  new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(engineerDTO.get(), HttpStatus.OK);
    }

    @PostMapping("/takeClient")
    public ResponseEntity<?> takeClient(@RequestBody EngineerAndClient engineerAndClient) {

//        Boolean bool = clientService.findById(engineerAndClient.getEngineer(), engineerAndClient.getClient());
        commonService.checkForTakeClient(engineerAndClient.getEngineer(), engineerAndClient.getClient());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(GetJSONException e) {
        ClientErrorResponse  clientErrorResponse = new ClientErrorResponse(
                "Error JSON format... Please reading instruction for API DB" + e.getMessage()
        );
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(UnknownException e) {
        return new ResponseEntity<>("UnknownException in DB... ", HttpStatus.BAD_REQUEST);
    }
}
