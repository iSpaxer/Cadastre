package DBPostgres.util.wrapper;

import DBPostgres.dto.client.ClientDbDTO;
import DBPostgres.dto.engineer.EngineerLoginDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EngineerAndClient {
    private EngineerLoginDTO engineer;
    private ClientDbDTO client;
}
