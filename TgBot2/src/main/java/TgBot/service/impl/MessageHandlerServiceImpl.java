package TgBot.service.impl;

import TgBot.service.MessageHandlerService;
import TgBot.telegramAPI.TelegramBot;
import TgBot.util.CommonSendTextMessage;
import TgBot.util.EmojiParserCustom;
import TgBot.web.service.impl.ApiRequestService;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {

    private TelegramBot telegramBot;
    private final CommonSendTextMessage commonSendTextMessage;
    private final TgBot.web.service.impl.ApiRequestService apiRequestService;
    private final EmojiParserCustom emojiParserCustom;

    @Autowired
    public MessageHandlerServiceImpl(CommonSendTextMessage commonSendTextMessage, ApiRequestService apiRequestService, EmojiParserCustom emojiParserCustom) {
        this.commonSendTextMessage = commonSendTextMessage;
        this.apiRequestService = apiRequestService;
        this.emojiParserCustom = emojiParserCustom;
    }

    @Override
    public void init(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }



    private void notAllowed(Update update) {
        commonSendTextMessage.sendTextMessage(
                new SendMessage(
                        update.getMessage().getChatId().toString(),
                        "Нет прав доступа, сначала выполните вход.\n Команда для входа /login"
                )
        );
    }

    @Override
    public void handle(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Boolean userIsActive = apiRequestService.telegramIsActive(update);

        switch (messageText) {
            case "/start" -> {
                startCommand(update);
            }
            case "/login" -> {
                loginCommand(update);
            }
            case "/help" -> {
                helpCommand(update);
            }
            case "/about" -> {
                if (!userIsActive)
                    notAllowed(update);
                aboutMeCommand(update);
            }
            case "/logout" -> {
                if (!userIsActive)
                    notAllowed(update);
                logoutCommand(update);
            }
            case "get_clients" -> {
                if (!userIsActive)
                    notAllowed(update);
                getClients(update);
            }
            case "/get_price" -> {
                if (!userIsActive)
                    notAllowed(update);
                getPriceCommand(update);
            }
            case "/set_price" -> {
                if (!userIsActive)
                    notAllowed(update);
                setPriceCommand(update);
            }
            case "/set_password" -> {
                if (!userIsActive)
                    notAllowed(update);
                setPasswordCommand(update);
            }
            default -> {
                commonSendTextMessage.sendTextMessage(update, EmojiParser.parseToUnicode("Я тебя не понял " + ":crying_cat_face:"));
            }
        }

    }


    private void startCommand(Update update) {
        Long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getChat().getFirstName();
        String answer = "Hi, " + firstName + "! Ваш chatId: " + chatId;
        commonSendTextMessage.sendTextMessage(update, answer);
    }

    private void loginCommand(Update update) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton keyboardButton = KeyboardButton.builder()
                .text(emojiParserCustom.messageWithEmoji("Войти " + ":heart:"))
                .webApp(new WebAppInfo("https://ispaxer.github.io/SPACE-REGION.github.io/"))
                .build();
//        keyboardButton.setText(
//                messageWithEmoji("Войти " + ":heart:")
//        );
//        keyboardButton.setWebApp(
//                new WebAppInfo("https://ispaxer.github.io/SPACE-REGION.github.io/")
//        );

        row.add(keyboardButton);
        keyboardRows.add(row);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);

        SendMessage sendMessage = new SendMessage(
                update.getMessage().getChatId().toString(),
                emojiParserCustom.messageWithEmoji("Для входа нажмите кнопку снизу " + ":point_down:")
        );
        sendMessage.setReplyMarkup(keyboardMarkup);
        sendMessage.setReplyMarkup(keyboardMarkup);
        commonSendTextMessage.sendTextMessage(sendMessage);
    }

    private void helpCommand(Update update) {
        commonSendTextMessage.sendTextMessage(
                new SendMessage(
                        update.getMessage().getChatId().toString(),
                        "Описание команд..."
                )
        );
    }

    private void aboutMeCommand(Update update) {
//        EngineerDTO engineerDTO = apiRequestService.getAuthEngineer();
//        commonSendTextMessage.sendTextMessage(
//                new SendMessage(
//                        update.getMessage().getChatId().toString(),
//                        "Вы вошли в аккаунт под " + engineerDTO.getLogin()
//                )
//        );
    }

    private void logoutCommand(Update update) {
//        EngineerDTO engineerDTO = apiRequestService.getEngineerByTgId(update);
//        apiRequestService.deleteTelegramId();
//        commonSendTextMessage.sendTextMessage(
//                new SendMessage(
//                        update.getMessage().getChatId().toString(),
//                        "Вы вышли из аккаунта " + engineerDTO.getLogin()
//                )
//        );
    }

    private void getClients(Update update) {

    }


    private void getPriceCommand(Update update) {
    }

    private void setPriceCommand(Update update) {

    }

    private void setPasswordCommand(Update update) {
//        String responseMessage = apiRequestService.updatePasswordByEngineer( );
//        commonSendTextMessage.sendTextMessage(
//                new SendMessage(
//                        update.getMessage().getChatId().toString(),
//                        responseMessage
//                )
//        );
    }

    private void visitSite(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                EmojiParser.parseToUnicode("Do you really want to visit? " + ":thinking:"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = getLists();

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        commonSendTextMessage.sendTextMessage(sendMessage);
    }



    @NotNull
    private static List<List<InlineKeyboardButton>> getLists() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton ybutton = new InlineKeyboardButton();
        ybutton.setText("Yes");
//        ybutton.setCallbackData("YES_BUTTON");

        WebAppInfo webAppInfo = new WebAppInfo("https://my-kadastr.ru/login");
        ybutton.setWebApp(webAppInfo);

        rowInline.add(ybutton);

        rowsInline.add(rowInline);

        return rowsInline;
    }
}
