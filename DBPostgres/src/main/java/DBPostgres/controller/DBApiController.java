package DBPostgres.controller;

import DBPostgres.dto.client.ClientDTO;
import DBPostgres.dto.client.ClientDbDTO;
import DBPostgres.dto.client.ClientForOutputTelegramDTO;
import DBPostgres.dto.client.ClientTakeTelegramDTO;
import DBPostgres.dto.engineer.EngineerDTO;
import DBPostgres.dto.engineer.EngineerTelegramDTO;
import DBPostgres.dto.engineer.EngineerUpdatePasswordDTO;
import DBPostgres.dto.pricelist.PricelistDTO;
import DBPostgres.exception.*;
import DBPostgres.models.Client;
import DBPostgres.models.Engineer;
import DBPostgres.service.ClientService;
import DBPostgres.service.CommonService;
import DBPostgres.service.PricelistService;
import DBPostgres.service.EngService;
import DBPostgres.util.ClientErrorResponse;
import DBPostgres.util.validate.EngineerValidator;
import DBPostgres.util.wrapper.EngineerAndClient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
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
    private PricelistService pricelistService;

    @Autowired
    public DBApiController(ClientService clientService, EngService engService, CommonService commonService, EngineerValidator engineerValidator, ModelMapper modelMapper, PricelistService pricelistService) {
        this.clientService = clientService;
        this.engService = engService;
        this.commonService = commonService;
        this.engineerValidator = engineerValidator;
        this.modelMapper = modelMapper;
        this.pricelistService = pricelistService;
    }

    @GetMapping("/test")
    public String testFunction() {
        return "is working!";
    }

//    @GetMapping("/allClients")
//    public ResponseEntity<?> clientList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
//        Page<Client> clientPage = clientService.getAllClient(pageable);
//        return new ResponseEntity<>(clientPage.getContent(), HttpStatus.OK);
//    }

    @GetMapping("/getClients")
    public ResponseEntity<?> getClients(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<ClientDbDTO> clientDbDToPage = clientService.getAllClients(pageable);
        return new ResponseEntity<>(clientDbDToPage, HttpStatus.OK);
    }

    @GetMapping("/getClientsWithBetweenDate")
    public ResponseEntity<?> getClientsWithBetweenDate (
            @PageableDefault(sort = {"created_data"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
            @RequestParam(name = "from") String fromDate, @RequestParam(name = "to") String toDate) {
        Page<ClientDbDTO> clientDbDToPage = clientService.getClientsWithBetweenDate(fromDate, toDate, pageable);
        return new ResponseEntity<>(clientDbDToPage, HttpStatus.OK);
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

    // TODO создание нового Инженера
//    @PostMapping("/saveEngineer")
//    public ResponseEntity<?> gettingEngineer(@RequestBody @Valid EngineerLoginDTO engineerLoginDTO, BindingResult bindingResult) {
//        engineerValidator.validate(engineerLoginDTO, bindingResult);
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
//            List<ObjectError> errors = bindingResult.getAllErrors();
//            for (ObjectError error : errors) {
//                errorMsg
//                        .append(error.getCode());
//            }
//            throw new GetJSONException(errorMsg.toString());
//        }
//
//        engService.save(modelMapper.map(engineerLoginDTO, Engineer.class));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

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
        log.error(engineerDTO.toString());
        Boolean checkAuth = engService.authenticationEngineer(engineerDTO);
        log.info(" " + checkAuth);
        return new ResponseEntity<>(checkAuth, HttpStatus.OK);
    }

    @PostMapping("/authenticationEngineerTelegram")
    public ResponseEntity<?> authenticationEngineerTelegram(@RequestBody @Valid EngineerTelegramDTO engineerTelegramDTO,
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
        log.error(engineerTelegramDTO.toString());
        Boolean checkAuth = engService.authenticationEngineerTelegram(engineerTelegramDTO);
        log.info(" " + checkAuth);
        return new ResponseEntity<>(checkAuth, HttpStatus.OK);
    }


    @PostMapping("/telegramIsActive")
    public ResponseEntity<?> telegramIsActive(@RequestBody Long tgId) {

        Boolean isActive = engService.telegramIsActive(tgId);
        log.info(" " + isActive);
        return new ResponseEntity<>(isActive, HttpStatus.OK);
    }

    @GetMapping("getAllEngineersWithTgId")
    public ResponseEntity<?> getAllEngineersWithTgId() {
        Optional<Long[]> allEngineersWithTgId = engService.getAllEngineersWithTgId();
        return new ResponseEntity<>(allEngineersWithTgId.get(), HttpStatus.OK);
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

    @PostMapping("/takeClientUsingTgbot")
    public ResponseEntity<?> takeClientUsingTgbot(@RequestBody ClientTakeTelegramDTO clientTakeTelegramDTO) {
        ClientForOutputTelegramDTO clientForOutputTelegramDTO = commonService.takeClientUsingTgbot(clientTakeTelegramDTO);
        return new ResponseEntity<>(clientForOutputTelegramDTO, HttpStatus.OK);
    }



    @PostMapping("/updateEngineerPassword")
    public ResponseEntity<?> updateEngineerPassword(@RequestBody @Valid EngineerUpdatePasswordDTO engineerUpdatePasswordDTO, BindingResult bindingResult) {
//        engineerValidator.validate(engineerUpdatePasswordDTO, bindingResult);
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

        engService.updatePassword(engineerUpdatePasswordDTO);
        return new ResponseEntity<>("Update password for " + engineerUpdatePasswordDTO.getLogin() + " successfully!", HttpStatus.OK);
    }

    @PostMapping("/takeClient")
    public ResponseEntity<?> takeClient(@RequestBody EngineerAndClient engineerAndClient) {

//        Boolean bool = clientService.findById(engineerAndClient.getEngineer(), engineerAndClient.getClient());
        commonService.checkForTakeClient(engineerAndClient.getEngineer(), engineerAndClient.getClient());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getPricelist")
    public ResponseEntity<?> getPricelist() {
        List<PricelistDTO> pricelistAllDTO = pricelistService.getPricelist();
        return new ResponseEntity<>(pricelistAllDTO, HttpStatus.OK);
    }
    @GetMapping("/pricelistSwap")
    public ResponseEntity<?> getPricelistSwap() {
        pricelistService.pricelistSwap();
        return new ResponseEntity<>("Swap Pricelist is successfully", HttpStatus.OK);
    }


    // TODO VALID
    @PostMapping("/updatePricelistDeadline")
    public ResponseEntity<?> updatePricelistDeadline(@RequestBody @Valid PricelistDTO pricelistDTO) {
        pricelistService.updateDeadline(pricelistDTO);
        return new ResponseEntity<>("Updated Pricelist Deadline is successfully!", HttpStatus.OK);
    }

    @PostMapping("/updatePricelistCost")
    public ResponseEntity<?> updatePricelistCost(@RequestBody @Valid PricelistDTO pricelistDTO) {
        pricelistService.updateCost(pricelistDTO);
        return new ResponseEntity<>("Updated Pricelist Cost service is successfully!", HttpStatus.OK);
    }

    @PostMapping("/updatePricelist")
    public ResponseEntity<?> updatePricelist(@RequestBody List<PricelistDTO> pricelistDTOList) {
        pricelistService.updatePricelist(pricelistDTOList);
        return new ResponseEntity<>("Updated Pricelist successfully!", HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(GetJSONException e) {
        ClientErrorResponse  clientErrorResponse = new ClientErrorResponse(
                "Error JSON format... Please reading instruction for API DB" + e.getMessage()
        );
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(BodyEmptyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(ClientIsBusyAnotherEngineer e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(EngineerNotAuthentication e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(UnknownException e) {
        return new ResponseEntity<>("UnknownException in DB... ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(MissingServletRequestParameterException e) {
        return new ResponseEntity<>("Required parameters are not entered " + "HttpStatus " + HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(DateTimeException e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
    }
}
