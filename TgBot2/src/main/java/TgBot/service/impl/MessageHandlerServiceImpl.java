package TgBot.service.impl;

import TgBot.service.MessageHandlerService;
import TgBot.telegramAPI.TelegramBot;
import TgBot.util.CommonSendTextMessage;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {

    private TelegramBot telegramBot;
    private CommonSendTextMessage commonSendTextMessage;

    @Autowired
    public MessageHandlerServiceImpl(CommonSendTextMessage commonSendTextMessage) {
        this.commonSendTextMessage = commonSendTextMessage;
    }

    @Override
    public void init(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void handle(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        switch (messageText) {
            case "/start" -> {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            }
            case "/register" -> {
                registerCommandReceived(chatId);
            }
            case "/visit" -> {
                visitSite(chatId);
            }
            default -> {
                commonSendTextMessage.sendTextMessage(chatId, EmojiParser.parseToUnicode("Я тебя не понял " + ":crying_cat_face:"));
            }
        }
    }

    private String messageWithEmoji(String s) {
        return EmojiParser.parseToUnicode(s);
    }

    private void startCommandReceived(Long chatId, String firstName) {
        String answer = "Hi, " + firstName + "! ";
        commonSendTextMessage.sendTextMessage(chatId, answer);
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

    private void registerCommandReceived(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                EmojiParser.parseToUnicode("Do you really want to register? " + ":thinking:"));
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


//    private void sendMessage(Long chatId, String textToSend) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId.toString());
//        sendMessage.setText(textToSend);
//
//        addHeartAndDis(sendMessage);
//        try {
//            telegramBot.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            log.error("Error occurred " + e.getMessage());
//        }
//    }
//
//    private void sendMessage(SendMessage sendMessage) {
//        try {
//            telegramBot.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            log.error("Error occurred " + e.getMessage());
//        }
//    }
//
//    private void addHeartAndDis(SendMessage sendMessage) {
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//
//        KeyboardRow row = new KeyboardRow();
////        row.add(EmojiParser.parseToUnicode(":heart:"));
//
//        KeyboardButton keyboardButton = new KeyboardButton();
//        keyboardButton.setText(EmojiParser.parseToUnicode("Open login " + ":heart:"));
//        keyboardButton.setWebApp(new WebAppInfo("https://ispaxer.github.io/SPACE-REGION.github.io/"));
//
//        row.add(keyboardButton);
//        keyboardRows.add(row);
//
//
////        row = new KeyboardRow();
////        row.add(EmojiParser.parseToUnicode(":-1:"));
////        keyboardRows.add(row);
//
//        keyboardMarkup.setKeyboard(keyboardRows);
//        sendMessage.setReplyMarkup(keyboardMarkup);
//    }
}
