package Boot.cadastreCompany.service.rabbit;

import java.util.List;

public interface CommonAnswerConsumer {
    void consumeCheckLogin(List<String> loginPassword);

    void consumeGetListClient(Boolean bool);
}
