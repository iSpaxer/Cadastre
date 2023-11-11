package TgBot.service;

import TgBot.config.BotConfig;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.methods.webapp.AnswerWebAppQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Autowired
    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your date store"));
        listOfCommands.add(new BotCommand("/deletedata", "delete your data"));
        listOfCommands.add(new BotCommand("/help", "description all commands"));
        listOfCommands.add(new BotCommand("/settings", "bot settings"));
        listOfCommands.add(new BotCommand("/register", "register me"));
        listOfCommands.add(new BotCommand("/visit", "visit on site"));

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
        if (update.hasMessage() && update.getMessage().hasText()) {
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

                    sendMessage(chatId, EmojiParser.parseToUnicode("Я тебя не понял " + ":crying_cat_face:"));
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();

            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            EditMessageText editMessageText = new EditMessageText();
            switch (callBackData) {
                case "YES_BUTTON" -> {
//                    String text = "You pressed YES button";
//
//                    editMessageText.setChatId(chatId.toString());
//                    editMessageText.setText(text);
//                    editMessageText.setMessageId(messageId);
//
//                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//                    List<List<InlineKeyboardButton>> rowsInline = getLists();
//                    inlineKeyboardMarkup.setKeyboard(rowsInline);
//
//                    editMessageText.setReplyMarkup(inlineKeyboardMarkup);

                    String chatID = update.getCallbackQuery().getFrom().getId().toString();
                    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();


                    answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
                    answerCallbackQuery.setUrl("https://ya.ru/");


                    try {
                        execute(answerCallbackQuery);
                        return;
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        return;
                    }


                }
                case "NO_BUTTON" -> {
                    String text = "You pressed NO button";
                    editMessageText.setChatId(chatId.toString());
                    editMessageText.setText(text);
                    editMessageText.setMessageId(messageId);

                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = getLists();
                    inlineKeyboardMarkup.setKeyboard(rowsInline);

                    editMessageText.setReplyMarkup(inlineKeyboardMarkup);
                }
            }
            try {
                execute(editMessageText);
            } catch (TelegramApiException e) {
                log.error("Error edited message: " + e.getMessage());
            }
        }
    }



    private void startCommandReceived(Long chatId, String firstName) {
        String answer = "Hi, " + firstName + "!";
        sendMessage(chatId, answer);
    }

    private void visitSite(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                EmojiParser.parseToUnicode("Do you really want to visit? " + ":thinking:"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = getLists();

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage(sendMessage);


    }

    private void registerCommandReceived(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                EmojiParser.parseToUnicode("Do you really want to register? " + ":thinking:"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = getLists();

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage(sendMessage);
    }

    @NotNull
    private static List<List<InlineKeyboardButton>> getLists() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton ybutton = new InlineKeyboardButton();
        ybutton.setText("Yes");
        ybutton.setCallbackData("YES_BUTTON");

        WebAppInfo webAppInfo = new WebAppInfo("https://ya.ru/");
        ybutton.setWebApp(webAppInfo);

//        InlineKeyboardButton nbutton = new InlineKeyboardButton();
//        nbutton.setText("No");
//        nbutton.setCallbackData("NO_BUTTON");

        rowInline.add(ybutton);
//        rowInline.add(nbutton);

        rowsInline.add(rowInline);

        return rowsInline;
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred " + e.getMessage());
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred " + e.getMessage());
        }
    }

    private void addHeartAndDis(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(EmojiParser.parseToUnicode(":heart:"));
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add(EmojiParser.parseToUnicode(":-1:"));

        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

}
