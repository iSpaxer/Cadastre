package TgBot.util.cache;

import TgBot.dto.EditSendUsersDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
//TODO refactoring
public class CacheCustom {
    public Map<String, EditSendUsersDTO> cashe = new HashMap<>();
}
