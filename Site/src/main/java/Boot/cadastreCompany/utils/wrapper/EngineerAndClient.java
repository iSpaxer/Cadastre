package Boot.cadastreCompany.utils.wrapper;


import Boot.cadastreCompany.dto.ClientDbDTO;
import Boot.cadastreCompany.dto.EngineerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EngineerAndClient {
    private EngineerDTO engineer;
    private ClientDbDTO client;
}
