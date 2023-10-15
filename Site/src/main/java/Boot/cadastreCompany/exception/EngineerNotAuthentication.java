package Boot.cadastreCompany.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EngineerNotAuthentication extends RuntimeException {
    public EngineerNotAuthentication(String sms) {
        super(sms);
    }
}
