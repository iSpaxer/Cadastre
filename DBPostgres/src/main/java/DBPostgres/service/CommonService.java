package DBPostgres.service;

import DBPostgres.dto.client.ClientDbDTO;
import DBPostgres.dto.client.ClientForOutputTelegramDTO;
import DBPostgres.dto.client.ClientTakeTelegramDTO;
import DBPostgres.dto.engineer.EngineerLoginDTO;

public interface CommonService {
    void checkForTakeClient(EngineerLoginDTO engineerLoginDTO, ClientDbDTO clientDbDTO);

    ClientForOutputTelegramDTO takeClientUsingTgbot(ClientTakeTelegramDTO clientTakeTelegramDTO);
}
