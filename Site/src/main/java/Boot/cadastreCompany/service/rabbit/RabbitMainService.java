package Boot.cadastreCompany.service.rabbit;

import Boot.cadastreCompany.service.HomeService;
import org.springframework.stereotype.Service;

import static rabitmq.RabbitQueue.ANSWER_CLIENTS;

@Service
public class RabbitMainService {
    private GetListClientsProducer getListClientsProducer;
    private HomeService homeService;

    public RabbitMainService(GetListClientsProducer getListClientsProducer, HomeService homeService) {
        this.getListClientsProducer = getListClientsProducer;
        this.homeService = homeService;
    }

    public void SendAllClients(int i) {
        //TODO добавить счеткик?
        getListClientsProducer.produceAll(ANSWER_CLIENTS, homeService.getAllClient() );
    }

}
