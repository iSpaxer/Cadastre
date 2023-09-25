package Boot.cadastreCompany.service.rabbit;

public interface NotificationProducer {
    void produce(String rabbitQueue, String str);
}
