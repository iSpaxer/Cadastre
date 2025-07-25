package TgBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientWithoutPhoneForTelegramDTO {
    private int id;
    private String name;
    private Long[] allEngineersWithTgId;
}
