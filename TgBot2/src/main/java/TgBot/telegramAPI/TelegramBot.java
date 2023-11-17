package TgBot.telegramAPI;

import TgBot.configuration.BotConfig;
import TgBot.service.DistributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final DistributionService distributionService;

    @Autowired
    public TelegramBot(BotConfig botConfig, DistributionService distributionService) {
        this.botConfig = botConfig;
        this.distributionService = distributionService;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "welcome message"));
        listOfCommands.add(new BotCommand("/login", "login in account"));
        listOfCommands.add(new BotCommand("/about", "about me"));
        listOfCommands.add(new BotCommand("/logout", "logout from account"));
        listOfCommands.add(new BotCommand("/set_price", "settings price"));
        listOfCommands.add(new BotCommand("/set_password", "change password"));

        distributionService.init(this);

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error settings bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        distributionService.distribution(update);
    }

}
