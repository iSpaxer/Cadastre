package TgBot.web.controller;

import TgBot.dto.ClientWithoutPhoneForTelegramDTO;
import TgBot.util.CommonSendTextMessage;
import TgBot.util.EmojiParserCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/tgbot/api")
@RequiredArgsConstructor
public class MainRestController {
    //TODO CommonSendTextMessage MB does not have telegrambot (init was not created)
    private final CommonSendTextMessage commonSendTextMessage;
    private final EmojiParserCustom emojiParserCustom;
    @PostMapping("notificOfNewClient")
    public ResponseEntity<?> notificOfNewClient(@RequestBody ClientWithoutPhoneForTelegramDTO clientWithoutPhoneForTelegramDTO) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(emojiParserCustom.messageWithEmoji(
                   ":white_check_mark:"+ " Новое обращение заказчика!"
                           + "\n:id: " + clientWithoutPhoneForTelegramDTO.getId()
                           + "\n:bust_in_silhouette: " + clientWithoutPhoneForTelegramDTO.getName()
                           + "\nСкорее обработай его! " + ":rocket:"));


        for (Long id : clientWithoutPhoneForTelegramDTO.getAllEngineersWithTgId()) {
            sendMessage.setChatId(id.toString());
            setInlineKeyboardButtonForGetAllAuthTgId(sendMessage, clientWithoutPhoneForTelegramDTO.getId());
            commonSendTextMessage.sendTextMessage(sendMessage);
        }

        return ResponseEntity.ok("Successfully!");
    }

    @GetMapping("test")
    public String test() {
        return "tgbot is Working!";
    }

    private void setInlineKeyboardButtonForGetAllAuthTgId(SendMessage sendMessage, Integer clientId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton ybutton = new InlineKeyboardButton(
                emojiParserCustom.messageWithEmoji("Take!" + ":fire:"));

        ybutton.setCallbackData("taken_client_with_tgId: " + clientId); // TODO Long all engineers JSON

        rowInline.add(ybutton);
        rowsInline.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInline);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

//    private void visitSite(Long chatId) {
//        SendMessage sendMessage = new SendMessage(chatId.toString(),
//                emojiParserCustom.messageWithEmoji("Do you really want to visit? " + ":thinking:"));
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//
//        List<List<InlineKeyboardButton>> rowsInline = getLists();
//
//        inlineKeyboardMarkup.setKeyboard(rowsInline);
//
//        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//        commonSendTextMessage.sendTextMessage(sendMessage);
//    }
//
//
//
//    @NotNull
//    private static List<List<InlineKeyboardButton>> getLists() {
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//
//        InlineKeyboardButton ybutton = new InlineKeyboardButton();
//        ybutton.setText("Yes");
////        ybutton.setCallbackData("YES_BUTTON");
//
//        WebAppInfo webAppInfo = new WebAppInfo("https://my-kadastr.ru/login");
//        ybutton.setWebApp(webAppInfo);
//
//        rowInline.add(ybutton);
//
//        rowsInline.add(rowInline);
//
//        return rowsInline;
//    }
}

