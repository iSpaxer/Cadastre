package DBPostgres.service.impl;

import DBPostgres.dto.client.ClientDTO;
import DBPostgres.dto.client.ClientWithoutPhoneForTelegramDTO;
import DBPostgres.models.Client;
import DBPostgres.service.ApiRequestService;
import DBPostgres.service.DBConsumer;
import DBPostgres.service.EngService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

import static rabitmq.RabbitQueue.GET_CLIENT;

@Slf4j
@Service
public class DBConsumerImpl implements DBConsumer {
    private final ClientServiceImpl clientService;
    private final EngService engService;
    private final ApiRequestService apiRequestService;
    private final ModelMapper modelMapper;

   // private static final Logger log = LoggerFactory.getLogger(WebClientFilter.class);

    @Autowired
    public DBConsumerImpl(ClientServiceImpl homeService, EngService engService, ApiRequestService apiRequestService, ModelMapper modelMapper) {
        this.clientService = homeService;
        this.engService = engService;
        this.apiRequestService = apiRequestService;
        this.modelMapper = modelMapper;
    }

    @Override
    @RabbitListener(queues = GET_CLIENT)
    public void clientConsume(ClientDTO clientDTO) {
        log.info("Getting new client:    name: " + clientDTO.getName() + " phone: " + clientDTO.getPhone());
        Client savedClient = clientService.save(modelMapper.map(clientDTO, Client.class));
        Optional<Long[]> allEngineersWithTgId = engService.getAllEngineersWithTgId();
        if (allEngineersWithTgId.isPresent()) {
            ClientWithoutPhoneForTelegramDTO clientWithoutPhoneForTelegramDTO = new ClientWithoutPhoneForTelegramDTO(
                    savedClient.getId(),
                    savedClient.getName(),
                    allEngineersWithTgId.get()
            );
            try {
                apiRequestService.sendNotificationInTelegram(clientWithoutPhoneForTelegramDTO);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //TODO если тг упадёт, то смс не придёт
                return;
            }
        } else {
            log.error("Not exist engineer sad with telegram id");
        }
    }
}
