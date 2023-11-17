package TgBot.util;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

@Component
public class EmojiParserCustom {
    public String messageWithEmoji(String UNIcode) {
        return EmojiParser.parseToUnicode(UNIcode);
    }
}
