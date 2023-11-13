package TgBot.util;

import TgBot.telegramAPI.TelegramBot;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CommonSendTextMessage implements InitTelegramBot {
    private TelegramBot telegramBot;

    @Override
    public void init(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendTextMessage(SendMessage sendMessage) {
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred " + e.getMessage());
        }
    }

    public void sendTextMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(textToSend);

        addHeartAndDis(sendMessage);
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred " + e.getMessage());
        }
    }

    private void addHeartAndDis(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
//        row.add(EmojiParser.parseToUnicode(":heart:"));

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(EmojiParser.parseToUnicode("Open login " + ":heart:"));
        keyboardButton.setWebApp(new WebAppInfo("https://ispaxer.github.io/SPACE-REGION.github.io/"));

        row.add(keyboardButton);
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }
}
