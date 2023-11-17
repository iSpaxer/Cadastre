package TgBot.web.service;

import TgBot.dto.EngineerTelegramDTO;

public interface ApiRequestService {
    public Boolean authenticationEngineer(EngineerTelegramDTO engineerTelegramDTO);
    public Boolean telegramIsActive(Long chatId);
}
