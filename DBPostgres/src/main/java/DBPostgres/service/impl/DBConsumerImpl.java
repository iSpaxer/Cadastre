package DBPostgres.service.impl;

import DBPostgres.dto.ClientDTO;
import DBPostgres.models.Client;
import DBPostgres.service.DBConsumer;
import DBPostgres.service.HomeService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static rabitmq.RabbitQueue.GET_CLIENT;

@Slf4j
@Service
public class DBConsumerImpl implements DBConsumer {
    private HomeService homeService;
    private ModelMapper modelMapper;

   // private static final Logger log = LoggerFactory.getLogger(WebClientFilter.class);

    @Autowired
    public DBConsumerImpl(HomeService homeService, ModelMapper modelMapper) {
        this.homeService = homeService;
        this.modelMapper = modelMapper;
    }

    @Override
    @RabbitListener(queues = GET_CLIENT)
    public void clientConsume(ClientDTO clientDTO) {

        log.info("Getting new client:    name: " + clientDTO.getName() + " phone: " + clientDTO.getPhone());
        homeService.save(modelMapper.map(clientDTO, Client.class));
    }
}
