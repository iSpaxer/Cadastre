package DBPostgres.service;

import DBPostgres.dto.ClientDbDTO;
import DBPostgres.dto.EngineerLoginDTO;

public interface CommonService {
    void checkForTakeClient(EngineerLoginDTO engineerLoginDTO, ClientDbDTO clientDbDTO);
}
